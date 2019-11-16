package org.isb.training.selenium;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import jxl.Sheet;
import org.isb.training.selenium.ConfigFileReader;
import org.isb.training.selenium.Driver;
import org.isb.training.selenium.config.Constants;
import org.isb.training.selenium.KeywordFunctions;

public class RunTest {

	final static Logger logger = Logger.getLogger(RunTest.class);
	public static Result result;
	static XSSFWorkbook workbook;
	static XSSFSheet sheet1;
	static XSSFSheet sheet2;
	static XSSFSheet sheet3;
	Map<String, Object[]> data1;
	Map<String, Object[]> data2;
	Map<String, Object[]> data3;

	private boolean testCaseFlag = true;

	private WebDriver webDriver;
	Driver driver;
	FileOutputStream fileOutputStream;

	@BeforeClass
	public void beforeClass() throws IOException {

		driver = new Driver();
		fileOutputStream = new FileOutputStream(new File(Constants.FILE_PATH));

		// Blank workbook
		workbook = new XSSFWorkbook();

		// Create a blank sheet
		sheet2 = workbook.createSheet("Test Suite");
		// Create a blank sheet
		sheet3 = workbook.createSheet("Test Cases");
		// Create a blank sheet
		sheet1 = workbook.createSheet("Test Steps");

		// This data needs to be written (Object[])
		data2 = new TreeMap<String, Object[]>();
		// Header
		data2.put("2",
				new Object[] { "Serial Number", "Test Suite Description", "Execution Flag", "Test Suite Result" });

		// This data needs to be written (Object[])
		data1 = new TreeMap<String, Object[]>();
		// Header
		data1.put("1", new Object[] { "Test Case Number", "Test Step Number", "Test Step Description", "Element", "Value", "Keyword",
				"Test Result", "Test Result Description" });

		// This data needs to be written (Object[])
		data3 = new TreeMap<String, Object[]>();
		// Header
		data3.put("3", new Object[] { "Serial Number", "Test Case Number", "Test Case Description", "Execution Flag",
				"Test Suite Result" });

	}

	@AfterClass
	public void afterClass() {
		webDriver.quit();
	}

	@Test
	public void readExcel() throws Exception {

		// creating class object for configFileReader
		ConfigFileReader configFileReader = new ConfigFileReader();
		ExcelSheetDriver excelSheetDriver = new ExcelSheetDriver();
		ExcelSheetDriver excelSheetDriver1 = new ExcelSheetDriver();
		ExcelSheetDriver excelSheetDriver2 = new ExcelSheetDriver();

		// variable name testSuitesheet data type is Sheet
		Sheet testSuitesheet = excelSheetDriver.getWorksheet(configFileReader.getTestSuite(),
				configFileReader.getTestSuiteSheet());
		int c = excelSheetDriver.columnCount();
		int r = excelSheetDriver.rowCount();

		for (int i = 1; i < r; i++) {

			String serialNumber = excelSheetDriver.readCell(testSuitesheet, 0, i);
			String description = excelSheetDriver.readCell(testSuitesheet, 1, i);
			String executionFlag = excelSheetDriver.readCell(testSuitesheet, 2, i);
			System.out.println("TestSuite: serialNumber: " + serialNumber);
			System.out.println("TestSuite: description: " + description);
			System.out.println("TestSuite: executionFlag: " + executionFlag);

			if (executionFlag.equalsIgnoreCase("Y")) {
				Sheet TestCaseSheet = excelSheetDriver1.getWorksheet(configFileReader.getTestCasePath(), description);
				int rTestcase = excelSheetDriver1.rowCount(); // 5 rows
				int cTestCase = excelSheetDriver1.columnCount(); // 4 columns

				for (int k = 1; k < rTestcase; k++) {
					
					// Reset testCaseFlag
					testCaseFlag = true;
					
					String testCaseSerialNumber = excelSheetDriver1.readCell(TestCaseSheet, 0, k);
					String testCaseNumber = excelSheetDriver1.readCell(TestCaseSheet, 1, k);
					String testCaseDescription = excelSheetDriver1.readCell(TestCaseSheet, 2, k);
					String testCaseExecutionFlag = excelSheetDriver1.readCell(TestCaseSheet, 3, k);
					logger.info("TestCases: testCaseSerialNumber: " + testCaseSerialNumber);
					logger.info("TestCases: testCaseNumber: " + testCaseNumber);
					logger.info("TestCases: testCaseDescription: " + testCaseDescription);
					logger.info("TestCases: testCaseExecutionFlag: " + testCaseExecutionFlag);

					if (testCaseExecutionFlag.equalsIgnoreCase("y")) {

						Sheet testStepSheet = excelSheetDriver2.getWorksheet(configFileReader.getTestStepsPath(),
								description);
						int testStepsRow = excelSheetDriver2.rowCount(); // row count =44
						int testStepsColumn = excelSheetDriver2.columnCount(); // column counts=6
						webDriver = driver.InitateDriver();
						KeywordFunctions comlib = new KeywordFunctions(webDriver);
						TestStepInputs testStepInputs = new TestStepInputs();
						
						for (int w = 1; w < testStepsRow; w++) {
							String testStepsSerialNumber = excelSheetDriver.readCell(testStepSheet, 0, w);
							String testStepCaseNumber = excelSheetDriver.readCell(testStepSheet, 1, w);
							String testStepsDescription = excelSheetDriver.readCell(testStepSheet, 2, w);
							String webElement = excelSheetDriver.readCell(testStepSheet, 3, w);
							String webElementValue = excelSheetDriver.readCell(testStepSheet, 4, w);
							String inputValue = excelSheetDriver.readCell(testStepSheet, 5, w);
							String testStepsKeyword = excelSheetDriver.readCell(testStepSheet, 6, w);
							
							testStepInputs.setTestStepsSerialNumber(testStepsSerialNumber);
							testStepInputs.setTestStepCaseNumber(testStepCaseNumber);
							testStepInputs.setTestStepsDescription(testStepsDescription);
							testStepInputs.setWebElement(webElement);
							testStepInputs.setWebElementValue(webElementValue);
							testStepInputs.setInputValue(inputValue);
							testStepInputs.setTestStepsKeyword(testStepsKeyword);
							
							if (testCaseNumber.equalsIgnoreCase(testStepCaseNumber)) {
								logger.info("testStepsSerialNumber:" + testStepsSerialNumber);
								logger.info("testStepsDescription:" + testStepsDescription);
								logger.info("webElement:" + webElement);
								logger.info("webElementValue:" + webElementValue);
								logger.info("inputValue:" + inputValue);
								logger.info("testStepsKeyword:" + testStepsKeyword);

								logger.info("Executing performActions Method with the three arguments -"
										+ testStepsKeyword + " " + inputValue + " " + webElementValue);
								System.out.println("Executing performActions Method with the three arguments -"
										+ "keyword :" + testStepsKeyword + " " + "Value: " + inputValue + " " + "webElementValue: "
										+ webElementValue);
//								result = comlib.performActions(testStepsKeyword, inputValue, webElementValue);
								// Run tests
								result = comlib.runTest(testStepInputs);
								if (result.isResult() == true) {
									data1.put(Integer.toString(w),
											new Object[] { testCaseNumber, testStepsSerialNumber, testStepsDescription, webElementValue, inputValue,
													testStepsKeyword, Constants.KEYWORD_PASS, result.getMessage() });
									writeToExcel(data1, sheet1);
								} else {
									data1.put(Integer.toString(w),
											new Object[] { testCaseNumber, testStepsSerialNumber, testStepsDescription, webElementValue, inputValue,
													testStepsKeyword, Constants.KEYWORD_FAIL, result.getMessage() });
									writeToExcel(data1, sheet1);
									testCaseFlag = false;
								}
							}

						}

						
					} 
					if (testCaseExecutionFlag.equalsIgnoreCase("y")) {
						if (testCaseFlag == true) {
							System.out.println("In test case execution flag - Y");
							data3.put(Integer.toString(k), new Object[] { testCaseSerialNumber, testCaseNumber,
									testCaseDescription, testCaseExecutionFlag, Constants.KEYWORD_PASS });
							writeToExcel(data3, sheet3);
						} else {
							System.out.println("testCaseFlag - N");
							data3.put(Integer.toString(k), new Object[] { testCaseSerialNumber, testCaseNumber,
									testCaseDescription, testCaseExecutionFlag, Constants.KEYWORD_FAIL });
							writeToExcel(data3, sheet3);
						}
					} else {

						System.out.println("In test case execution flag - N");
						data3.put(Integer.toString(k), new Object[] { testCaseSerialNumber, testCaseNumber,
								testCaseDescription, testCaseExecutionFlag, Constants.KEYWORD_SKIPPED });
						writeToExcel(data3, sheet3);
					}
				}

			}
			if (executionFlag.equalsIgnoreCase("Y")) {
				if (testCaseFlag == true) {
					data2.put(Integer.toString(i),
							new Object[] { serialNumber, description, executionFlag, Constants.KEYWORD_PASS });
					writeToExcel(data2, sheet2);
				} else {

					data2.put(Integer.toString(i),
							new Object[] { serialNumber, description, executionFlag, Constants.KEYWORD_FAIL });
					writeToExcel(data2, sheet2);
				}
			} else {
				data2.put(Integer.toString(i),
						new Object[] { serialNumber, description, executionFlag, Constants.KEYWORD_SKIPPED });
				writeToExcel(data2, sheet2);
			}
		}

		excelSheetDriver.closeworkbook();
		excelSheetDriver2.closeworkbook();
		excelSheetDriver1.closeworkbook();
		try {

			// Writes test results in Workbook
			workbook.write(fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void writeToExcel(Map<String, Object[]> data, XSSFSheet sheet) {

		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			//System.out.println("key -> " + key);
			// this creates a new row in the sheet
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				// this line creates a cell in the next column of that row
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String) {
					//System.out.println("obj.toString() -> " + obj.toString());
					cell.setCellValue((String) obj);
				} else if (obj instanceof Integer) {
					//System.out.println("(Integer) obj) -> " + obj.toString());
					cell.setCellValue((Integer) obj);
				}
			}
		}

	}

}
