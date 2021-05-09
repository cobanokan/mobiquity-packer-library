package com.mobiquity.packer.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobiquity.packer.PackerProperties;
import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.Item;

class DefaultItemParser implements ItemParser {

	private static final Logger log = LoggerFactory.getLogger(DefaultItemParser.class.getName());
	
	public Item parse(String itemAsString) throws APIException {
		log.debug("Parsing item {}", itemAsString);
		
		String[] split = itemAsString.split(",");
		
		int index = Integer.valueOf(split[0]);
		
		double weight = Double.valueOf(split[1]);
		if (weight > PackerProperties.getMaxItemWeight()) {			
			log.error("Item weight {} exceeds max allowed weight {}", weight, PackerProperties.getMaxItemWeight());
			throw new APIException("Max allowed item weight exceeded");
		}
		
		String valueWithoutCurrency = split[2].substring(1);
		int cost = Integer.valueOf(valueWithoutCurrency);
		if (cost > PackerProperties.getMaxItemCost()) {
			log.error("Item cost {} exceeds max allowed cost {}", weight, PackerProperties.getMaxItemCost());
			throw new APIException("Max allowed item cost exceeded");			
		}

		return new Item(index, weight, cost);
	}

}
