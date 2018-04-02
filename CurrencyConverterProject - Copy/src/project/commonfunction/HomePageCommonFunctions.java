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
//	public static boolean scenarioResultFlag = false;
	public static boolean ExtentReportFlag = false;
	public static String reportPath = "";
//	public static String imagePath = "";
	
	public static String FX_travel_MigrantID = "id=ubermenu-section-link-fx-travel-and-migrant-ps";
	public static String CurrencyConverterID = "id=ubermenu-item-cta-currency-converter-ps";
	public static String CurrencyConverterlabel = "xpath=//h1[contains(text(),'Currency converter')]";
	
	
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
	
//	public void convertBlankAmount() throws Exception{
//		unconditionalWait(10);
//		int noOfFrame = driver.findElements(By.tagName("iframe")).size();
//		System.out.println(noOfFrame);
//		driver.switchTo().frame("westpac-iframe");
////		System.out.println(driver.getPageSource());
////		System.out.println(driver.findElement(By.id("convert")).isDisplayed());
//		WebAction.Click(ConvertButtonID);
//		unconditionalWait(5);
//	}
	
	
//	public void GetSheet(String sheetName) throws Exception{
//		HashMap<String, String> testSheet = new HashMap<String, String>();
//		System.out.println("Initiating Sheet Fetching");
//		
//		testSheet = getData(sheetName,"Test001");
//		
//		System.out.println("KK:"+testSheet.get("FirstName"));
//		
//		System.out.println(testSheet);
//		System.out.println("Sheet successfully fetched");
//		
//	}
	
//	public String getSnap() throws IOException, Exception{
//		 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//		 String snapPath = imagePath+"\\"+"img_"+getCurrentTimeStamp()+".png";
//		 FileUtils.copyFile(scrFile, new File(snapPath));
//		return snapPath;
//	}
//	
//	
//	public void reportResult(String stepDescription, boolean stepStatus, boolean snapForPass) throws Exception{
//		if(stepStatus){
//			if(snapForPass){
//				test.log(LogStatus.PASS, stepDescription+" - PASS", test.addScreenCapture(getSnap()));
//				System.out.println(stepDescription+" is PASS");
//			}
//			else{
//				test.log(LogStatus.FAIL, stepDescription+" - PASS");
//				System.out.println(stepDescription+" is PASS");
//			}
//				
//		}
//		else
//		{
//			test.log(LogStatus.FAIL, stepDescription+" - FAIL", test.addScreenCapture(getSnap()));
//			scenarioResultFlag = false;
//			System.out.println(stepDescription+" is FAIL");
//		}
//	}

}
