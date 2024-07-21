package com.natwest.code.service;

import com.natwest.code.model.ReportGenerationRequestDto;
import org.springframework.core.io.ByteArrayResource;

public interface ReportGeneratorService {
   ByteArrayResource generateReport(ReportGenerationRequestDto reportGenerationRequestDto);

   ByteArrayResource generateReportAsync(ReportGenerationRequestDto reportGenerationRequestDto);
}
