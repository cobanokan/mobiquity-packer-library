package com.mobiquity.packer.model;

public class Item {

	private int index;
	
	private double weight;
	
	private int cost;
	
	public Item(int index, double weight, int cost) {
		this.index = index;
		this.weight = weight;
		this.cost = cost;
	}
	
	public int getIndex() {
		return index;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public int getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return "Item [index=" + index + ", weight=" + weight + ", cost=" + cost + "]";
	}
}
