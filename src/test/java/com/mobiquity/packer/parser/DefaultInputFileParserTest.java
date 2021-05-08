package com.mobiquity.packer.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.PackingProblem;

@ExtendWith(MockitoExtension.class)
class DefaultInputFileParserTest {

	@Mock
	private PackingProblemParser packingProblemParser;
	
	private InputFileParser parser;;
	
	@BeforeEach
	void setUp() {
		parser = new DefaultInputFileParser(packingProblemParser);
	}
	
	@Test
	void returnsParsedPackingProblemsFromInputFile() throws APIException {
		PackingProblem packingProblem = new PackingProblem(90, Collections.emptyList());
		
		when(packingProblemParser.parse(anyString())).thenReturn(packingProblem);
		
		List<PackingProblem> problems = parser.parse("src/test/resources/example_input");
		
		assertEquals(4, problems.size());
		assertEquals(packingProblem, problems.get(0));
		
		verify(packingProblemParser).parse("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
		verify(packingProblemParser).parse("8 : (1,15.3,€34)");
		verify(packingProblemParser).parse("75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)");
		verify(packingProblemParser).parse("56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)");
		verifyNoMoreInteractions(packingProblemParser);
	}
	
	@Test
	void throwsExceptionForInvalidFilPath() throws APIException {
		Throwable thrownException = assertThrows(APIException.class, () -> {
			parser.parse("invalid_file_path");
		});
		
		assertEquals("Could not read file", thrownException.getMessage());
		verifyNoMoreInteractions(packingProblemParser);
	}
}
