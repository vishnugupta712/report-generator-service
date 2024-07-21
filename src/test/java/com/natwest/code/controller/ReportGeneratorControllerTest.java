package com.natwest.code.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.natwest.code.model.ReportGenerationRequestDto;
import com.natwest.code.service.ReportGeneratorService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ReportGeneratorControllerTest {

  @InjectMocks
  private ReportGeneratorController reportGeneratorController;

  @Mock
  private ReportGeneratorService reportGeneratorService;

  private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(reportGeneratorController).build();

  @Test
  public void testGenerateReportSuccess() throws Exception {
    // Arrange
    ReportGenerationRequestDto requestDto = new ReportGenerationRequestDto();
    ByteArrayResource resource = new ByteArrayResource("test report content".getBytes());

    when(reportGeneratorService.generateReportAsync(requestDto)).thenReturn(resource);

    String timestamp = LocalDateTime.now().toString();
    String dynamicFileName = "report_" + timestamp + ".csv";

    // Act and Assert
    mockMvc.perform(post("/report/generate")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("{\"someField\": \"someValue\"}")) // Replace with actual JSON body
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
           .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + dynamicFileName))
           .andExpect(content().string("test report content"));
  }

  @Test
  public void testGenerateReportException() throws Exception {
    // Arrange
    ReportGenerationRequestDto requestDto = new ReportGenerationRequestDto();
    when(reportGeneratorService.generateReportAsync(requestDto)).thenThrow(new RuntimeException("Generation error"));

    // Act and Assert
    mockMvc.perform(post("/report/generate")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("{\"someField\": \"someValue\"}")) // Replace with actual JSON body
           .andExpect(status().isInternalServerError())
           .andExpect(content().string("Report generation failed: Generation error"));
  }
}