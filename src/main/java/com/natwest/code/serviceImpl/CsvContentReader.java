package com.natwest.code.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.sapient.code.constants.Constants;
import com.sapient.code.model.IncomeInfo;
import com.sapient.code.service.FileContentReader;

public class CsvContentReader implements FileContentReader{

	@Override
	public List<IncomeInfo> extractDataFromFile(String filePath) {
		
		List<IncomeInfo> infoList = new ArrayList<>();
		try(Stream<String> list = Files.lines(Paths.get(filePath)).skip(1)){
			
			list.forEach(str -> {
				String[] strArr = str.split(Constants.COMMA);
				IncomeInfo info = 
						new IncomeInfo(strArr[0],strArr[1],strArr[2],strArr[3], 
								Double.parseDouble(strArr[4]));
				infoList.add(info);
			});
			
		} catch (IOException e) {
			e.printStackTrace();
			return infoList;
		}
		return infoList;
	}

}
