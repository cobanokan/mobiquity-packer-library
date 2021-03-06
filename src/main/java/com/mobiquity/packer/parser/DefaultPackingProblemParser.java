package com.mobiquity.packer.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobiquity.packer.PackerProperties;
import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.PackingProblem;

class DefaultPackingProblemParser implements PackingProblemParser {
	
	private static final Logger log = LoggerFactory.getLogger(DefaultPackingProblemParser.class.getName());

	private ItemParser itemParser;
	
	DefaultPackingProblemParser(ItemParser itemParser) {
		this.itemParser = itemParser;
	}
	
	public PackingProblem parse(String inputLine) throws APIException {
		log.debug("Parsing packing problem {}", inputLine);
		
		String[] split = inputLine.split(":");
		
		Integer packageWeight = Integer.valueOf(split[0].trim());
		if (packageWeight > PackerProperties.getMaxPackageWeight()) {
			log.error("Package weight {} exceedes max allowed package weight {}", packageWeight, PackerProperties.getMaxPackageWeight());
			throw new APIException("Package weight exceeds max package weight constraint");
		}
		
		String itemsStringSeperatedBySpace = split[1].substring(1)
				.replace("(", "")
				.replace(")", "");
		
		List<String> itemsAsStringList = Arrays.asList(itemsStringSeperatedBySpace.split(" "));
		if (itemsAsStringList.size() > PackerProperties.getMaxItemNumber()) {
			log.error("Max allowed item number {} exceeded", PackerProperties.getMaxItemNumber());
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
