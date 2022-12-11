package com.cocus.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.cocus.BaseTest.BaseClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;



public class Report extends BaseClass {

	public static SoftAssert s_assert = new SoftAssert();
	public static String relativePathIndividualTest;
	public static String reportName;
	/**
	 * @param htmlReporter
	 * @param executionType
	 * @param ResultFolderPath
	 * @return
	 */
	public static ExtentHtmlReporter StartHtmlFinalReport(ExtentHtmlReporter htmlReporter, String executionType,
			String ResultFolderPath) {

		htmlReporter = new ExtentHtmlReporter(
				(ResultFolderPath + "CR_" + executionType + "_" + Util.randomNum() + ".html"));
		htmlReporter.config().setDocumentTitle("Automation Execution Report");
		htmlReporter.config().setReportName("Test Execution Report");
		//		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		return htmlReporter;
	}

	/**
	 * @param htmlReporter
	 * @param extent
	 * @return
	 */
	public static ExtentReports StartExtentReport(ExtentHtmlReporter htmlReporter, ExtentReports extent) {
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Host Name", Util.getHostNameOfMachine()); // MPINDO02056
		extent.setSystemInfo("Env", "Test");
		return extent;
	}

	/**
	 * @param stepDetails
	 */
	public static void failTest(String stepDetails) {

		try {
			test.fail(MarkupHelper.createLabel(stepDetails, ExtentColor.RED));
			s_assert.assertTrue(false);

		} catch (Exception e) {		
			System.out.println(e.getMessage());
		}

	}

	/**
	 * @param htmlReporter
	 * @param browserName
	 * @param TcName
	 * @param ResultFolderPath
	 * @return
	 */
	public static ExtentHtmlReporter StartHtmlIndividualTestCaseReport(ExtentHtmlReporter htmlReporter,
			String browserName, String TcName, String ResultFolderPath) {
		if (TcName.length() > 30) {
			TcName = TcName.trim().replaceAll(" ", "").substring(0, 30);
		} else {
			TcName = TcName.trim().replaceAll(" ", "");
		}
		String IndividualReports = ResultFolderPath + "Testcases/";
		Util.CreateFolder(IndividualReports);
		relativePathIndividualTest = TcName + "_" + Util.randomNum() + ".html";
		reportName = IndividualReports + relativePathIndividualTest;
		htmlReporter = new ExtentHtmlReporter(reportName);
		htmlReporter.config().setDocumentTitle("Automation Execution Report");
		htmlReporter.config().setReportName("Test Execution Report");
		htmlReporter.config().setTheme(Theme.STANDARD);

		return htmlReporter;
	}

	/**
	 * @param extent
	 * @param testCaseName
	 * @return
	 */
	public static ExtentTest testCreateFinal(ExtentReports extent, String testCaseName) {
		test = extent.createTest(testCaseName, testCaseName);
		return test;
	}

	/**
	 * @param test
	 */
	public static void reportLink(ExtentTest test) {
		test.info(MarkupHelper.createLabel("  <a href='" + "Testcases/" + relativePathIndividualTest
				+ "' target=\"_blank\" style=\"color: rgb(255, 255, 255)\"> Test Case link : " + reportName + "</a>",
				ExtentColor.BLUE));
	}
	/**
	 * @param extent
	 */
	public static void endReport(ExtentReports extent) {
		extent.flush();
	}

	/**
	 * @param stepDetails
	 */
	public static void failTestConsolidated(String stepDetails) {

		try {
			testFinal.fail(MarkupHelper.createLabel(stepDetails, ExtentColor.RED));
			s_assert.assertAll();
		} catch (Exception e) {		
			System.out.println(e.getMessage());
		}

	}
	/**
	 * @param stepDetails
	 */
	public static void passTestConsolidated(String stepDetails) {

		try {
			testFinal.pass(MarkupHelper.createLabel(stepDetails, ExtentColor.BLUE));
		} catch (Exception e) {		
			System.out.println(e.getMessage());
		}

	}
	/**
	 * @param stepDetails
	 */
	public static void partialTestConsolidated(String stepDetails) {

		try {
			testFinal.warning(MarkupHelper.createLabel(stepDetails, ExtentColor.BLACK));
		} catch (Exception e) {		
			System.out.println(e.getMessage());
		}

	}
	/**
	 * @param extent
	 * @param testCaseName
	 * @return
	 */
	public static ExtentTest testCreate(ExtentReports extent, String testCaseName) {
		test = extent.createTest(testCaseName, testCaseName);
		testCaseCount++;
		return test;
	}

	/**
	 * @param stepDetails
	 */
	public static void passTest(String stepDetails) {
		try {
			test.pass(MarkupHelper.createLabel(stepDetails, ExtentColor.GREEN));
			Assert.assertTrue(true);
		} catch (Exception e) {
			Report.infoTest("Error while reporting" + e.getMessage());
		}
	}

	/**
	 * @param stepDetails
	 */
	public static void skipTest(String stepDetails) {
		try {
			test.skip(MarkupHelper.createLabel(stepDetails, ExtentColor.BLUE));
			throw new SkipException("Skipping the test as runmode is NO ");
		} catch (Exception e) {
			System.out.println("Skipping the test as runmode is NO");
		}
	}

	/**
	 * @param stepDetails
	 */
	public static void infoTest(String stepDetails) {

		try {
			test.info(MarkupHelper.createLabel(stepDetails, ExtentColor.AMBER));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @param driver
	 * @param stepDetails
	 * @param screenshotName
	 */
	public static void passTestScreenshot(AndroidDriver<AndroidElement> driver, String stepDetails, String screenshotName) {

		try {
			if(driver!=null)
			{
				String ResultFolderPathForScreenshot = ResultFolderPath + "Screenshot";
				Util.CreateFolder(ResultFolderPathForScreenshot);
				String screenshotPath = Util.capture(driver, screenshotName, ResultFolderPathForScreenshot);
				String relativePath = "../Screenshot/"+screenshotPath;
				test.pass(MarkupHelper.createLabel(stepDetails, ExtentColor.GREEN));
				test.pass(MarkupHelper.createLabel(" <a href='" + relativePath
						+ "' target=\"_blank\" style=\"color: rgb(255, 255, 255)\"> Screenshot link :" + screenshotName
						+ "</a>", ExtentColor.BLUE));
			}

		} catch (Exception e) {
			Report.infoTest(" message " + e.getMessage());
			Report.failTestSnapshot(driver, "File IO Exception  ", "Exception");

		}

	}

	/**
	 * @param stepDetails
	 */
	public static void failTestStop(String stepDetails) {

		test.fail(MarkupHelper.createLabel(stepDetails, ExtentColor.RED));
		Assert.assertTrue(false);
	}


	/**
	 * @param driver
	 * @param e
	 * @param stepDetails
	 */
	public static void failTestException(AndroidDriver<AndroidElement> driver,Exception e,String stepDetails) {

		try {
			test.fail(MarkupHelper.createLabel(e.getMessage(), ExtentColor.RED));
			List<String> base64StringImage = generatePageScreenshot(driver);
			test.fail("<span class='label white-text red'>" + stepDetails + "</span><br><a href='data:image/gif;base64,"+base64StringImage.get(0)+"' data-featherlight='image'><img src='data:image/gif;base64,"+base64StringImage.get(1)+"' height='50' width='50' alt='Failed Screenshot'></a>");
			s_assert.assertTrue(false);

		} catch (Exception e1) {		
			System.out.println("*******Exception while capturing Image***********");
		}

	}

	/**
	 * @param driver
	 * @param stepDetails
	 * @param screenshotName
	 */
	public static void failTestSnapshot(AndroidDriver<AndroidElement> driver, String stepDetails, String screenshotName) {
		try {
			s_assert.assertTrue(false);
			if (!screenshotName.equals("")) {
				//Fail with Label and Embedded Screenshot
				List<String> base64StringImage = generatePageScreenshot(driver);
				test.fail("<span class='label white-text red'>" + stepDetails + "</span><br><a href='data:image/gif;base64,"+base64StringImage.get(0)+"' data-featherlight='image'><img src='data:image/gif;base64,"+base64StringImage.get(1)+"' height='50' width='50' alt='Failed Screenshot'></a>");

			}
		} catch (Exception e) {
			System.out.println("*******Exception while capturing Image***********");

		}

	}
	/**
	 * @param driver
	 * @return
	 * @throws IOException
	 */
	public static List<String> generatePageScreenshot(AndroidDriver<AndroidElement> driver) throws IOException {
		List<String> stringImages = new ArrayList<String>();
		String strImage = "";
		String thumbnailImage = "";
		try {
			File src = null;
			TakesScreenshot ts = (TakesScreenshot) driver;
			src = ts.getScreenshotAs(OutputType.FILE);
			BufferedImage img = ImageIO.read(src);			
			strImage = imgToBase64String(img, img.getWidth(), img.getHeight());
			thumbnailImage = thumbnailImageToBase64String(img,100,100);
			stringImages.add(strImage);
			stringImages.add(thumbnailImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(WebDriverException se)
		{

			System.out.println("*******Exception while capturing Image***********");
		}
		return stringImages;
	}

	/**
	 * @param img
	 * @param width
	 * @param height
	 * @return
	 */
	public static String imgToBase64String(final RenderedImage img, int width, int height) {
		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			//Image tmp = ((Image) img).getScaledInstance(width, height, Image.SCALE_SMOOTH);
			Image tmp = ((Image) img).getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = resizedImage.createGraphics();
			g2d.drawImage(tmp, 0, 0, width, height, null);
			g2d.setComposite(AlphaComposite.Src);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.dispose();
			ImageIO.write(resizedImage, "gif", java.util.Base64.getEncoder().wrap(byteStream));
			return byteStream.toString(StandardCharsets.ISO_8859_1.name());
		} catch (final IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	/**
	 * @param img
	 * @param width
	 * @param height
	 * @return
	 */
	private static String thumbnailImageToBase64String(final RenderedImage img, int width, int height) {

		final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			Image tmp = ((Image) img).getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = thumbnailImage.createGraphics();
			g2d.drawImage(tmp, 0, 0, null);
			g2d.dispose();
			ImageIO.write(thumbnailImage, "png", java.util.Base64.getEncoder().wrap(byteStream));
			return byteStream.toString(StandardCharsets.ISO_8859_1.name());
		} catch (final IOException ioe) {
			throw new UncheckedIOException(ioe);
		}

	}

	/**
	 * 
	 */
	public static void endTest() {
		try {
			test.info(MarkupHelper.createLabel("Test Completed", ExtentColor.TEAL));
		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}
	}
}
