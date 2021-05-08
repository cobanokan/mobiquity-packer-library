package com.mobiquity.packer.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.PackingProblem;
import com.mobiquity.packer.model.PackingSolution;


/*
 * The problem we are handling is a customization of famous knapsack problem
 * However item weights are decimal and item indices are expected instead of total cost
 * Brute force solution would be using recursion and checking all the possible combinations.
 * With this approach we calculate the same subproblems multiple times.
 * We should order the items by weight to make sure we are picking lighter item when costs are equal
 * Brute force solution computation complexity would be O(2^n + nlogn) and spatial complexity O(1)
 * For optimization on recursive solution we can use memoization by keeping a 2D array (solution matrix)
 * As we calculate the solutions for subproblems we fill the matrix and reuse the solution from the matrix when it is needed again.
 * We should order the items by weight to make sure we are picking lighter item when costs are equal
 * Since we have decimal item weights we need to know how many digits there could be in decimal part and multiply weights with 10^decimal_precision
 * Computational complexity is O(n*W*10^D + nlogn) where n is number of items, W is package weight and D is max number of digits in fractional part
 * Spatial complexity is O(n*W*10^D) (size of our solution matrix)
 * If fractional part has a high number of digits this solution becomes less efficient
 * Another efficient and cleaner solution would be using dynamic programing with memoization.
 * With this approach we iterate over items and try to calculate the best possible solution for weight values from 0 to target weight.
 * We keep a 2D array (solution matrix) and fill the matrix either by choosing the current item or by keeping the solution without current item.
 * Since we have decimal item weights we need to know how many digits there could be in decimal part and multiply weights with 10^decimal_precision
 * It increases the number of iterations but makes the solution more precise.
 * Before applying this solution we sort the items by weight to make sure that lighter item is picked in case the costs are equal
 * Computational complexity is O(n*W*10^D + nlogn) where n is number of items, W is package weight and D is max number of digits in fractional part
 * Spatial complexity is O(n*W*10^D) (size of our solution matrix).
 * If fractional part has a high number of digits this solution becomes less efficient
 * In this solution we use dynamic programing since it is scalable and cleaner than using recursion.
 * For simplicity we will assume that there could be only 2 digits in the fractional part of item weights.
 */
public class PackingProblemSolver {
	static Logger log = LogManager.getLogger(PackingProblemSolver.class.getName());

	//We are assuming weight can have only 2 decimal points
	private static final int DECIMAL_MULTIPLIER = 100;

	public String solve(PackingProblem problem) {
		log.debug("Solving packing problem {}", problem);
		
		//Sorting items by weight to make sure that we pick the lighter item if costs are equal
		final List<Item> items = problem.getItems()
				.stream()
				.sorted(getItemWeightComparator())
				.collect(Collectors.toList());
		
		final int itemsSize = items.size();
		final int weightWithoutDecimal = problem.getWeight() * DECIMAL_MULTIPLIER;
		
		//We will use array of array to keep best solution matrix for each item for the target weight
		final PackingSolution solutionMatrix[][] = new PackingSolution[itemsSize + 1][weightWithoutDecimal + 1];
		
		for (int currentItemPosition = 0; currentItemPosition <= itemsSize; currentItemPosition++) {
			for (int currentMaxWeight = 0; currentMaxWeight <= weightWithoutDecimal; currentMaxWeight++) {
				//Fill the first row and column with empty solution
				if(currentItemPosition == 0 || currentMaxWeight == 0) {
					solutionMatrix[currentItemPosition][currentMaxWeight] = PackingSolution.empty();
				} else {
					Item currentItem = items.get(currentItemPosition - 1);
					
					//If we should not take the current item best solution would be same as solution for previous item
					PackingSolution solutionWithoutCurrentItem = solutionMatrix[currentItemPosition - 1][currentMaxWeight];
					solutionMatrix[currentItemPosition][currentMaxWeight] = solutionWithoutCurrentItem;
					
					int currentItemWeightWithoutDecimal = (int) Math.round(currentItem.getWeight() * DECIMAL_MULTIPLIER);
					if(currentItemWeightWithoutDecimal <= currentMaxWeight) {
						//To get the best solution that we can add our item, we check the max weight without currentItem weight
						int weightWithoutCurrentItem = currentMaxWeight - currentItemWeightWithoutDecimal;
						
						PackingSolution bestSolutionToBeUsedWithCurrentItem = solutionMatrix[currentItemPosition - 1][weightWithoutCurrentItem];
						
						PackingSolution solutionWithNewItem = bestSolutionToBeUsedWithCurrentItem.solutionWithNewItem(currentItem);

						//If solution including current item is better(higher cost/weight) we use the new solution
						if (solutionWithNewItem.compareTo(solutionWithoutCurrentItem) > 0) {
							solutionMatrix[currentItemPosition][currentMaxWeight] = solutionWithNewItem;
						}
					}
				}
				
			}
		}
		
		return solutionMatrix[itemsSize][weightWithoutDecimal].getOutput();
	}

	private Comparator<Item> getItemWeightComparator() {
		return new Comparator<Item>() {
			
			@Override
			public int compare(Item arg0, Item arg1) {
				return Double.compare(arg0.getWeight(), arg1.getWeight());
			}
		};
	}
}
