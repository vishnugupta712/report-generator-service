package com.natwest.code.service.impl;
import com.natwest.code.model.InputFileRequestDto;
import com.natwest.code.model.OutputResponseDto;
import com.natwest.code.model.ReferenceInputFileRequestDto;
import com.natwest.code.service.DataProcessorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class DataProcessorServiceImplTest {

  private final DataProcessorService dataProcessorService = new DataProcessorServiceImpl();

  @Test
  public void testProcessExtractedDataSuccess() {
    // Arrange
    InputFileRequestDto inputDto1 = new InputFileRequestDto("key1", "field1", "field2", "field3", 5.0, "ref1", "ref2");
    ReferenceInputFileRequestDto refDto1 = new ReferenceInputFileRequestDto("key1", "data1", "data2", "data3", "data4", 10.0);

    List<InputFileRequestDto> inputList = List.of(inputDto1);
    List<ReferenceInputFileRequestDto> refInputList = List.of(refDto1);

    // Act
    List<OutputResponseDto> output = dataProcessorService.processExtractedData(inputList, refInputList);

    // Assert
    assertEquals(1, output.size());
    OutputResponseDto outputDto = output.get(0);
    assertEquals("field1field2", outputDto.getOutfield1());
    assertEquals("data1", outputDto.getOutfield2());
    assertEquals("data2data3", outputDto.getOutfield3());
    assertEquals(50.0, outputDto.getOutfield4());
    assertEquals(10.0, outputDto.getOutfield5());
  }

  @Test
  public void testProcessExtractedDataEmptyLists() {
    // Arrange
    List<InputFileRequestDto> inputList = new ArrayList<>();
    List<ReferenceInputFileRequestDto> refInputList = new ArrayList<>();

    // Act
    List<OutputResponseDto> output = dataProcessorService.processExtractedData(inputList, refInputList);

    // Assert
    assertTrue(output.isEmpty());
  }

  @Test
  public void testProcessExtractedDataNoMatchingReference() {
    // Arrange
    InputFileRequestDto inputDto1 = new InputFileRequestDto("key1", "field1", "field2", "field3", 5.0, "ref1", "ref2");
    ReferenceInputFileRequestDto refDto1 = new ReferenceInputFileRequestDto("key1", "data1", "data2", "data3", "data4", 10.0);

    List<InputFileRequestDto> inputList = List.of(inputDto1);
    List<ReferenceInputFileRequestDto> refInputList = List.of(refDto1);

    // Act
    List<OutputResponseDto> output = dataProcessorService.processExtractedData(inputList, refInputList);

    // Assert
    assertEquals(1, output.size());
    OutputResponseDto outputDto = output.get(0);
    assertEquals("field1field2", outputDto.getOutfield1());
    assertEquals(null, outputDto.getOutfield2());
    assertEquals(null, outputDto.getOutfield3());
    assertEquals(0.0, outputDto.getOutfield4());
    assertEquals(0.0, outputDto.getOutfield5());
  }
}
