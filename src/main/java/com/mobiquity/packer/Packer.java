package com.mobiquity.packer;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.PackingProblem;
import com.mobiquity.packer.parser.InputFileParserFactory;

//Facade design pattern is used. Complexity of the library is hidden to outside
public class Packer {

	private static final Logger log = LoggerFactory.getLogger(Packer.class.getName());
	
	private Packer() {
		
	}

	/**
	 * 
	 * @param filePath path to the file to be processed
	 * @return indices of the items used in the optimal solution for each line in the input file
	 * @throws APIException
	 */
	public static String pack(String filePath) throws APIException {
		log.info("Packing input file at {}", filePath);
		if (filePath == null) {
			log.error("Filepath can not be null");
			throw new APIException("Filepath can not be null");
		}
	
		List<PackingProblem> problems = InputFileParserFactory.getDefault().parse(filePath);
		
		log.info("Found {} packing problem", problems.size());

		PackingProblemSolver solver = new PackingProblemSolver();
		
		return problems
			.stream()
			.map(problem -> solver.solve(problem))
			.collect(Collectors.joining("\n"));
	}
}
