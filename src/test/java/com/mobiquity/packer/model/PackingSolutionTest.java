package com.mobiquity.packer.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PackingSolutionTest {

	@Test
	void shouldReturnNewSolutionWithAddedNewItem() {
		PackingSolution empty = PackingSolution.empty();
		Item firstItem = new Item(1, 10.9, 10);
		
		PackingSolution solutionWithNewItem = empty.cloneSolutionWithNewItem(firstItem);
		
		assertEquals(1, solutionWithNewItem.getIndices().size());
		assertEquals(firstItem.getIndex(), solutionWithNewItem.getIndices().get(0));
		assertEquals(firstItem.getCost(), solutionWithNewItem.getTotalCost());

		Item secondItem = new Item(2, 1.23, 20);
		
		PackingSolution solutionWithSecondItem = solutionWithNewItem.cloneSolutionWithNewItem(secondItem);
		
		assertEquals(2, solutionWithSecondItem.getIndices().size());
		assertEquals(secondItem.getIndex(), solutionWithSecondItem.getIndices().get(1));
		assertEquals(firstItem.getCost() + secondItem.getCost(), solutionWithSecondItem.getTotalCost());
	}
	
	@Test
	void outputForEmptySolution() {
		PackingSolution empty = PackingSolution.empty();
		
		assertEquals("-", empty.getOutput());
	}
	
	@Test
	void outputWithOneItem() {
		PackingSolution empty = PackingSolution.empty();
		Item firstItem = new Item(1, 10.9, 10);
		
		PackingSolution solutionWithNewItem = empty.cloneSolutionWithNewItem(firstItem);
		
		assertEquals("1", solutionWithNewItem.getOutput());
	}
	
	@Test
	void outputWithMultipleItemsShouldBeOrdered() {
		Item firstItem = new Item(2, 10.9, 10);
		Item secondItem = new Item(1, 1.9, 20);
		Item thirdItem = new Item(3, 2.9, 30);
		
		PackingSolution empty = PackingSolution.empty();
		String output = empty.cloneSolutionWithNewItem(firstItem)
			.cloneSolutionWithNewItem(secondItem)
			.cloneSolutionWithNewItem(thirdItem)
			.getOutput();
		
		assertEquals("1,2,3", output);
	}
	
	@Test
	void solutionWithHigherTotalCostShouldBePrefered() {
		PackingSolution empty = PackingSolution.empty();
		Item firstItem = new Item(1, 10.9, 10);
		
		PackingSolution solutionWithNewItem = empty.cloneSolutionWithNewItem(firstItem);
		assertTrue(solutionWithNewItem.compareTo(empty) > 0);
		assertTrue(empty.compareTo(solutionWithNewItem) < 0);
	}
	
	@Test
	void whenCostsAreEqualLighterSolutionShouldBePrefered() {
		PackingSolution empty = PackingSolution.empty();
		Item firstItem = new Item(1, 19.9, 10);
		Item secondItem = new Item(2, 10.9, 10);
		
		PackingSolution solutionWithFirstItem = empty.cloneSolutionWithNewItem(firstItem);
		PackingSolution solutionWithSecondItem = empty.cloneSolutionWithNewItem(secondItem);
		assertTrue(solutionWithSecondItem.compareTo(solutionWithFirstItem) > 0);
		assertTrue(solutionWithFirstItem.compareTo(solutionWithSecondItem) < 0);
	}
}
