package com.natwest.code;

import com.sapient.code.factory.FileProcessor;
import com.sapient.code.service.DataProcessorService;
import com.sapient.code.serviceImpl.DataProcessorServiceImpl;

public class ReportGenerationApplication {

	public static void main(String[] args) {
		FileProcessor fileProcessor = new FileProcessor();
		DataProcessorService dataProcessor = new DataProcessorServiceImpl();

		dataProcessor
				.processExtractedData(fileProcessor.extractDataFromFile("C:\\Users\\indiahiring\\Desktop\\Input.csv"));
	}

}
