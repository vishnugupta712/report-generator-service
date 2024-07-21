package com.natwest.code.service.impl;

import com.natwest.code.factory.FileProcessor;
import com.natwest.code.model.OutputResponseDto;
import com.natwest.code.model.ReportGenerationRequestDto;
import com.natwest.code.service.DataProcessorService;
import com.natwest.code.service.FileContentReader;
import com.natwest.code.service.ReportGeneratorService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ReportGeneratorServiceImpl implements ReportGeneratorService {
  private FileProcessor fileProcessor;
  private DataProcessorService dataProcessorService;

  @Override
  public ByteArrayResource generateReport(ReportGenerationRequestDto reportGenerationRequestDto) {
    log.info("[reportGenerateService] stared report generate process");
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try{
      var inputFileList = fileProcessor.extractDataFromFile(reportGenerationRequestDto);
      var outputReport = dataProcessorService.processExtractedData(inputFileList.getLeft(), inputFileList.getRight());
      log.info("writing output report to file");
      writeOutputFile(outputReport, outputStream);
      log.info("after writing to output file size : {}", outputStream.toByteArray().length);
      return new ByteArrayResource(outputStream.toByteArray());
    }
    catch(IOException e){
      log.error("[Service]while extracting data from file process", e);
    }
    return null;
  }

  @Override
  public ByteArrayResource generateReportAsync(ReportGenerationRequestDto reportGenerationRequestDto) {
    log.info("[reportGenerateService] stared report generate process in async");
    return CompletableFuture.supplyAsync(()-> generateReport(reportGenerationRequestDto)).join();
  }

  private void writeOutputFile(List<OutputResponseDto> outputRecords, OutputStream outputStream) throws IOException {
    try (OutputStreamWriter writer = new OutputStreamWriter(outputStream);
      CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("outfield1", "outfield2", "outfield3", "outfield4", "outfield5"))) {
      for (OutputResponseDto outputRecord : outputRecords) {
        csvPrinter.printRecord(
          outputRecord.getOutfield1(),
          outputRecord.getOutfield2(),
          outputRecord.getOutfield3(),
          outputRecord.getOutfield4(),
          outputRecord.getOutfield5()
        );
      }
    }
  }
}
