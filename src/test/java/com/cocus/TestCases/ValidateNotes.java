package com.cocus.TestCases;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.cocus.BaseTest.BaseClass;
import com.cocus.ObjectRepository.Mainscreen;
import com.cocus.driver.MobileDriver;
import com.cocus.pojo.Result;
import com.cocus.pojo.Result.Name;
import com.cocus.utils.BaseMethods;
import com.cocus.utils.Report;

public class ValidateNotes extends BaseClass{

	Mainscreen MainscreenObj;

	@Test
	public void ValidateNotes() throws MalformedURLException, InterruptedException {
		try {
			test = Report.testCreate(extent,getClass().getName()+"_" +deviceName + "_" + deviceID+ "_"+androidVersion+" ");
			Result TestData = BaseMethods.getDataFromApi(configProp.getProperty("URI"), 200);
			MobileDriver.StartAndroidDeviceWithAppium(androidDriver, false);
			MainscreenObj = new Mainscreen(androidDriver);
			Name nm = TestData.getName();
			String Title = nm.getTitle()+" "+nm.getFirst()+" "+nm.getLast();
			MainscreenObj.validateNotesAddition(Title);
		} catch (Exception e) {
			Report.failTestStop("Validate Notes Failed" + e);
		}
		Report.endTest();
		androidDriver.quit();
	}

}
