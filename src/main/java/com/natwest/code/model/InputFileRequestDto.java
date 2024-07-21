package com.natwest.code.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputFileRequestDto {
	private String field1;
	private String field2;
	private String field3;
	private String field4;
	private double field5;
	private String refkey1;
	private String refkey2;
}
