package com.cocus.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cocus.BaseTest.BaseClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;



public class Util extends BaseClass {

	/**
	 * @param FolderPath
	 * @return
	 * @notes: This Function Creates a folder
	 */
	public static boolean CreateFolder(String FolderPath) {
		boolean result = false;
		try {
			File directory = new File(FolderPath);
			if (!directory.exists()) {
				// result = directory.mkdir();
				result = directory.mkdirs();
			}
		} catch (Exception e) {
			System.out.println("Error while creating the specific folder. Location " + FolderPath + ". Error message "
					+ e.getMessage());
		}
		return result;

	}

	/**
	 * @return
	 * @notes: This Function generates random number
	 */
	public static String randomNum() {
		String randomNumber = "";

		try {
			randomNumber = new SimpleDateFormat("yyyyMMdd_HHmmSSS").format(Calendar.getInstance().getTime()).toString();
			return randomNumber;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return randomNumber;
	}

	/**
	 * @return
	 * @notes: this function gets the host name of the machine
	 */
	public static String getHostNameOfMachine() {
		String hostname = "Unknown";
		try {
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		} catch (UnknownHostException ex) {
			Report.failTest("Hostname can not be resolved");
		}

		return hostname;

	}

	/**
	 * @return
	 * @notes: This function generates the date string
	 */
	public static String DateString() {
		Util.HardWait(1000);
		String randomNumber = "";

		randomNumber = new SimpleDateFormat("MMddyyyy").format(Calendar.getInstance().getTime()).toString();

		return randomNumber;
	}

	/**
	 * @param MilliSec
	 * @notes: this function is used to hardwait
	 */
	public static void HardWait(int MilliSec) {
		try {
			Thread.sleep(MilliSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @return
	 * @notes: This function is used to get the system chrome version
	 */
	public static String systemChromeVersion() {

		String chromeVersion = "";

		try {

			Runtime rt = Runtime.getRuntime();
			Process proc = rt
					.exec("reg query " + "HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon " + "/v version");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			String s = null;
			while ((s = stdInput.readLine()) != null) {
				// System.out.println(s);
				if (s.contains("version")) {
					String temp = s.split("    ")[3];
					chromeVersion = temp.split("\\.")[0] + ".0"; // Retrieving parent version and appending zero
				}
			}

			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		} catch (Exception e) {
			System.out.println("Not able to fetch the chrome browser version installed on your machine.");
			System.out.println("Please update the chrome version manually for Web driver manager.");
		}

		return chromeVersion;
	}
	/**
	 * @param driver
	 * @param element
	 * @param Elementname
	 * @throws IOException
	 * @notes: This function is used to check if the element is exist
	 */
	public static void elementExist(WebDriver driver, WebElement element, String Elementname) throws IOException {
		Util.waitForElement(driver, element);
		if (element.isDisplayed()) {
			Util.highLightElement(driver, element);
			Report.passTest(Elementname + " is  present on page");

			// System.out.println(element +" is getting displayed");
		} else {
			Report.failTest(Elementname + " is  not present on page");
		}
	}

	/**
	 * @param driver
	 * @param element
	 * @notes: this function is used to wait for the visibility of the element
	 */
	public static void waitForElement(WebDriver driver, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			//System.out.println(e.getMessage());

		}
	}
	/**
	 * @param driver
	 * @param element
	 * @notes: this function is used to highlight the element 
	 */
	public static void highLightElement(WebDriver driver, WebElement element) {
		try {
			Util.waitForElement(driver, element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'border: 4px solid red;');", element);
			js.executeScript("arguments[0].setAttribute('style','border: solid 4px white');", element);
			js.executeScript("arguments[0].setAttribute('style', 'border: 4px solid red;');", element);
			js.executeScript("arguments[0].setAttribute('style','border: solid 4px white');", element);
		} catch (Exception e) {
			System.out.println("Error occured highLightElement " + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * @param driver
	 * @param screenshotName
	 * @param ResultFolderPath
	 * @return
	 * @notes: this function is used to get the screen capture
	 */
	public static String capture(AndroidDriver<AndroidElement> driver, String screenshotName, String ResultFolderPath) {
		File source = null;
		String FileName = null;
		String randomTime = randomNum();
		try {
			System.out.println(driver.getClass().getName());
			if ( driver.getClass().getName().contains("AndroidDriver")) {
				TakesScreenshot ts = (TakesScreenshot) driver;
				source = ts.getScreenshotAs(OutputType.FILE);
			}

			FileName = screenshotName + randomTime + ".jpg";
			String dest = ResultFolderPath + "/" + screenshotName + randomTime + ".jpg";
			File destination = new File(dest);
			FileUtils.copyFile(source, destination);
			return FileName;
		} catch (WebDriverException se) {

		} catch (Exception e) {
			System.out.println(
					"*******Exception while capturing Image*******" + screenshotName + "****" + e.getMessage());
		}

		return FileName;
	}

	/**
	 * @param element
	 * @return
	 * @notes: this function gets the text from the element
	 */
	public static String getTextFromElement(WebElement element) {
		String name = null;
		try {
			name = element.getText().trim();
		} catch (Exception e) {
			Report.failTest(e.getMessage() + " getTextFromElement method failed");
		}
		return name;
	}
	
	public static void mobileSendKeys(AndroidDriver<AndroidElement> driver, WebElement element, String valueToPut,
			boolean clearField, String desc) {
		try {
			waitForElement(driver, element);
			if (element.isEnabled()) {
				if (clearField) {
					element.clear();
				}
				element.sendKeys(valueToPut);
				if (isKeyboardVisible())
					driver.hideKeyboard();
			} else {
				Report.failTestSnapshot(driver, " Object " + desc + " not Enabled ", "ObjDisable");
			}
		} catch (Exception e) {
			Report.failTestSnapshot(driver, desc + ":: Exception  " + e.getMessage(), "Text Input");
		}

	}
	
	public static boolean isKeyboardVisible() {
		boolean isKeyboardPresent = false;
		try {
			Process p = Runtime.getRuntime().exec("adb shell dumpsys input_method | grep mInputShown");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String outputText = "";

			while ((outputText = in.readLine()) != null) {

				if (!outputText.trim().equals("")) {
					String keyboardProperties[] = outputText.split(" ");
					String keyValue[] = keyboardProperties[keyboardProperties.length - 1].split("=");
					String softkeyboardpresenseValue = keyValue[keyValue.length - 1];
					if (softkeyboardpresenseValue.equalsIgnoreCase("false")) {
						isKeyboardPresent = false;
					} else {
						isKeyboardPresent = true;
					}
				}
			}
			in.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return isKeyboardPresent;
	}

	/**
	 * @param Actual
	 * @param Expected
	 */
	public static  void verifyText(String Actual, String Expected) {
		try {
			if (Actual.trim().equalsIgnoreCase(Expected.trim())) {
				Report.passTest("Verified Text As Expected. Text: " + Expected);
			} else {
				Report.failTest("Failed: Text not as Expected. Actual: " + Actual + ", Expected: " + Expected);
			}
		} catch (Exception e) {
			Report.failTest(e.getClass().getSimpleName() + " verifyText method failed");
		}
	}

	/**
	 * @param element
	 * @param name
	 * @return
	 */
	public static String getAttribute(WebElement element, String name) {
		String attribute = null;
		try {
			attribute = element.getAttribute(name);
		} catch (Exception e) {

			Report.failTest(e.getMessage() + " getAttribute method failed");
		}
		return attribute;
	}

	/**
	 * @param element
	 * @param attributeName
	 * @param expectedAttribute
	 * @return
	 */
	public static String verifyAttribute(WebElement element, String attributeName, String expectedAttribute) {
		String attribute = null;
		try {
			attribute = element.getAttribute(attributeName);
			verifyText(attribute, expectedAttribute);
		} catch (Exception e) {

			Report.failTest(e.getMessage() + " verifyAttribute method failed");
		}
		return attribute;
	}


	/**
	 * @param element
	 * @param cssValue
	 * @return
	 */
	public String getCssValueFromElement(WebElement element, String cssValue) {
		String name = null;
		try {
			name = element.getCssValue(cssValue);
		} catch (Exception e) {
			Report.failTest(e.getMessage() + " getCssValueFromElement method failed");
		}
		return name;
	}
	
	/**
	 * @param miliSeconds
	 */
	public static void hardWait(long miliSeconds) {
		try {
			Thread.sleep(miliSeconds);
		} catch (Exception e) {
			Report.failTest("Not able to wait");
		}
	}

	/**
	 * @param driver
	 * @param element
	 * @return
	 */
	private static boolean isElementPresent(WebDriver driver, WebElement element) {

		String objectIdentifierType = "";
		String objectIdentifierValue = "";
		String objectArray1[] = null;
		String objectArray[] = null;
		WebDriverWait wait = new WebDriverWait(driver, 60);

		try {
			String object = element.toString();
			objectArray1 = object.split("->");
			objectArray = objectArray1[1].split(":");

		} catch (Exception e) {
			Report.failTest("Element is not located : " + element.toString());
		}

		try {
			objectIdentifierType = objectArray[0].trim();
			objectIdentifierValue = objectArray[1].trim();
			System.out.println(objectIdentifierType);
			System.out.println(objectIdentifierValue);
			switch (objectIdentifierType) {
			case "id":
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(objectIdentifierValue)));
				Thread.sleep(250);

				break;
			case "cssSelector":
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(objectIdentifierValue)));
				Thread.sleep(250);

				break;
			case "linkText":
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(objectIdentifierValue)));
				Thread.sleep(250);

				break;
			case "xpath":
				// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(objectIdentifierValue)));
				wait.until(ExpectedConditions.visibilityOf(element));
				Thread.sleep(250);

				break;
			default:
				Report.failTest("Locator used is not as expected");

			}

		} catch (Exception e) {
			Report.failTest("INFO  : " + objectIdentifierValue + " Element is Not Present on Web Page");
			return false;
		}
		return true;
	}
	
	/**
	 * @param Element
	 * @param ElementName
	 * @param driver
	 */
	public static void click(WebElement Element,String ElementName, WebDriver driver) {
		try{
			waitForElement(driver, Element);
			Element.click();
			Report.passTest("Successfully Clicked on "+ElementName);
		}
		catch(Exception e){
			e.printStackTrace();
			Report.failTest(e.getMessage()+" click method failed ");
		}
	}
	
	/**
	 * @param driver
	 * @param element
	 * @param ValueToPut
	 * @param ClearField
	 * @param Desc
	 */
	public static void sendKeysWithClear(WebDriver driver, WebElement element, String ValueToPut, boolean ClearField,
			String Desc) {
		try {
			waitForElement(driver, element);
			if (isNull_Empty_WhiteSpace(ValueToPut) == false) {
				if (element.isEnabled()) {
					if (ClearField) {
						element.clear();
					}
					element.sendKeys(ValueToPut);

				} else {
					//Report.failTestSnapshot(driver, " Object " + Desc + " not Enabled ", "ObjDisable");
				}
			}
		} catch (Exception e) {
			//Report.failTestSnapshot(driver, Desc + ":: Exception  " + e.getMessage(), "test");
		}

	}
	
	/**
	 * @param CmpVal
	 * @return
	 */
	public static boolean isNull_Empty_WhiteSpace(String CmpVal) {
		try {
			CmpVal = CmpVal.replaceAll("\u00a0", "");
			CmpVal = CmpVal.replaceAll("&nbsp", "").trim();
		} catch (Exception e) {
			System.out.println("Error occured isNull_Empty_WhiteSpace " + e.getMessage());
		}
		if ((!CmpVal.trim().equals("")) && CmpVal != null && (CmpVal.isEmpty()) == false) {
			return false;
		} else {
			return true;
		}
		// return CmpVal != null && !string.isEmpty() &&
		// !string.trim().isEmpty();
	}
	
	/**
	 * @param date
	 * @param format
	 * @return
	 */
	public static String convertDateToString(Date date, String format) {

		DateFormat df = new SimpleDateFormat(format);
		String reportDate = df.format(date);
		return reportDate;
	}
	
	/**
	 * @param selectTag
	 * @return
	 * @throws InterruptedException
	 */
	public static List<String> getAllDropdownValues(WebElement selectTag) throws InterruptedException {
		List<String> ls = new ArrayList<String>();
		try {
			Select sel = new Select(selectTag);
			List<WebElement> we = sel.getOptions();

			for (WebElement a : we) {
				if (!a.getAttribute("value").equals("")) {
					ls.add(a.getText());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ls;
	}
	/**
	 * @param driver
	 * @param element
	 * @param selectOptionValue
	 * @throws IOException
	 */
	public static void selectOption(WebDriver driver, WebElement element, String selectOptionValue) throws IOException {
		try {
			Select dropdown = new Select(element);
			dropdown.selectByVisibleText(selectOptionValue);
		} catch (Exception e) {
			// Report.failTestSnapshot(driver, e.getMessage(), "Failed select option from
			// dropdown");

		}
	}

	/**
	 * @param driver
	 * @param element
	 * @param selectOptionValue
	 */
	public static void selectOptionByValue(WebDriver driver, WebElement element, String selectOptionValue) {
		try {
			Select dropdown = new Select(element);
			dropdown.selectByValue(selectOptionValue);
		} catch (Exception e) {
			// Report.failTestSnapshot(driver, e.getMessage(), "Failed select option from
			// dropdown");
		}
	}
	
	/**
	 * @param actualValue
	 * @param expectedValue
	 * @param columnName
	 * @return
	 */
	public static boolean compareStringValues(String actualValue, String expectedValue, String columnName) {
		boolean result=false;
		        try {
		         if(!columnName.equals("PetDOB")) {
		            if (actualValue.trim().equals(expectedValue.trim()))
		            {
		                Report.passTest("validated Column/Value " + columnName + " Actual Value is: " + actualValue
		                        + " and Expected Value is: " + expectedValue);
		                result=true;

		            } else {
		                Report.failTest("Failed : Validation of Column/Value " + columnName + ". Actual Value is: " + actualValue
		                        + " and Expected Value is: " + expectedValue);
		            }
		         } else if(columnName.equals("Name") || columnName.equalsIgnoreCase("DOB")) {
		        	 if (actualValue.trim().contains(expectedValue.trim()))
			            {
			                Report.passTest("validated Column/Value " + columnName + " Actual Value is: " + actualValue
			                        + " and Expected Value is: " + expectedValue);
			                result=true;

			            } else {
			                Report.failTest("Failed : Validation of Column/Value " + columnName + ". Actual Value is: " + actualValue
			                        + " and Expected Value is: " + expectedValue);
			            }
		         }
		        } catch (Exception e) {
		            Report.failTest("Compare Value For Column/Value " + columnName + " is Failed");
		        }
		return result;
		    }
	
	public static void wait_inMinutes(double min) {
		min = min * 60000;

		try {
			Thread.sleep((long) min);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	public static void mobileClick(AndroidDriver<AndroidElement> driver, WebElement element) {
		try {
			waitForElement(driver, element);
			element.click();
			
		} catch (WebDriverException se) // Handling org.openqa.selenium.WebDriverException: java.net.SocketException
		{
			try {
				waitForElement(driver, element);
				element.click();
				
			} catch (Exception e) {
				Report.infoTest("Object not found" + e.getMessage());
			}
		} catch (Exception e1) {
			Report.infoTest("Object not found" + e1.getMessage());
		}
	}
	
	public static boolean validateMobileLabelText(AndroidDriver<AndroidElement> driver, WebElement element,
			String expectedText) {
		boolean existenceFlag = false;
		try {
			
			if (element.getText().equals(expectedText)) {
				Report.passTestScreenshot(driver, expectedText, "Label Text Validation Passed");
				existenceFlag = true;
				return existenceFlag;
			} else {
				Report.failTestSnapshot(driver, "Failed : Label text is not as expected. Actual : " + element.getText()
						+ " and expected is : " + expectedText, "Label Text Validation Failed");
				return existenceFlag;
			}
		} catch (Exception e) {
			Report.failTestSnapshot(driver, "Failed : Not able to validate Label text : " + expectedText,
					"Label Text Validation Failed");
		}

		return existenceFlag;
	}
	
	public static String getDateinrequiredformat() {
		
		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
		String stringDate = DateFor.format(date);
		try {
			DateFor = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
			stringDate = DateFor.format(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return stringDate;
	}
}
