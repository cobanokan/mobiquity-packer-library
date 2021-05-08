package com.mobiquity.packer.parser;

public class InputFileParserFactory {
	
	public static InputFileParser getDefault() {
		ItemParser itemParser = new DefaultItemParser();
		PackingProblemParser packingProblemParser = new DefaultPackingProblemParser(itemParser);
		return new DefaultInputFileParser(packingProblemParser);
	}

}
