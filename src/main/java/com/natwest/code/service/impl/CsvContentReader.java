package com.natwest.code.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natwest.code.model.InputFileRequestDto;
import com.natwest.code.model.ReferenceInputFileRequestDto;
import com.natwest.code.service.FileContentReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.Pair;


public class CsvContentReader implements FileContentReader {

	@Override
	public Pair<List<InputFileRequestDto>, List<ReferenceInputFileRequestDto>> extractDataFromFile(byte[] inputFileData, byte[] referenceFileData) {
		List<InputFileRequestDto> inputFileRequestDtoList = new ArrayList<>();
		List<ReferenceInputFileRequestDto> referenceInputFileRequestDtoList = new ArrayList<>();
		ObjectMapper objMapp = new ObjectMapper();
		InputStream inputStream = new ByteArrayInputStream(inputFileData);
		InputStream refInputStream = new ByteArrayInputStream(referenceFileData);
		try (InputStreamReader reader = new InputStreamReader(inputStream);
			InputStreamReader refFilereader = new InputStreamReader(refInputStream);
			CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
			CSVParser refCsvparser = new CSVParser(refFilereader, CSVFormat.DEFAULT.withFirstRecordAsHeader());) {
			for (CSVRecord record : refCsvparser) {
				// Process each record dynamically
				Map<String, String> recordMap = new HashMap<>();
				record.toMap().forEach((key, value) -> {
					recordMap.put(key, value);
				});

				// Print or process the record map
				var referenceInputFileRequestDto = objMapp.convertValue(recordMap, ReferenceInputFileRequestDto.class);
				referenceInputFileRequestDtoList.add(referenceInputFileRequestDto);
			}
			for (CSVRecord record : parser) {
				Map<String, String> recordMap = new HashMap<>();
				record.toMap().forEach((key, value) -> {
					recordMap.put(key, value);
				});

				// Print or process the record map
				var inputFileRequestDto = objMapp.convertValue(recordMap, InputFileRequestDto.class);
				inputFileRequestDtoList.add(inputFileRequestDto);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return Pair.of(Collections.emptyList(), Collections.emptyList());
		}
		return Pair.of(inputFileRequestDtoList, referenceInputFileRequestDtoList);
	}

}
