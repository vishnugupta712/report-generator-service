package com.natwest.code.service.impl;

import com.natwest.code.model.InputFileRequestDto;
import com.natwest.code.model.OutputResponseDto;
import com.natwest.code.model.ReferenceInputFileRequestDto;
import com.natwest.code.service.DataProcessorService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class DataProcessorServiceImpl implements DataProcessorService {

	@Override
	public List<OutputResponseDto> processExtractedData(List<InputFileRequestDto> inputList,
																									List<ReferenceInputFileRequestDto> refInputList){
		log.info("processing input report to generate output report at data processing layer");
		Map<String, ReferenceInputFileRequestDto> referenceMap = refInputList.stream()
																																				 .collect(Collectors.toConcurrentMap(
																																					 record -> record.getRefkey1()
																																						 + record.getRefkey2() , record -> record));
		var outputReportList = inputList.stream().map(inputRecord -> {
			var referenceRecord = referenceMap.get(inputRecord.getRefkey1() + inputRecord.getRefkey2());
			return transform(inputRecord, referenceRecord);
		}).collect(Collectors.toList());
		log.info("output report generated : {}", outputReportList);
		return outputReportList;
	}

	private OutputResponseDto transform(InputFileRequestDto input, ReferenceInputFileRequestDto reference) {
		OutputResponseDto output = new OutputResponseDto();
		output.setOutfield1(input.getField1() + input.getField2());
		output.setOutfield2(reference.getRefdata1());
		output.setOutfield3(reference.getRefdata2() + reference.getRefdata3());
		var maxField5 = Double.max(input.getField5(),reference.getRefdata4());
		output.setOutfield4(Double.parseDouble(input.getField3())* maxField5);
		output.setOutfield5(maxField5);
		return output;
	}
}
