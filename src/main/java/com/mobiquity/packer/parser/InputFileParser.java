package com.mobiquity.packer.parser;

import java.util.List;

import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.PackingProblem;

public interface InputFileParser {
	List<PackingProblem> parse(String filePath) throws APIException;
}
