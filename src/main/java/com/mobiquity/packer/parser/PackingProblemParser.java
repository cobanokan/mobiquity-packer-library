package com.mobiquity.packer.parser;

import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.PackingProblem;

//Interface can be implemented for different problem format
interface PackingProblemParser {
	PackingProblem parse(String line) throws APIException;
}
