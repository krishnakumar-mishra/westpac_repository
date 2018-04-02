package project.resources;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.poi.util.IOUtils;

import com.relevantcodes.extentreports.ExtentReports;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import project.commonfunction.HomePageCommonFunctions;
//import project.commonfunction.commonFunction;

public class HomePageStepDefinitions extends HomePageCommonFunctions{
public 	HashMap<String, String> testData = new HashMap<String, String>();

	
	@Before
	public void setUp(Scenario result) throws Exception{
		ScenarioName = result.getName();
		TestIdentifier = ScenarioName.substring(0, ScenarioName.lastIndexOf("_"));
		System.out.println("TestIdentifier : "+TestIdentifier);
		
		HTML_Report = result;
		scenarioResultFlag = true;
		
		
		//HashMap<String, String> testData = new HashMap<String, String>();
		System.out.println("Initiating Sheet Fetching");
		
		testData = getData("DemoSheet","Test002");
		System.out.println(testData);
		
		if(!ExtentReportFlag)
		{
			reportPath = System.getProperty("user.dir")+"\\target\\"+"MyTest_ExecutionReport"+"_"+getCurrentTimeStamp();
			imagePath = reportPath+"\\"+"images";
			new File(reportPath).mkdir();
			new File(imagePath).mkdir();
			
			extent = new ExtentReports(reportPath+"\\MyTest_Execution_Report.html", true);
			extent.config().documentTitle("MyReport_TitleKK");
			extent.config().reportHeadline("MyReport_HeadlineKK"+getCurrentTimeStamp().substring(0, 8));
			
			ExtentReportFlag = true;
			
		}
		
		test = extent.startTest(TestIdentifier);
		System.out.println("Completed Cucumber Method");
		
		
	}
	
	
	
	
	@Given ("^Customer access the Westpac application$")
	public void firstMethod() throws Exception{
		System.out.println("First Method");
		
		//GetSheet("DemoSheet");
//		System.out.println(testData.get("FirstName"));
		AccessLink(testData.get("URL"));
		System.out.println("Link accessed");
		
		
	}
	
	@When ("^Customer navigates to Currency Converter page$")
	public void navigatesCurrencyConverter() throws Exception{
		openCurrencyConverter();
	}
	
//	@And ("^Did not provide any amount to convert and clicks on Convert button$")
//	public void convertBlankCurrencyAmount() throws Exception{
//		convertBlankAmount();
//	}
		
	@After
	public void entTestCase(Scenario result) throws URISyntaxException, IOException, Exception{
		if(result.isFailed() && scenarioResultFlag)
			reportResult("Cucumber marked scenario as Fail due to failure in last validation point", false, true);
			extent.endTest(test);
		
		
		if(result.isFailed() && (verifyStatus && !verifyStatusFalse)){
			File screenshot = takeScreenshot("error_screenshot");
			InputStream  screenshotstream = new FileInputStream(screenshot);
			result.embed(IOUtils.toByteArray(screenshotstream), "image/jpeg");
			
		}
			closeAllBrowsers();
			extent.flush();
	}



}
