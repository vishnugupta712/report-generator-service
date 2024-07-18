package com.natwest.code.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.sapient.code.constants.Constants;
import com.sapient.code.model.IncomeInfo;
import com.sapient.code.model.OutputModel;
import com.sapient.code.service.DataProcessorService;

public class DataProcessorServiceImpl implements DataProcessorService{

	public Map<String, Double> processExtractedData(List<IncomeInfo> infoList){

		Map<String,List<Double> > map=  new HashMap<>();
		Map<String, Double> incomeMap = new TreeMap<>();
		infoList.stream()
		.sorted(new IncomeInfoComparator())
		.map(info -> {
			List<Double> doubleList = new ArrayList<>();
			String key =  compositeKey(info);
			if (map.containsKey(key)) {
				double totalIncome = map.get(key).get(1)+convertToUSD(info.getAmount(), info.getCurrency());
				double count = map.get(key).get(0)+1;
				doubleList.add(count);
				doubleList.add(totalIncome);
				map.get(key).clear();
				map.put(key,doubleList );
			}
			else {
				doubleList.add( 1.0);
				
				doubleList.add(convertToUSD(info.getAmount(), info.getCurrency()));
				map.put(key, doubleList);
			}
			return info;
		}).collect(Collectors.toList());
		
		map.keySet().stream()
		.map(str -> {
			List<Double> dlList= map.get(str);
			Double avAmount = dlList.get(1)/dlList.get(0);
			incomeMap.put(str, avAmount);
			return str;
		}).collect(Collectors.toSet());
		writeToFile(incomeMap);
		return incomeMap;
	}
	
	private String compositeKey (IncomeInfo info) {
		StringBuilder strBuild = new StringBuilder();
		if (info.getCountry()==null || info.getCountry().trim().isEmpty()) {
			strBuild.append(info.getCity());
		}
		else strBuild.append(info.getCountry());
		
		return strBuild
				.append(Constants.COLON_SEPERATOR)
				.append(info.getGender()).toString();
	}
	
	
	private double convertToUSD(double amount, String currency) {
		
		if ("GBP".equals(currency)) {
			return amount/Constants.USD_GBP;
		}
		if ("INR".equals(currency)) {
			return amount/Constants.USD_INR;
		}
		if ("HKD".equals(currency)) {
			return amount/Constants.USD_HKD;
		}
		
		if ("SGD".equals(currency)) {
			return amount/Constants.USD_SGD;
		}
		
		return amount;
	}
	
	
	private void writeToFile(Map<String, Double> incomeMap) {
		List<OutputModel> modelList= new ArrayList<>();
		incomeMap.keySet()
		.stream()
		.map(str-> {
			String[] strArr= str.split(Constants.COLON_SEPERATOR);
			OutputModel om = new OutputModel(strArr[0] ,strArr[1], incomeMap.get(str));
			modelList.add(om);
			return str;
		}).collect(Collectors.toSet());
		
		try {
			FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\indiahiring\\Desktop\\Output.csv"));
			
			fos.write(("City/Country,	Gender, Average Income(in USD)"+"\n").getBytes());
			modelList.forEach(model -> {
				try {
					fos.write((model.getCountry()+","+model.getGender()+","+model.getAmount()+"\n").getBytes());
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			});
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
