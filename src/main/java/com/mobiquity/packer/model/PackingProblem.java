package com.mobiquity.packer.model;

import java.util.List;

public class PackingProblem {

	private int weight;
	
	//Since the order of items is important for picking lighter item when costs are equal we use List
	private List<Item> items;

	public PackingProblem(Integer weight, List<Item> items) {
		this.weight = weight;
		this.items = items;
	}

	public int getWeight() {
		return weight;
	}

	public List<Item> getItems() {
		return items;
	}
	
	@Override
	public String toString() {
		return "PackingProblem [weight=" + weight + ", items=" + items + "]";
	}
}
