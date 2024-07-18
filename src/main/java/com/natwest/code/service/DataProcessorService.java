package com.natwest.code.service;

import java.util.List;
import java.util.Map;

import com.sapient.code.model.IncomeInfo;

public interface DataProcessorService {

	public Map<String, Double> processExtractedData(List<IncomeInfo> infoList);
	
}
