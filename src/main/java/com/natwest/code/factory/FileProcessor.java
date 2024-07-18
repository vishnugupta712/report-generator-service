package com.natwest.code.factory;

import java.util.List;

import com.sapient.code.constants.Constants;
import com.sapient.code.model.IncomeInfo;
import com.sapient.code.service.FileContentReader;
import com.sapient.code.serviceImpl.CsvContentReader;

public class FileProcessor {

	FileContentReader fileReader ;
	
	public List<IncomeInfo> extractDataFromFile (String filePath){
		String format = filePath.substring(filePath.lastIndexOf(Constants.DOT)+1);
		
		if (Constants.CSV.contains(format)) {
			fileReader = new CsvContentReader();
			return fileReader.extractDataFromFile(filePath);
		}
		
		if (Constants.XML.contains(format)) {
			fileReader = new CsvContentReader();
			return fileReader.extractDataFromFile(filePath);
		}
		
		if (Constants.EXCEL.contains(format) || Constants.EXCEL_2.contains(format)) {
			fileReader = new CsvContentReader();
			return fileReader.extractDataFromFile(filePath);
		}
		
		return null;
	}
}
