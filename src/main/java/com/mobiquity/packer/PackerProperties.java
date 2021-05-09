package com.mobiquity.packer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Reads constraints from properties file if cannot find the file default values are used
public class PackerProperties {
	private static Integer DEFAULT_MAX_PACKAGE_WEIGHT = 100;
	private static Integer DEFAULT_MAX_ITEM_WEIGHT = 100;
	private static Integer DEFAULT_MAX_ITEM_COST = 100;
	private static Integer DEFAULT_MAX_ITEM_NUMBER = 15;
	
	private static final Logger log = LoggerFactory.getLogger(PackerProperties.class.getName());

	private static Properties prop;
	
	public static int getMaxItemNumber() {
		if(prop == null) {
			loadProperties();
		}
		return Integer.valueOf(prop.getProperty("item.max.number", DEFAULT_MAX_ITEM_NUMBER.toString()));
	}
	
	public static int getMaxItemCost() {
		if(prop == null) {
			loadProperties();
		}
		return Integer.valueOf(prop.getProperty("item.max.cost", DEFAULT_MAX_ITEM_COST.toString()));
	}

	public static int getMaxItemWeight() {
		if(prop == null) {
			loadProperties();
		}
		return Integer.valueOf(prop.getProperty("item.max.weight", DEFAULT_MAX_ITEM_WEIGHT.toString()));
	}
	
	public static int getMaxPackageWeight() {
		if(prop == null) {
			loadProperties();
		}
		return Integer.valueOf(prop.getProperty("package.max.weight", DEFAULT_MAX_PACKAGE_WEIGHT.toString()));
	}
	
	private static void loadProperties() {
		prop = new Properties();
		try (InputStream input = new FileInputStream("src/main/resources/packer.properties")) {
			prop.load(input);
		} catch (IOException ex) {
			log.warn("Could not load packer.properties. Default values will be used");
		}
	}
}
