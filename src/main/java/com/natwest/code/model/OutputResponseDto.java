package com.natwest.code.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputResponseDto implements Serializable{
	private String outfield1;
	private String outfield2;
	private String outfield3;
	private double outfield4;
	private double outfield5;
	
}
