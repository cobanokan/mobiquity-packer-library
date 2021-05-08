package com.mobiquity.packer.parser;

import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.Item;

interface ItemParser {
	Item parse(String itemAsString) throws APIException;
}
