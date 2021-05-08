package com.mobiquity.packer.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mobiquity.packer.constant.ConstraintConstants;
import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.PackingProblem;

class DefaultPackingProblemParser implements PackingProblemParser {
	
	private static Logger log = LogManager.getLogger(DefaultPackingProblemParser.class.getName());

	private ItemParser itemParser;
	
	DefaultPackingProblemParser(ItemParser itemParser) {
		this.itemParser = itemParser;
	}
	
	public PackingProblem parse(String inputLine) throws APIException {
		log.debug("Parsing packing problem {}", inputLine);
		
		String[] split = inputLine.split(":");
		
		Integer packageWeight = Integer.valueOf(split[0].trim());
		if (packageWeight > ConstraintConstants.MAX_PACKAGE_WEIGHT) {
			log.error("Package weight {} exceedes max allowed package weight {}", packageWeight, ConstraintConstants.MAX_PACKAGE_WEIGHT);
			throw new APIException("Package weight exceeds max package weight constraint");
		}
		
		String itemsStringSeperatedBySpace = split[1].substring(1)
				.replace("(", "")
				.replace(")", "");
		
		List<String> itemsAsStringList = Arrays.asList(itemsStringSeperatedBySpace.split(" "));
		if (itemsAsStringList.size() > ConstraintConstants.MAX_ITEM_NUMBER) {
			log.error("Max allowed item number {} exceeded", ConstraintConstants.MAX_ITEM_NUMBER);
			throw new APIException("Number of items exceeds max allowed item number");
		}
		
		List<Item> items = new ArrayList<Item>();
		for (String itemAsString : itemsAsStringList) {
			Item item = itemParser.parse(itemAsString);
			
			//For optimization we ignore items heavier than package weight
			if (item.getWeight() < packageWeight) {				
				items.add(item);
			}
		}
		
		return new PackingProblem(packageWeight, items);
		
	}
}
