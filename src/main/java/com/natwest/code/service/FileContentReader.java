package com.natwest.code.service;

import com.natwest.code.model.InputFileRequestDto;
import com.natwest.code.model.ReferenceInputFileRequestDto;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public interface FileContentReader {

	public Pair<List<InputFileRequestDto>, List<ReferenceInputFileRequestDto>> extractDataFromFile(byte[] inputFileData, byte[] referenceFileData) ;
}
