package com.mobiquity.packer;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.PackingSolution;

class PackingSolutionTest {

	@Test
	void shouldReturnClonedSolutionWithAddedNewItem() {
		Item firstItem = new Item(1, 10.9, 10);
		List<Integer> indices = new ArrayList<Integer>();
		indices.add(firstItem.getIndex());
		
		PackingSolution solutionWithFirstItem = new PackingSolution(indices, firstItem.getCost(), firstItem.getWeight());
		
		assertEquals(1, solutionWithFirstItem.getIndices().size());
		assertEquals(firstItem.getIndex(), solutionWithFirstItem.getIndices().get(0));
		assertEquals(firstItem.getCost(), solutionWithFirstItem.getTotalCost());
		assertEquals(firstItem.getWeight(), solutionWithFirstItem.getTotalWeight());

		Item secondItem = new Item(2, 1.23, 20);
		
		PackingSolution solutionWithSecondItem = PackingSolutionUtil.cloneSolutionWithNewItem(solutionWithFirstItem, secondItem);
		
		assertEquals(2, solutionWithSecondItem.getIndices().size());
		assertEquals(secondItem.getIndex(), solutionWithSecondItem.getIndices().get(1));
		assertEquals(firstItem.getCost() + secondItem.getCost(), solutionWithSecondItem.getTotalCost());
		assertEquals(firstItem.getWeight() + secondItem.getWeight(), solutionWithSecondItem.getTotalWeight());
	}
	
	@Test
	void outputForEmptySolution() {
		PackingSolution empty = PackingSolution.empty();
		
		assertEquals("-", PackingSolutionUtil.getOutput(empty));
	}
	
	@Test
	void outputWithOneItem() {
		List<Integer> indices = new ArrayList<Integer>();
		indices.add(1);

		PackingSolution solutionWithNewItem = new PackingSolution(indices, 10, 10.9);
		
		assertEquals("1", PackingSolutionUtil.getOutput(solutionWithNewItem));
	}
	
	@Test
	void outputWithMultipleItemsShouldBeOrdered() {
		List<Integer> indices = new ArrayList<Integer>();
		indices.add(2);
		indices.add(1);
		indices.add(3);

		PackingSolution solution = new PackingSolution(indices, 90, 56.65);
		
		assertEquals("1,2,3", PackingSolutionUtil.getOutput(solution));
	}
	
	@Test
	void shouldPickSolutionWithHigherTotalCost() {
		List<Integer> indices = new ArrayList<Integer>();
		indices.add(1);
		
		PackingSolution cheapSolution = new PackingSolution(indices, 10, 5.6);
		PackingSolution expensiveSolution = new PackingSolution(indices, 20, 3.5);
		
		PackingSolution solution = PackingSolutionUtil.pickBetterSolution(cheapSolution, expensiveSolution);
		assertEquals(expensiveSolution, solution);
	}
	
	@Test
	void whenCostsAreEqualLighterSolutionShouldBePrefered() {
		List<Integer> indices = new ArrayList<Integer>();
		indices.add(1);
		
		PackingSolution heavySolution = new PackingSolution(indices, 20, 5.6);
		PackingSolution lightSolution = new PackingSolution(indices, 20, 3.5);
		
		PackingSolution solution = PackingSolutionUtil.pickBetterSolution(heavySolution, lightSolution);
		
		assertEquals(lightSolution, solution);
	}
}
