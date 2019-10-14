package org.isb.training.selenium;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Driver {

	final static Logger logger = Logger.getLogger(Driver.class);
	WebDriver driver;
	ConfigFileReader configFileReader;

	public WebDriver InitateDriver()
	{

		logger.info("Opening Browser");

		configFileReader = new ConfigFileReader();
		System.out.println("Configuration File Reader : Browser -> " + configFileReader.getBrowser());
		System.out.println("Configuration File Reader : Driver Path -> " + configFileReader.getDriverPath());

		if(configFileReader.getBrowser().equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", configFileReader.getDriverPath());
			driver = new FirefoxDriver();
			logger.info("Mozilla browser started");				
		}
		else if(configFileReader.getBrowser().equalsIgnoreCase("IE")) {
			System.out.println("In IE ...");
			System.setProperty("webdriver.ie.driver", configFileReader.getDriverPath());
			driver = new InternetExplorerDriver();
			logger.info("IE browser started");
		}
		else if(configFileReader.getBrowser().equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", configFileReader.getDriverPath());
			driver = new ChromeDriver();
			logger.info("Chrome browser started");
		}

		int implicitWaitTime=(10);
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		// capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
		//driver.manage().window().setSize(screenResolution);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;

	}


}
