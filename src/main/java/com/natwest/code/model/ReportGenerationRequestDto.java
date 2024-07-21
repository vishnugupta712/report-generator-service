package com.natwest.code.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReportGenerationRequestDto {
  private MultipartFile inputFile;
  private MultipartFile referenceFile;
}
