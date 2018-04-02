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
	
	//Krishna
	public void AccessLink(String URL) throws Exception{
		initiateBrowser("Chrome");
		openURL(URL);
		maximizeWindow();
		System.out.println(driver.getCurrentUrl());
		if(driver.getCurrentUrl().equals("https://www.westpac.co.nz/"))
			reportResult("Link Accessed", true, true);
		else
			reportResult("Link Accessed", false, true);
		unconditionalWait(3);
	}
	
	public void openCurrencyConverter() throws Exception{
		WebAction.mouseOver(FX_travel_MigrantID);
		unconditionalWait(3);
		WebAction.Click(CurrencyConverterID);
		unconditionalWait(8);
		if(getText(CurrencyConverterlabel).equals("Currency converter"))
			reportResult("Currency converter page opened", true, true);
		else
			reportResult("Currency converter page opened", false, true);
		unconditionalWait(10);
		driver.switchTo().frame("westpac-iframe");
	}
	

}
