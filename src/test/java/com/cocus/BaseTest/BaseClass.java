package com.cocus.BaseTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Timer;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.cocus.driver.AppiumServerJava;
import com.cocus.utils.PropertiesRead;
import com.cocus.utils.Report;
import com.cocus.utils.Util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;




public class BaseClass {

	public static long timeOut = 30, driverWait = 30, emailTimeOut;
	public static JavascriptExecutor js;
	public static AndroidDriver<AndroidElement> androidDriver;
	public static IOSDriver<IOSElement> iOSDriver;
	public static ExtentTest testFinal;
	public static ExtentReports extentFinal;
	public static ExtentHtmlReporter htmlReporterFinal;	
	public static ExtentTest test;
	public static ExtentReports extent;
	public static ExtentHtmlReporter htmlReporterIndividual;
	public static int individualRptCount = 0;
	
	public static String systemDir = System.getProperty("user.dir");
	public static String ExcelPath;
	public static Properties configProp;
	public static Properties prop;
		
	public static String ResultFolderPath;
	public String browser;
	public static final String CurrentDateFolder = Util.DateString();
	public static Timer timerThread = null;
	public static String deviceID;
	public static String androidVersion;
	public static String iOSVersion;
	public static String deviceName;
	public static String deviceNameiOS;
	public static String deviceEnvironment;
	public static String platform;
	public static int testCasePassed = 0, testCaseFailed = 0, testCaseExecuted = 0, testCasePartialExecuted = 0;
	public static int testCaseCount = 0;
	public static HashMap<Integer, String> testCaseResult = new HashMap<Integer, String>(),
		    testCaseId = new HashMap<Integer, String>(),
			testCaseResults = new HashMap<Integer, String>();	
	
	@BeforeSuite
	@Parameters({ "applicationName" })
	public void initializeWebdriver(@Optional("Test") String applicationName) throws Exception  {

		if (StringUtils.isEmpty(ResultFolderPath)) {
			ResultFolderPath = systemDir + "/TestResults" + "/" + "Report_" +CurrentDateFolder+  "/";
		}
		Util.CreateFolder(ResultFolderPath);
		configProp = PropertiesRead.readConfigProperty();
		htmlReporterFinal = Report.StartHtmlFinalReport(htmlReporterFinal, applicationName, ResultFolderPath);
		extentFinal = Report.StartExtentReport(htmlReporterFinal, extentFinal);
		AppiumServerJava.setUpMobileEnvironment();
		
	}

	@BeforeClass
	public void beforeTest(ITestContext contextContext) {
		try {			
			htmlReporterIndividual = Report.StartHtmlIndividualTestCaseReport(htmlReporterIndividual,
					configProp.getProperty("platform"), contextContext.getName(), ResultFolderPath);
			extent = Report.StartExtentReport(htmlReporterIndividual, extent);
			String testName = contextContext.getName();
			if(testName.equals("Default test") || testName.equals("Surefire test") || testName.equals("Command line test"))
			{
				testName = this.getClass().getName().toString().replace(".", "_");
				System.out.println(testName);		
				testFinal = Report.testCreateFinal(extentFinal, testName);	
			}
			else
			{
				testFinal = Report.testCreateFinal(extentFinal, "Test :->> " + contextContext.getName());
			}
			
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@AfterClass
	public void quitReport(ITestContext contextContext) throws IOException {
		String testCaseStatus = null;
		try {
			Report.reportLink(testFinal);
			Report.endReport(extent); //// individual test report

			String reportNamePath = Report.reportName;
			// Html Parser
			File input = new File(reportNamePath);
			Document html = Jsoup.parse(input, "UTF-8");
			
			String invidualTestStatus = html.getElementsByAttributeValueContaining("class", "test-status right").text();

			if (invidualTestStatus.trim().equalsIgnoreCase("fail")) {
				testCaseFailed++;
				testCaseStatus = "FAIL";
				Report.failTestConsolidated("Test Case Failed");
			} else if ((invidualTestStatus.toLowerCase().trim().contains("pass"))
					&& (html.getElementsByAttributeValueContaining("class", "label white-text teal").size() != 0)) {
				testCasePassed++;
				testCaseStatus = "PASS";
				Report.passTestConsolidated("Test Case Passed");
			} else {
				testCasePartialExecuted++;
				testCaseStatus = "PARTIAL EXECUTED";
				Report.passTestConsolidated("Test Case Passed");
			}

			testCaseResult.put(testCaseCount, testCaseStatus);
			testCaseExecuted = testCasePassed + testCaseFailed + testCasePartialExecuted;

		} catch (Exception e) {
			test = Report.testCreate(extent, "Test :->> " + contextContext.getName());
			Report.endReport(extent);
		}
	}



	
	  @AfterMethod public void terminateTimerSession() {	 

		  	if(timerThread!=null)
		  	{
		  		timerThread.cancel(); //After every test timerThread will get terminated if it started by any of the test
		  		timerThread = null;
		  	}
	  
	  }
	 

	@AfterSuite
	public void sendReportEmail() {
		

		System.out.println("====================================================");
		System.out.println("Test Cases Executed: " + testCaseExecuted);
		System.out.println("Test Cases Passed: " + testCasePassed);
		System.out.println("Test Cases Failed: " + testCaseFailed);
		//System.out.println("Test Cases Partially Executed: " + testCasePartialExecuted);
		System.out.println("====================================================");
		deviceEnvironment = configProp.getProperty("DeviceEnvironment");
			if(!deviceEnvironment.equalsIgnoreCase("Remote"))
			{
				AppiumServerJava.stopServer();
			}

		Report.endReport(extentFinal);
	}
	
}
