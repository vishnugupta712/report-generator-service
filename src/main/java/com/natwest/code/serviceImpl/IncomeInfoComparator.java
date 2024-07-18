package com.natwest.code.serviceImpl;

import java.util.Comparator;

import com.sapient.code.model.IncomeInfo;

public class IncomeInfoComparator implements Comparator<IncomeInfo> {

	@Override
	public int compare(IncomeInfo arg0, IncomeInfo arg1) {
		if ( (arg0.getCountry()!=null || !arg0.getCountry().trim().isEmpty())
			&& (arg1.getCountry()!=null || !arg1.getCountry().trim().isEmpty()))	{
			return arg0.getCountry().compareTo(arg1.getCountry())  
					+ arg0.getGender().compareTo(arg1.getGender());
		}
		else return arg0.getCity().compareTo(arg1.getCountry())  
				+ arg0.getGender().compareTo(arg1.getGender());
	}

}
