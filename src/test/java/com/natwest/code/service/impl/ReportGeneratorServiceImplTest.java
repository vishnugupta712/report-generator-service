package com.natwest.code.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.natwest.code.factory.FileProcessor;
import com.natwest.code.model.OutputResponseDto;
import com.natwest.code.model.ReportGenerationRequestDto;
import com.natwest.code.service.DataProcessorService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;

@ExtendWith(MockitoExtension.class)
public class ReportGeneratorServiceImplTest {

  @Mock
  private FileProcessor fileProcessor;

  @Mock
  private DataProcessorService dataProcessorService;

  @InjectMocks
  private ReportGeneratorServiceImpl reportGeneratorServiceImpl;

  @Test
  public void testGenerateReportSuccess() throws IOException {
    // Arrange
    ReportGenerationRequestDto requestDto = new ReportGenerationRequestDto();
    OutputResponseDto dto1 = new OutputResponseDto("value1", "value2", "value3", 12.2, 12.3);
    List<OutputResponseDto> outputReport = List.of(dto1);

    when(fileProcessor.extractDataFromFile(requestDto)).thenReturn(Pair.of(List.of(), List.of()));
    when(dataProcessorService.processExtractedData(any(), any())).thenReturn(outputReport);

    // Capture the output to validate
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    // Act
    ByteArrayResource result = reportGeneratorServiceImpl.generateReport(requestDto);

    // Assert
    assertNotNull(result);
    String resultContent = new String(result.getByteArray());
    assertTrue(resultContent.contains("value1"));
    assertTrue(resultContent.contains("value2"));
  }

  @Test
  public void testGenerateReportIOException() throws IOException {
    // Arrange
    ReportGenerationRequestDto requestDto = new ReportGenerationRequestDto();
    when(fileProcessor.extractDataFromFile(requestDto)).thenThrow(new IOException("File processing error"));

    // Act
    ByteArrayResource result = reportGeneratorServiceImpl.generateReport(requestDto);

    // Assert
    assertNull(result);
    // Check if the error is logged correctly (you might need to verify this if your logger configuration allows)
  }

  @Test
  public void testGenerateReportAsync() throws IOException {
    // Arrange
    ReportGenerationRequestDto requestDto = new ReportGenerationRequestDto();
    OutputResponseDto dto1 = new OutputResponseDto("value1", "value2", "value3", 12.2, 12.3);
    List<OutputResponseDto> outputReport = List.of(dto1);

    when(fileProcessor.extractDataFromFile(requestDto)).thenReturn(Pair.of(List.of(), List.of()));
    when(dataProcessorService.processExtractedData(any(), any())).thenReturn(outputReport);

    // Act
    ByteArrayResource result = reportGeneratorServiceImpl.generateReportAsync(requestDto);

    // Assert
    assertNotNull(result);
    String resultContent = new String(result.getByteArray());
    assertTrue(resultContent.contains("value1"));
    assertTrue(resultContent.contains("value2"));
  }
}