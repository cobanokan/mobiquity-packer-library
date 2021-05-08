package com.mobiquity.packer.parser;

import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.Item;

//Interface can be implemented for different Item format
interface ItemParser {
	Item parse(String itemAsString) throws APIException;
}
