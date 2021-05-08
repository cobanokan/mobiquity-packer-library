package com.mobiquity.packer.parser;

public class InputFileParserFactory {
	
	private static InputFileParser defaultInputFileParser;
	
	private InputFileParserFactory() {
	}	
	
	public static InputFileParser getDefault() {
		if (defaultInputFileParser == null) {
			ItemParser itemParser = new DefaultItemParser();
			PackingProblemParser packingProblemParser = new DefaultPackingProblemParser(itemParser);
			defaultInputFileParser = new DefaultInputFileParser(packingProblemParser);
		}
		return defaultInputFileParser;
	}

}
