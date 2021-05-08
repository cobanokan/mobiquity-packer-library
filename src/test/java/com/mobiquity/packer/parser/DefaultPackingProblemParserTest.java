package com.mobiquity.packer.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.PackingProblem;

@ExtendWith(MockitoExtension.class)
class DefaultPackingProblemParserTest {

	private static final String EXAMPLE_INPUT = "81 : (1,53.38,€45) (2,78.48,€3) (3,72.30,€76)";
	private static final String INPUT_WITH_ITEM_WEIGHT_EXCEEDING_MAX_WEIGHT = "81 : (1,83.38,€45) (2,78.48,€3)";
	private static final String INPUT_WITH_PACKAGE_WEIGHT_EXCEEDING_MAX_WEIGHT_CONSTRAINT = "110 : (1,55.55,€34)";
	private static final String INPUT_WITH_ITEM_NUMBER_EXCEEDING_MAX_ALLOWED_ITEM_NUMBER = "50 : (1,53.38,€45) (2,78.48,€3) (3,72.30,€76) (4,30.18,€9) (5,46.34,€48) (6,53.38,€45) (7,78.48,€3) (8,72.30,€76) (9,30.18,€9) (10,46.34,€48) (11,53.38,€45) (12,78.48,€3) (13,72.30,€76) (14,30.18,€9) (15,46.34,€48) (16,55.55,€4)";

	@Mock
	private ItemParser itemParser;
	
	private PackingProblemParser parser;
	
	@BeforeEach
	void setUp() {
		parser = new DefaultPackingProblemParser(itemParser);
	}
	
	@Test
	void convertsInputLineToPackingProblem() throws APIException {
		Item item = new Item(1, 1.0, 1);
		
		when(itemParser.parse(anyString())).thenReturn(item);
		
		PackingProblem packingProblem = parser.parse(EXAMPLE_INPUT);
		
		assertNotNull(packingProblem);
		assertEquals(81, packingProblem.getWeight());
		assertEquals(3, packingProblem.getItems().size());
		assertEquals(item, packingProblem.getItems().get(0));
		
		verify(itemParser).parse(eq("1,53.38,€45"));
		verify(itemParser).parse(eq("2,78.48,€3"));
		verify(itemParser).parse(eq("3,72.30,€76"));
		verifyNoMoreInteractions(itemParser);
	}

	@Test
	void ignoresItemHavingWeightLargerThanMaxWeight() throws APIException {
		Item itemWithExceedingWeight = new Item(1, 83.38, 45);
		Item validItem = new Item(2, 78.8, 3);
		
		when(itemParser.parse(anyString())).thenReturn(itemWithExceedingWeight, validItem);
		
		PackingProblem packingProblem = parser.parse(INPUT_WITH_ITEM_WEIGHT_EXCEEDING_MAX_WEIGHT);
		
		assertNotNull(packingProblem);
		assertEquals(81, packingProblem.getWeight());
		assertEquals(1, packingProblem.getItems().size());
		assertEquals(validItem, packingProblem.getItems().get(0));
		
		verify(itemParser).parse(eq("1,83.38,€45"));
		verify(itemParser).parse(eq("2,78.48,€3"));
		verifyNoMoreInteractions(itemParser);
	}
	
	@Test
	void throwsExceptionWhenPackageWeightExceedsMaxWeightConstraint() {
		Throwable thrownException = assertThrows(APIException.class, () -> {
			parser.parse(INPUT_WITH_PACKAGE_WEIGHT_EXCEEDING_MAX_WEIGHT_CONSTRAINT);
		});
		assertEquals("Package weight exceeds max package weight constraint", thrownException.getMessage());
		
		verifyNoMoreInteractions(itemParser);
	}
	
	@Test
	void throwsExceptionForItemNumberExceedingMaxAllowedItemNumber() {
		Throwable thrownException = assertThrows(APIException.class, () -> {
			parser.parse(INPUT_WITH_ITEM_NUMBER_EXCEEDING_MAX_ALLOWED_ITEM_NUMBER);
		});
		assertEquals("Number of items exceeds max allowed item number", thrownException.getMessage());		

		verifyNoMoreInteractions(itemParser);
	}
}
