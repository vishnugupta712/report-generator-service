package com.natwest.code.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferenceInputFileRequestDto {

	private String refkey1;
	private String refdata1;
	private String refkey2;
	private String refdata2;
	private String refdata3;
	private double refdata4;
	
	

}