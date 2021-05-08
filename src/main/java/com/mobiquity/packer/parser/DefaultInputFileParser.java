package com.mobiquity.packer.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.PackingProblem;

class DefaultInputFileParser implements InputFileParser {
	
	static Logger log = LogManager.getLogger(DefaultInputFileParser.class.getName());

	private PackingProblemParser packingProblemParser;
	
	//Dependency injection is used to have loose coupling
	public DefaultInputFileParser(PackingProblemParser packingProblemParser) {
		this.packingProblemParser = packingProblemParser;
	}
	
	public List<PackingProblem> parse(String filePath) throws APIException {
		
		log.debug("Parsing input file at {}", filePath);
		
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
        	List<String> linesList= lines.collect(Collectors.toList());
            
            List<PackingProblem> problems = new ArrayList<PackingProblem>();
            for (String line : linesList) {
            	problems.add(packingProblemParser.parse(line));
            }
            
            return problems;
        } catch (IOException e) {
        	log.error("Failed to parse file {}", filePath, e);
			throw new APIException("Could not read file", e);
		}
	}
}
