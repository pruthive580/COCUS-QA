package com.cocus.driver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.cocus.BaseTest.BaseClass;
import com.cocus.utils.Report;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class MobileDriver extends BaseClass {

	public static AndroidDriver<AndroidElement> StartAndroidDeviceWithAppium(AndroidDriver<AndroidElement> driver,
			boolean autoLaunchFlag)	throws MalformedURLException, InterruptedException 

	{
		try {
	
			deviceEnvironment = configProp.getProperty("DeviceEnvironment");
			platform = configProp.getProperty("platform");

			File applicationParentDirectoryPath = new File(System.getProperty("user.dir")+ "\\src\\test\\resources\\App");
			System.out.println(System.getProperty("user.dir")+"\\src\\test\\resources\\App");
			File app = new File(applicationParentDirectoryPath, "cocus.apk");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			if (platform.toUpperCase().equals("ANDROID")) {
				if(!deviceEnvironment.equals("Remote"))
				{
					capabilities.setCapability(MobileCapabilityType.VERSION, androidVersion);
					capabilities.setCapability("platformName", platform);
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
					capabilities.setCapability(MobileCapabilityType.UDID, deviceID);
					capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
					capabilities.setCapability("noReset", true);	
					capabilities.setCapability("appPackage", "com.example.android.testing.notes.mock");
					capabilities.setCapability("appActivity", "com.example.android.testing.notes.notes.NotesActivity");
					capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 10800);
					capabilities.setCapability("autoLaunch", autoLaunchFlag);
					capabilities.setCapability("autoAcceptAlerts", true);
					capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
					driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
					androidDriver = driver;
					androidDriver.launchApp();
				}
				else
				{
					System.out.println("Running Test Case on Qyrus Platform");
					deviceName = System.getProperty("QyrusDeviceID");
					deviceID = System.getProperty("QyrusDeviceID");
					capabilities.setCapability("platformName", "Android");
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName); //R52N20DJTTP
					capabilities.setCapability(MobileCapabilityType.UDID, deviceID);
					capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
					capabilities.setCapability("appium:AuthToken", "1432c8f5-1a81-4837-893a-3187ce59ef09");
					capabilities.setCapability("appPackage", "com.wm.ocs.android.da");
					capabilities.setCapability("appActivity", "com.wm.ocs.android.da.DriverActivity");
					capabilities.setCapability("noReset", true);
					capabilities.setCapability("autoAcceptAlerts", true);
					capabilities.setCapability("appium:autoLaunch", autoLaunchFlag);
					capabilities.setCapability("appium:SessionName", "DA Automation");
					capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 10800);
					driver = new AndroidDriver<AndroidElement>(new URL("https://wm-mobilecloud-us-east.quinnox.info:443/wd/hub"), capabilities);
					androidDriver = driver;

				
			} 
		}
	} catch(Exception e) {
		Report.failTestStop(" Device is not connected to appium programatically" + e );
	}
	
	return androidDriver;
	}
	public static  IOSDriver<IOSElement> StartiOSDeviceWithAppium(IOSDriver<IOSElement> driver,
			boolean autoLaunchFlag)	throws MalformedURLException, InterruptedException 

	{
		try {
			deviceEnvironment = configProp.getProperty("DeviceEnvironment");
			platform = configProp.getProperty("platform");
			iOSVersion = configProp.getProperty("iOSVersion");
			deviceNameiOS = configProp.getProperty("deviceNameiOS");
			File applicationParentDirectoryPath = new File(System.getProperty("user.dir")+ "\\src\\test\\resources\\App");
			System.out.println(System.getProperty("user.dir")+"\\src\\test\\resources\\App");
			File app = new File(applicationParentDirectoryPath, "cocus.ipa");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			if (platform.toUpperCase().equals("IOS")) {
				if(!deviceEnvironment.equals("Remote"))
				{
					capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, iOSVersion);
					capabilities.setCapability("platformName", platform);
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceNameiOS);
					capabilities.setCapability(MobileCapabilityType.UDID, "auto");
					capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
					capabilities.setCapability("noReset", true);	
					capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 10800);
					capabilities.setCapability("autoLaunch", autoLaunchFlag);
					capabilities.setCapability("autoAcceptAlerts", true);
					capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
					driver = new IOSDriver<IOSElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
					iOSDriver = driver;
				}
				
		}
	} catch(Exception e) {
		Report.failTestStop(" Device is not connected to appium programatically" + e );
	}
	return iOSDriver;
}

	public static void MobileDriverInitialisation() {
		
		
		
	}
public static AndroidDriver<AndroidElement> StartAndroidRemoteDriver(AndroidDriver<AndroidElement> driver,boolean autoLaunchFlag)
		throws MalformedURLException, InterruptedException {

	DesiredCapabilities capabilities = new DesiredCapabilities();
	//		capabilities.setCapability("app", "https://ctc-device-farm-wm.s3.us-west-1.amazonaws.com/general/apkstorage/1845e7ce-fd84-4f14-9f5c-e38b39ae907b/DA_5_7_6_20210415_QA.apk");
	capabilities.setCapability("platformName", platform);
	//		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
	//		capabilities.setCapability(MobileCapabilityType.BROWSER_VERSION, "80.0.3987.99");
	capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "R52N20DJTTP"); //R52N20DJTTP
	capabilities.setCapability(MobileCapabilityType.UDID, "R52N20DJTTP");
	capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
	capabilities.setCapability("appium:AuthToken", "1432c8f5-1a81-4837-893a-3187ce59ef09");
	capabilities.setCapability("appPackage", "com.wm.ocs.android.da");
	capabilities.setCapability("appActivity", "com.wm.ocs.android.da.DriverActivity");
	capabilities.setCapability("noReset", true);
	capabilities.setCapability("autoAcceptAlerts", true);
	capabilities.setCapability("appium:autoLaunch", autoLaunchFlag);
	capabilities.setCapability("appium:SessionName", "DA POC Run 2");
	capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 10800);
	//		ChromeOptions chromeOptions = new ChromeOptions();
	//		chromeOptions.setExperimentalOption("w3c", false);
	//		capabilities.merge(chromeOptions);

	//	driver = new AndroidDriver<AndroidElement>(new URL("https://dev-us-pao-0.headspin.io:7047/v0/0822f80cd1b74677917fdd04e2029b2e/wd/hub"), capabilities);
	driver = new AndroidDriver<AndroidElement>(new URL("https://wm-mobilecloud-us-east.quinnox.info:443/wd/hub"), capabilities);
	//	driver = new AndroidDriver<AndroidElement>(new URL("https://wm-mobilecloud-ap-south.quinnox.info:443/wd/hub"), capabilities);
	androidDriver = driver;

	return driver;
}


}
