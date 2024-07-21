package com.natwest.code.service;

import com.natwest.code.model.InputFileRequestDto;
import com.natwest.code.model.OutputResponseDto;
import com.natwest.code.model.ReferenceInputFileRequestDto;
import java.util.List;
import java.util.Map;

public interface DataProcessorService {

	public List<OutputResponseDto> processExtractedData(List<InputFileRequestDto> infoList,
																											List<ReferenceInputFileRequestDto> refInputList);
	
}
