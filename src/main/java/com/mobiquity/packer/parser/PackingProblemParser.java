package com.mobiquity.packer.parser;

import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.PackingProblem;

interface PackingProblemParser {
	PackingProblem parse(String line) throws APIException;
}
