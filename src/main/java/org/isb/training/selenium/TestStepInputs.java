package org.isb.training.selenium;

public class TestStepInputs {
	
	String testStepsSerialNumber;
	String testStepCaseNumber;
	String testStepsDescription;
	String webElement;
	String webElementValue;
	String inputValue;
	String testStepsKeyword;
	
	
	public TestStepInputs() {
		// No arg constructor
	}
	
	public TestStepInputs(String testStepsSerialNumber, String testStepCaseNumber, String testStepsDescription,
			String webElement, String webElementValue, String inputValue, String testStepsKeyword) {
		super();
		this.testStepsSerialNumber = testStepsSerialNumber;
		this.testStepCaseNumber = testStepCaseNumber;
		this.testStepsDescription = testStepsDescription;
		this.webElement = webElement;
		this.webElementValue = webElementValue;
		this.inputValue = inputValue;
		this.testStepsKeyword = testStepsKeyword;
	}

	public String getTestStepsSerialNumber() {
		return testStepsSerialNumber;
	}
	public void setTestStepsSerialNumber(String testStepsSerialNumber) {
		this.testStepsSerialNumber = testStepsSerialNumber;
	}
	public String getTestStepCaseNumber() {
		return testStepCaseNumber;
	}
	public void setTestStepCaseNumber(String testStepCaseNumber) {
		this.testStepCaseNumber = testStepCaseNumber;
	}
	public String getTestStepsDescription() {
		return testStepsDescription;
	}
	public void setTestStepsDescription(String testStepsDescription) {
		this.testStepsDescription = testStepsDescription;
	}
	public String getWebElement() {
		return webElement;
	}
	public void setWebElement(String webElement) {
		this.webElement = webElement;
	}
	public String getWebElementValue() {
		return webElementValue;
	}
	public void setWebElementValue(String webElementValue) {
		this.webElementValue = webElementValue;
	}
	public String getInputValue() {
		return inputValue;
	}
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
	public String getTestStepsKeyword() {
		return testStepsKeyword;
	}
	public void setTestStepsKeyword(String testStepsKeyword) {
		this.testStepsKeyword = testStepsKeyword;
	}

	@Override
	public String toString() {
		return "TestStepInputs [testStepsSerialNumber=" + testStepsSerialNumber + ", testStepCaseNumber="
				+ testStepCaseNumber + ", testStepsDescription=" + testStepsDescription + ", webElement=" + webElement
				+ ", webElementValue=" + webElementValue + ", inputValue=" + inputValue + ", testStepsKeyword="
				+ testStepsKeyword + "]";
	}
	
}
