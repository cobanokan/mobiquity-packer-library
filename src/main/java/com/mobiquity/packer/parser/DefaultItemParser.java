package com.mobiquity.packer.parser;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.mobiquity.packer.constant.ConstraintConstants;
import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.Item;

class DefaultItemParser implements ItemParser {

	static Logger log = LogManager.getLogger(DefaultItemParser.class.getName());

	public Item parse(String itemAsString) throws APIException {
		log.debug("Parsing item {}", itemAsString);
		
		String[] split = itemAsString.split(",");
		
		int index = Integer.valueOf(split[0]);
		
		double weight = Double.valueOf(split[1]);
		if (weight > ConstraintConstants.MAX_ITEM_WEIGHT) {			
			log.error("Item weight {} exceeds max allowed weight {}", weight, ConstraintConstants.MAX_ITEM_WEIGHT);
			throw new APIException("Max allowed item weight exceeded");
		}
		
		String valueWithoutCurrency = split[2].substring(1);
		int cost = Integer.valueOf(valueWithoutCurrency);
		if (cost > ConstraintConstants.MAX_ITEM_COST) {
			log.error("Item cost {} exceeds max allowed cost {}", weight, ConstraintConstants.MAX_ITEM_COST);
			throw new APIException("Max allowed item cost exceeded");			
		}

		return new Item(index, weight, cost);
	}

}
