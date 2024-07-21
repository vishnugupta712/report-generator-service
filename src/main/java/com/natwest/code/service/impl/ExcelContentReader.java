package com.natwest.code.service.impl;

import com.natwest.code.model.InputFileRequestDto;
import com.natwest.code.model.ReferenceInputFileRequestDto;
import com.natwest.code.service.FileContentReader;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class ExcelContentReader  implements FileContentReader {

	@Override
	public Pair<List<InputFileRequestDto>, List<ReferenceInputFileRequestDto>> extractDataFromFile(byte[] inputFileData, byte[] referenceFileData) {
		// TODO Auto-generated method stub
		return Pair.of(Collections.emptyList(), Collections.emptyList());
	}

}
