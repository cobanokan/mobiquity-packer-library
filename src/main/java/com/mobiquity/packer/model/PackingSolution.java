package com.mobiquity.packer.model;

import java.util.Collections;
import java.util.List;

public class PackingSolution{
	
	//Since the order of indices is important in the output we use List
	private List<Integer> indices;
	
	private int totalCost;
	
	private double totalWeight;

	public PackingSolution(List<Integer> indices, int totalCost, double totalWeight) {
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
}
