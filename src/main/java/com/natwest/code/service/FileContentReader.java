package com.natwest.code.service;

import java.util.List;

import com.sapient.code.model.IncomeInfo;

public interface FileContentReader {

	public List<IncomeInfo> extractDataFromFile(String filePath) ;
}
