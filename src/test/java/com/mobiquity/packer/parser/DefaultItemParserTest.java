package com.mobiquity.packer.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.Item;

class DefaultItemParserTest {

	private static final String VALID_INPUT = "1,24.22,€45";
	private static final String INPUT_EXCEEDING_MAX_ALLOWED_WEIGHT = "1,100.22,€45";
	private static final String INPUT_EXCEEDING_MAX_ALLOWED_COST = "1,45.22,€110";
	
	private ItemParser parser = new DefaultItemParser();
	
	@Test
	void shouldParseItemFromString() throws APIException {
		Item item = parser.parse(VALID_INPUT);
		
		assertNotNull(item);
		assertEquals(1, item.getIndex());
		assertEquals(24.22, item.getWeight());
		assertEquals(45, item.getCost());
	}
	
	@Test
	void shouldThrowExceptionOnExceedingMaxAllowedItemWeight() {
		Throwable exceptionThatWasThrown = assertThrows(APIException.class, () -> {
			parser.parse(INPUT_EXCEEDING_MAX_ALLOWED_WEIGHT);
		});
		
		assertEquals("Max allowed item weight exceeded", exceptionThatWasThrown.getMessage());
	}
	
	@Test
	void shouldThrowExceptionOnExceedingMaxAllowedItemValue() {
		Throwable exceptionThatWasThrown = assertThrows(APIException.class, () -> {
			parser.parse(INPUT_EXCEEDING_MAX_ALLOWED_COST);
		});
		
		assertEquals("Max allowed item cost exceeded", exceptionThatWasThrown.getMessage());
	}

}
