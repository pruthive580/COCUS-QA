package com.cocus.ObjectRepository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cocus.utils.Report;
import com.cocus.utils.Util;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @author bhargav.gembali
 *
 */
public class Mainscreen {


	AndroidDriver<AndroidElement> driver;

	public Mainscreen(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//android.widget.ImageButton[@resource-id='com.example.android.testing.notes.mock:id/fab_add_notes']")
	WebElement ADD_ACCEPT_BUTTON;
	
	@FindBy(xpath = "//android.widget.TextView[@text='New Note']")
	WebElement TITLE_TEXT;
	
	@FindBy(xpath = "//android.widget.EditText[@resource-id='com.example.android.testing.notes.mock:id/add_note_title']")
	WebElement TITLE_TEXTBOX;
	
	@FindBy(xpath = "//android.widget.EditText[@resource-id='com.example.android.testing.notes.mock:id/add_note_description']")
	WebElement TITLE_DESCRIPTION;
	
	@FindBy(xpath = "//android.widget.TextView[@resource-id='com.example.android.testing.notes.mock:id/note_detail_title']")
	WebElement TITLE_SAVED;
	
	@FindBy(xpath = "//android.widget.TextView[@resource-id='com.example.android.testing.notes.mock:id/note_detail_description']")
	WebElement DESCRIPTION_SAVED;
	
	
	/**
	 * 
	 */
	public void clickAddAcceptButton() {
		try {
			Util.mobileClick(driver, ADD_ACCEPT_BUTTON);
			Report.passTestScreenshot(driver, "Add New Note/Accept Button", "Add Note/Accept Button Clicked");
		} catch (Exception e) {
			Report.failTestSnapshot(driver, "Add New Note/Accept Button", "Add Note/Accept Button Not Clicked");
		}
	}
	
	/**
	 * @param title
	 */
	public void validateNotesAddition(String title) {
		try {
			String date = Util.getDateinrequiredformat();
			clickAddAcceptButton();
			if(Util.validateMobileLabelText(driver, TITLE_TEXT, "New Note")) {
			      Util.mobileSendKeys(driver, TITLE_TEXTBOX, title, true, "Added Title in Note");
			      Util.mobileSendKeys(driver, TITLE_DESCRIPTION, date, true, "Added Date In Required Format In Description of Note");
			      clickAddAcceptButton();
			      Report.passTestScreenshot(driver, "Entered Title and Description in add new note", "Entered Title and Description in add new note");
			      validateNotesAfterSaving(title, date);
			} else {
				Report.failTestStop("Add New Note Window Not Observed");
			}
		} catch (Exception e) {
			Report.failTestStop("Validates Notes Addition Failed");
		}
	}
	
	/**
	 * @param title
	 * @param date
	 */
	public void validateNotesAfterSaving(String title, String date) {
		try {
			Util.wait_inMinutes(0.05);
			String actualtitle = Util.getTextFromElement(TITLE_SAVED);
			String actualDesc = Util.getTextFromElement(DESCRIPTION_SAVED);
			Util.compareStringValues(actualtitle, title, "Entered Title in the Notes");
			Util.compareStringValues(actualDesc, date, "Entered Description in the notes");
			Report.passTestScreenshot(driver, "Entered Values are same after saving", "Validation of entered details in notes");
		} catch (Exception e) {
			Report.infoTest("Entered details in note are not validated after saving" + e);
		}
	}
}
