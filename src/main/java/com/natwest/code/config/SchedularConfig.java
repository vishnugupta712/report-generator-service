package com.natwest.code.config;

import com.natwest.code.model.CustomMultipartFile;
import com.natwest.code.model.ReportGenerationRequestDto;
import com.natwest.code.service.ReportGeneratorService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class SchedularConfig {
  @Autowired
  private ReportGeneratorService reportGenerationService;

  private final Path reportStorageLocation = Paths.get("generated_reports").toAbsolutePath().normalize();
//schedule at every day at 1AM
  @Scheduled(cron = "0 0 1 * * ?")
  public void generateReportAtScheduledTime() throws IOException{
    var inputFile = fetchInputFile();
    var referenceFile = fetchReferenceFile();

    CompletableFuture.runAsync(() -> {
      try {
        String reportId = String.valueOf(System.currentTimeMillis());
        var inputDto  = new ReportGenerationRequestDto();
        inputDto.setInputFile(inputFile);
        inputDto.setReferenceFile(referenceFile);
        ByteArrayResource reportResource = reportGenerationService.generateReport(inputDto);

        // Save the generated report to disk
        Path reportPath = reportStorageLocation.resolve(reportId + ".csv");
        Files.createDirectories(reportPath.getParent());
        Files.write(reportPath, reportResource.getByteArray(), StandardOpenOption.CREATE);

        log.info("[Schedular]Report generated and saved successfully.");
      } catch (IOException e) {
        log.error("[Schedular]Failed to generate or save report: " + e.getMessage());
      }
    }).exceptionally(ex -> {
      log.error("[Schedular]Error during report generation: " + ex.getMessage());
      return null;
    });
  }

  private MultipartFile fetchInputFile() throws IOException{
    ClassPathResource classPathResource = new ClassPathResource("mock/input.csv");
    MultipartFile multipartFile = new CustomMultipartFile(classPathResource.getInputStream().readAllBytes(), "input.csv", "text"
      + "/plain");
    return multipartFile;
  }

  private MultipartFile fetchReferenceFile() throws IOException{
    ClassPathResource refClassPathResource = new ClassPathResource("mock/reference.csv");
    MultipartFile multipartFile = new CustomMultipartFile(refClassPathResource.getInputStream().readAllBytes(), "reference.csv", "text"
      + "/plain");
    return multipartFile;
  }
}
