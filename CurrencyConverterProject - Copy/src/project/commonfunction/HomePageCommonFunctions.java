package project.commonfunction;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.relevantcodes.extentreports.LogStatus;

import project.action.WebAction;

public class HomePageCommonFunctions extends WebAction{

	public static String TestIdentifier = "";
	public static boolean ExtentReportFlag = false;
	public static String reportPath = "";

	
	public static String FX_travel_MigrantID = "id=ubermenu-section-link-fx-travel-and-migrant-ps";
	public static String CurrencyConverterID = "id=ubermenu-item-cta-currency-converter-ps";
	public static String CurrencyConverterlabel = "xpath=//h1[contains(text(),'Currency converter')]";
	
	/*
	 * This method is used for Browser setup and URL launch
	 */
	
	public void AccessLink(String URL) throws Exception{
		initiateBrowser("Chrome");
		openURL(URL);
		maximizeWindow();
		System.out.println(driver.getCurrentUrl());
		
		/*
		 * This is a checkpoint to verify and embed in the report that the correct URL is being launched
		 */
		if(driver.getCurrentUrl().equals(testData.get(URL)))
			reportResult("Link Accessed", true, true);
		else
			reportResult("Link Accessed", false, true);
		unconditionalWait(3);
	}
	
	
	/*
	 * This method will take the driver to Currency Converter page and verify the same 
	 * with the content present in page
	 */
	public void openCurrencyConverter() throws Exception{
		WebAction.mouseOver(FX_travel_MigrantID);
		unconditionalWait(3);
		WebAction.Click(CurrencyConverterID);
		unconditionalWait(8);
		
		//This is a checkpoint to verify if the driver lands into Currency Converter page
		// by checking the Currency Converter label present in the page
		if(getText(CurrencyConverterlabel).equals("Currency converter"))
			reportResult("Currency converter page opened", true, true);
		else
			reportResult("Currency converter page opened", false, true);
		unconditionalWait(10);
		
		//This will switch to the frame which is used for Currency converter
		driver.switchTo().frame("westpac-iframe");
	}
	

}
