package org.isb.training.selenium;

import java.io.File;
import java.io.FileOutputStream;
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
import org.isb.training.selenium.CommonFunctionsLib;

public class RunTest {

	final static Logger logger = Logger.getLogger(RunTest.class);
	public static boolean result;
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	Map<String, Object[]> data;

	private WebDriver webDriver;
	Driver driver;

	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.chrome.driver", "./webdriver/chromedriver.exe");
		driver = new Driver();

		// Blank workbook 
		workbook = new XSSFWorkbook(); 

		// Create a blank sheet 
		sheet = workbook.createSheet("Chrome Test"); 

		// This data needs to be written (Object[]) 
		data = new TreeMap<String, Object[]>(); 
		// Header
		data.put("1", new Object[]{ "Test Step Case Number", "Test Step Description", "Element", "Value", "Keyword", "Test Result"}); 
	}

	@AfterClass
	public void afterClass() {
		webDriver.quit();
	}

	@Test
	public void verifySearchButton() throws Exception {

		// creating class object for configFileReader 
		ConfigFileReader configFileReader = new ConfigFileReader();
		ExcelSheetDriver excelSheetDriver = new ExcelSheetDriver();
		ExcelSheetDriver excelSheetDriver1 = new ExcelSheetDriver();
		ExcelSheetDriver excelSheetDriver2 = new ExcelSheetDriver();

		// variable name testSuitesheet data type is Sheet
		Sheet testSuitesheet = excelSheetDriver.getWorksheet(configFileReader.getTestSuite(), configFileReader.getTestSuiteSheet());
		int c = excelSheetDriver.columnCount();
		int r = excelSheetDriver.rowCount();

		for (int i = 1; i < r; i++) {

			String SNo = excelSheetDriver.readCell(testSuitesheet, 0, i);
			String Description = excelSheetDriver.readCell(testSuitesheet, 1, i);
			String ExecutionFlag = excelSheetDriver.readCell(testSuitesheet, 2, i);
			System.out.println("TestSuite:" + SNo);
			System.out.println("TestSuite:" + Description);
			System.out.println("TestSuite:" + ExecutionFlag);

			if(ExecutionFlag.equalsIgnoreCase("Y")){
				Sheet TestCasesheet =  excelSheetDriver1.getWorksheet(configFileReader.getTestCasePath(), Description);
				System.out.println("TestCasesheet " + TestCasesheet.toString());
				int rTestcase = excelSheetDriver1.rowCount();  // 5 rows
				int cTestCase = excelSheetDriver1.columnCount();  // 4 columns

				for(int k = 1; k < rTestcase; k++)
				{
					String snoTestCase = excelSheetDriver1.readCell(TestCasesheet,0, k);
					String testCaseNumber = excelSheetDriver1.readCell(TestCasesheet,1, k);
					String testcaseDescription = excelSheetDriver1.readCell(TestCasesheet,2, k);
					String testcaseExecutionFlag = excelSheetDriver1.readCell(TestCasesheet,3, k);
					logger.info("TestCases:" + snoTestCase);
					logger.info("TestCases:" + testCaseNumber);
					logger.info("TestCases:" + testcaseDescription);
					logger.info("TestCases:" + testcaseExecutionFlag);


					if(testcaseExecutionFlag.equalsIgnoreCase("y")){

						Sheet testStepsheet = excelSheetDriver2.getWorksheet(configFileReader.getTestStepsPath(), Description);
						int rowTestSteps = excelSheetDriver2.rowCount();   // row count =44
						int columnTestSteps = excelSheetDriver2.columnCount();  // column counts=6
						webDriver = driver.InitateDriver();
						CommonFunctionsLib comlib = new CommonFunctionsLib(webDriver);
						for(int w = 1; w < rowTestSteps; w++)
						{
							String snoTestSteps = excelSheetDriver.readCell(testStepsheet,0, w);
							String testStepcaseNumber = excelSheetDriver.readCell(testStepsheet,1, w);
							String desTestSteps = excelSheetDriver.readCell(testStepsheet,2, w);
							String element = excelSheetDriver.readCell(testStepsheet,3, w);
							String value = excelSheetDriver.readCell(testStepsheet,4, w);
							String keywordTestSteps = excelSheetDriver.readCell(testStepsheet,5, w);
							if(testCaseNumber.equalsIgnoreCase(testStepcaseNumber)){
								logger.info("snoTestSteps:" + snoTestSteps);
								logger.info("desTestSteps:" + desTestSteps);
								logger.info("element:" + element);
								logger.info("value:" + value);
								logger.info("keywordTestSteps:" + keywordTestSteps);

								logger.info("Executing performActions Method with the three arguments -" + keywordTestSteps + " " + value + " "+  element);
								System.out.println("Executing performActions Method with the three arguments -" + "keyword :" + keywordTestSteps + " " + "Value: " + value + " "+ "element: " + element);
								result = comlib.performActions(keywordTestSteps, value, element);
								if(result == true){
									data.put(Integer.toString(w), new Object[]{ snoTestSteps, desTestSteps, element, value, keywordTestSteps, Constants.KEYWORD_PASS }); 
									writeToExcel(data);
								} else {
									data.put(Integer.toString(w), new Object[]{ snoTestSteps, desTestSteps, element, value, keywordTestSteps, Constants.KEYWORD_FAIL }); 
									writeToExcel(data);
								}
							}

						}

					}
				}

			}
		}

		excelSheetDriver.closeworkbook();
		excelSheetDriver2.closeworkbook();
		excelSheetDriver1.closeworkbook();

	}

	private void writeToExcel(Map<String, Object[]> data) {
		// Iterate over data and write to sheet 
		Set<String> keyset = data.keySet(); 
		int rownum = 0; 
		for (String key : keyset) { 
			// this creates a new row in the sheet 
			Row row = sheet.createRow(rownum++); 
			Object[] objArr = data.get(key); 
			int cellnum = 0; 
			for (Object obj : objArr) { 
				// this line creates a cell in the next column of that row 
				Cell cell = row.createCell(cellnum++); 
				if (obj instanceof String) 
					cell.setCellValue((String)obj); 
				else if (obj instanceof Integer) 
					cell.setCellValue((Integer)obj); 
			} 
		} 
		try { 

			// this Writes the workbook test result			
			FileOutputStream out = new FileOutputStream(new File(Constants.FILE_PATH)); 
			workbook.write(out); 
			out.close(); 
		} 
		catch (Exception e) { 
			e.printStackTrace(); 
		} 
	} 

}