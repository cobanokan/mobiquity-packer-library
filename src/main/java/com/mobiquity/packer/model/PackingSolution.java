package com.mobiquity.packer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PackingSolution implements Comparable<PackingSolution> {
	
	//Since the order of indices is important in the output we use List
	private List<Integer> indices;
	
	private int totalCost;
	
	private double totalWeight;

	private PackingSolution(List<Integer> indices, int totalCost, double totalWeight) {
		this.indices = indices;
		this.totalCost = totalCost;
		this.totalWeight = totalWeight;
	}
	
	public static PackingSolution empty() {
		return new PackingSolution(Collections.emptyList(), 0, 0);
	}
	
	public List<Integer> getIndices() {
		return indices;
	}

	public int getTotalCost() {
		return totalCost;
	}
	
	public double getTotalWeight() {
		return totalWeight;
	}

	@Override
	public int compareTo(PackingSolution arg0) {
		//If totalCosts are equal lighter solution is better
		int costComparison = this.totalCost - arg0.getTotalCost();
		return costComparison == 0 ? 
				Double.compare(arg0.getTotalWeight(), this.totalWeight) :
				costComparison;
	}
	
	public PackingSolution solutionWithNewItem(Item item) {
		List<Integer> indicesWithNewItem = new ArrayList<Integer>(indices);
		indicesWithNewItem.add(item.getIndex());
		
		return new PackingSolution(
				indicesWithNewItem,
				totalCost + item.getCost(),
				totalWeight + item.getWeight());
	}

	public String getOutput() {
		if (indices.isEmpty()) {
			return "-";
		}
		return indices
			.stream()
			.sorted()
			.map(i -> String.valueOf(i))
			.collect(Collectors.joining(","));
	}
}
