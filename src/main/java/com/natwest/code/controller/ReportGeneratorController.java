package com.natwest.code.controller;

import static com.natwest.code.constants.Constants.CUSTOM_REPORT_PATH_FOR_SCHEDULAR;

import com.natwest.code.model.ReportGenerationRequestDto;
import com.natwest.code.service.ReportGeneratorService;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportGeneratorController {
  private ReportGeneratorService reportGeneratorService;

  private final Path reportStorageLocation = Paths.get(CUSTOM_REPORT_PATH_FOR_SCHEDULAR).toAbsolutePath().normalize();

  //API to instant generate output CSV from input CSV's
  //It generate report in async with join to reduce overhead on main thread which will increase run time.
  @PostMapping(value ="/generate", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE })
  public ResponseEntity<ByteArrayResource> generateReport(@ModelAttribute ReportGenerationRequestDto reportGenerationRequestDto) {
    try {
      var reportResource = reportGeneratorService.generateReportAsync(reportGenerationRequestDto);
      if(Objects.nonNull(reportResource)){
        String timestamp = LocalDateTime.now().toString();
        String dynamicFileName = "report_" + timestamp + ".csv";
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_OCTET_STREAM)
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+dynamicFileName)
                             .body(reportResource);
      }
    } catch (Exception e) {
      return ResponseEntity.status(500).body(new ByteArrayResource(("Report generation failed: " + e.getMessage()).getBytes()));
    }
    return ResponseEntity.status(500).body(new ByteArrayResource(("Report generation failed: due to null output "
      + "stream").getBytes()));
  }

// API to download output CSV file which was generated at scheduled time
  @GetMapping("/download/{reportId}")
  public ResponseEntity<Resource> downloadReport(@PathVariable String reportId) {
    try {
      Path filePath = reportStorageLocation.resolve(reportId + ".csv").normalize();
      Resource resource = new UrlResource(filePath.toUri());

      if (resource.exists() || resource.isReadable()) {
        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                             .body(resource);
      } else {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
