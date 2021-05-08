package com.mobiquity.packer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mobiquity.packer.exception.APIException;

class PackerTest {

	@Test
	void shouldSolvePackingProblems() throws APIException {
		String solution = Packer.pack("src/test/resources/example_input");
		
		assertEquals("4\n" + 
				"-\n" + 
				"2,7\n" + 
				"8,9", solution);
	}
	
	@Test
	void shouldThrowExceptionForNullFilePath() {
		Throwable thrownException = assertThrows(APIException.class, () -> Packer.pack(null));
		assertEquals("Filepath can not be null", thrownException.getMessage());
	}
	
	@Test
	void throwsExceptionForInvalidInputFormat() throws APIException {
		Throwable thrownException = assertThrows(APIException.class, () -> Packer.pack("src/test/resources/input_invalid_format"));
		assertEquals("Invalid input format", thrownException.getMessage());
	}
}
