package com.natwest.code.factory;

import com.natwest.code.constants.Constants;
import com.natwest.code.model.InputFileRequestDto;
import com.natwest.code.model.ReferenceInputFileRequestDto;
import com.natwest.code.model.ReportGenerationRequestDto;
import com.natwest.code.service.FileContentReader;
import com.natwest.code.service.impl.CsvContentReader;
import com.natwest.code.service.impl.ExcelContentReader;
import com.natwest.code.service.impl.XmlContentReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Component
public class FileProcessor {

	private FileContentReader fileReader ;
	
	public Pair<List<InputFileRequestDto>, List<ReferenceInputFileRequestDto>> extractDataFromFile (ReportGenerationRequestDto reportGenerationRequestDto) throws IOException {
		var fileExtension = FilenameUtils.getExtension(reportGenerationRequestDto.getInputFile().getOriginalFilename());
		byte[] inputFileData = reportGenerationRequestDto.getInputFile().getBytes();
		byte[] referenceFileData = reportGenerationRequestDto.getReferenceFile().getBytes();
		if (Constants.CSV.contains(fileExtension)) {
			fileReader = new CsvContentReader();
			return fileReader.extractDataFromFile(inputFileData,
																						referenceFileData);
		}
		
		if (Constants.XML.contains(fileExtension)) {
			fileReader = new XmlContentReader();
			return fileReader.extractDataFromFile(inputFileData,
																						referenceFileData);
		}
		
		if (Constants.EXCEL.contains(fileExtension) || Constants.EXCEL_2.contains(fileExtension)) {
			fileReader = new ExcelContentReader();
			return fileReader.extractDataFromFile(inputFileData,
																						referenceFileData);
		}
		
		return Pair.of(Collections.emptyList(), Collections.emptyList());
	}
}
