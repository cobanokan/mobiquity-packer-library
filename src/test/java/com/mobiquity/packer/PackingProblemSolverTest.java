package com.mobiquity.packer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mobiquity.packer.PackingProblemSolver;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.PackingProblem;

class PackingProblemSolverTest {

	PackingProblemSolver solver = new PackingProblemSolver();
	
	@Test
	void solutionShouldBeEmptyForProblemWithNoItems() {
		PackingProblem problem = new PackingProblem(50, Collections.emptyList());
		
		String solution = solver.solve(problem);
		
		assertEquals("-", solution);
	}
	
	@Test
	void solutionShouldBeEmptyIfItemWeightExceedsPackageWeight() {
		Item item = new Item(1, 3, 90);
		
		PackingProblem problem = new PackingProblem(2, Collections.singletonList(item));
		
		String solution = solver.solve(problem);
		
		assertEquals("-", solution);
	}
	
	@Test
	void solutionShouldIncludeTheItemIfThereIsOnlyOneItemWithValidWeight() {
		Item item = new Item(1, 1.9, 90);
		
		PackingProblem problem = new PackingProblem(2, Collections.singletonList(item));
		
		String solution = solver.solve(problem);
		
		assertEquals("1", solution);
	}

	@Test
	void solutionShouldIncludeOnlyTheItemWithLargerCostIfTotalWeightExceedsPackageWeight() {
		Item item = new Item(1, 1.6, 10);
		Item itemWithLargerCost = new Item(2, 1.9, 20);
		
		List<Item> items = new ArrayList<Item>();
		items.add(item);
		items.add(itemWithLargerCost);
		
		PackingProblem problem = new PackingProblem(3, items);
		
		String solution = solver.solve(problem);
		
		assertEquals("2", solution);
	}

	@Test
	void solutionShouldIncludeItemsWithLargerCostInTotal() {
		Item item = new Item(1, 1.1, 10);
		Item item1ToInclude = new Item(2, 1.2, 30);
		Item item2 = new Item(3, 1.3, 20);
		Item item2ToInclude = new Item(4, 1.4, 40);
		
		List<Item> items = new ArrayList<Item>();
		items.add(item);
		items.add(item2);
		items.add(item1ToInclude);
		items.add(item2ToInclude);
		
		PackingProblem problem = new PackingProblem(3, items);
		
		String solution = solver.solve(problem);
		
		assertEquals("2,4", solution);
	}
	
	@Test
	void solutionShouldIncludeFirstItemToRespectWeightLimit() {
		Item item1ToInclude = new Item(1, 1.1, 20);
		Item item = new Item(2, 1.6, 30);
		Item item2 = new Item(3, 0.6, 10);
		Item item2ToInclude = new Item(4, 1.6, 40);
		
		List<Item> items = new ArrayList<Item>();
		items.add(item1ToInclude);
		items.add(item);
		items.add(item2);
		items.add(item2ToInclude);
		
		PackingProblem problem = new PackingProblem(3, items);
		
		String solution = solver.solve(problem);
		
		assertEquals("1,4", solution);
	}
	
	@Test
	void solutionShouldIncludeItemWithMinorWeightIfCostIsTheSame() {
		Item item1ToInclude = new Item(1, 1.2, 10);
		Item item = new Item(2, 1.4, 10);
		Item item2 = new Item(3, 1.6, 30);
		Item item2ToInclude = new Item(4, 1.6, 40);
		
		List<Item> items = new ArrayList<Item>();
		items.add(item1ToInclude);
		items.add(item);
		items.add(item2);
		items.add(item2ToInclude);
		
		PackingProblem problem = new PackingProblem(3, items);
		
		String solution = solver.solve(problem);
		
		assertEquals("1,4", solution);
	}
}
