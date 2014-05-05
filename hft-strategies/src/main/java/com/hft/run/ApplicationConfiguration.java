package com.hft.run;

import java.io.File;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/*
 * TODO : create interface for api 
 * to have it used both on testing and production without overhead of work I am using the Singleton pattern
 * with a PARAMETER ...to be refacored later
 */

public class ApplicationConfiguration {

	static {
		initializeLog4J();
	}

	public static Configuration get() {
		try {
			CompositeConfiguration config = new CompositeConfiguration();
			config.addConfiguration(new SystemConfiguration());
			config.addConfiguration(new PropertiesConfiguration(new File("etc/strategies.properties")));
			return config;
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}

	}

	protected static void initializeLog4J() {

		String log4jPath = get().getString("log4j.path");
		if (log4jPath != null) {
			PropertyConfigurator.configure(log4jPath);
			Logger.getLogger(ApplicationConfiguration.class).info("log4j configured by " + log4jPath);
		}
	}
	
	public static String getVersion() {
		return get().getString("hft.version", "N/A");
	}
}
