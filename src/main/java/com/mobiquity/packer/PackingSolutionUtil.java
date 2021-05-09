package com.mobiquity.packer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.PackingSolution;

class PackingSolutionUtil {
	
	
	/**
	 * @param first first packing solution to compare
	 * @param second second packing solution to compare
	 * @return solution which has better cost/weight value
	 */
	static PackingSolution pickBetterSolution(PackingSolution first, PackingSolution second) {
		//solution with higher cost is better
		int costComparison = first.getTotalCost() - second.getTotalCost();

		//solution with less weight is better
		int weightComparison = Double.compare(second.getTotalWeight(), first.getTotalWeight());
		
		int comparison = costComparison == 0 ? weightComparison : costComparison;
		
		//solution with higher density is better
		if(comparison > 0) {
			return first;
		}
		return second;
	}
	
	/**
	 * @param solution solution to get output
	 * @return '-'  if no index found or comma seperated ordered indices  
	 */
	static String getOutput(PackingSolution solution) {
		if (solution.getIndices().isEmpty()) {
			return "-";
		}
		return solution.getIndices()
			.stream()
			.sorted()
			.map(i -> String.valueOf(i))
			.collect(Collectors.joining(","));
	}
	
	/**
	 * @param solution base solution to clone with new item
	 * @param item item to be included in new solution
	 * @return new solution with added item
	 */
	static PackingSolution cloneSolutionWithNewItem(PackingSolution solution, Item item) {
		List<Integer> indicesWithNewItem = new ArrayList<Integer>(solution.getIndices());
		indicesWithNewItem.add(item.getIndex());
		
		return new PackingSolution(
				indicesWithNewItem,
				solution.getTotalCost() + item.getCost(),
				solution.getTotalWeight() + item.getWeight());
	}
}
