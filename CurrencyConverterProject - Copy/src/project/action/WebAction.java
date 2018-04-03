package project.action;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.james.mime4j.field.ParseException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.util.IOUtils;
//import org.openqa.jetty.html.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.Scenario;




public class WebAction {
	
	public static Properties prop;
	public static HashMap<String,String> envDetails = new HashMap<String,String>();
	public static HashMap<String,String> testData = new HashMap<String,String>();
	public static boolean verifyStatus;
	public static boolean verifyStatusFalse=false;
	public static String verifyErrorMessage="";
	public static WebDriver driver;
	public static String Body_tag ="xpath=//body";
	public static String Form_tag = "xpath=//form";
	public static boolean SafeBrowserClose = true;
	public static String Sheet_Name="";
	public static String Row_Name="";
	public static String Currently_Running_Script_Name=null;
	public static WebDriverWait wait;		
	public static String Output_Folder_Name=null;
	public static Scenario HTML_Report;
	public static String ScenarioName = "";
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	public static String imagePath = "";
	public static boolean scenarioResultFlag = false;
	
	
	
	
	
	public static Properties loadProperties() throws Exception{
	
		prop = new Properties();
		InputStream input = new FileInputStream("src/test/resources/com/oregon/wbc/resources/OR.properties");
		prop.load(input);
		return prop;
	}
	
	public static void setUp(String inputRowKeyword) throws Exception{
		envDetails = getData("Set_Up",inputRowKeyword);
		System.out.println(envDetails);
		System.out.println("1. Completed Setup method");
		envSetUp(testData.get("BrowserName"),envDetails.get("ElementTimeOut"),envDetails.get("PageLoadTimeout"));
		System.out.println("2. Completed Setup method");
		verifyStatus = true;
		verifyStatusFalse=false;
		verifyErrorMessage="";
		System.out.println("Completed Setup method");
	}

	/*
	 * This method is used to insert time on HTML report
	 */
	
	public static void ScriptExecutionTime(String msg) throws Exception{
		verifyMethod(msg,false);
		verifyResult();
	}
	
	
	/*
	 * used to initiate the browser and set the time outs
	 */
	
	public static void envSetUp(String BrowserName, String elemTimeout, String pageLoadTime) throws Exception{
		initiateBrowser(BrowserName);
		maximizeWindow();
		elementTimeOut(elemTimeout);
		pageLoadTimeOut(pageLoadTime);
	}
	
	public static String createOutputFolderName(String scriptName) throws Exception{
		String outputFolderName = scriptName+"-"+new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new GregorianCalendar().getTime());
		return outputFolderName;
	}
	
	/*
	 * This method is used to initiate the browsers profile
	 */
	
	public static void initiateBrowser(String BrowserName) throws Exception{
		if(BrowserName.equals("IE")){
			File file = new File("driverservers/IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			System.out.println("Initiating Browser");
			driver = new InternetExplorerDriver();
		}
		
		else if(BrowserName.equalsIgnoreCase("FF")||BrowserName.equalsIgnoreCase("firefox")){
			ProfilesIni profile = new ProfilesIni();
			FirefoxProfile myprofile = profile.getProfile("WOWProfile");
			driver = new FirefoxDriver(myprofile);
		}
		
		else if(BrowserName.equalsIgnoreCase("Chrome")){
			File file = new File("driverservers/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			System.out.println("Initiating Browser");
			driver = new ChromeDriver();
		}
	}
	
	
	public static By elementLocator(String locator) throws Exception{
		
		if(locator.startsWith("xpath"))
			return By.xpath(locator.substring(6));
		
		else if(locator.startsWith("css"))
			return By.cssSelector(locator.substring(4));
		
		else if(locator.startsWith("id"))
			return By.id(locator.substring(3));
		
		else if(locator.startsWith("class"))
			return By.className(locator.substring(6));
		
		else if(locator.startsWith("name"))
			return By.name(locator.substring(5));
		
		else if(locator.startsWith("tag"))
			return By.tagName(locator.substring(4));
		
		else if(locator.startsWith("link"))
			return By.linkText(locator.substring(5));
		
		else throw new IllegalArgumentException("Invalid locator format: "+locator);
	}
	
	public static void elementTimeOut(String time) throws Exception{
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(time), TimeUnit.SECONDS);
	}
	
	public static void pageLoadTimeOut(String time) throws Exception{
		driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(time), TimeUnit.SECONDS);
	}
	
	
	/*
	 * This method will get the URL of the application from the testdata and launch the URL in browser
	 * Here to deal with the excel sheet fetching, poi-3.10.1 jar is used
	 */
	public static void openURL(String URL) throws Exception{
		driver.get(URL);
		unconditionalWait(5);
	}
	
	
	/*
	 * closes the current window
	 */
	public static void close() throws Exception{
		try{
			driver.close();
		}
		catch(Exception e){
			
		}
	}
	
	/*
	 * closes all the browser
	 */
	public static void closeAllBrowsers() throws Exception{
		try{
			driver.quit();
		}
		catch(Exception e){
			
		}
	}
	
	public static WebElement findWebElement(By locator) throws Exception{
		WebElement elem = null;
		try{
			elem = driver.findElement(locator);
		}
		
		catch(NoSuchElementException e){
			verifyMethod("locator is not identified",false);
			verifyResult();
		}
		
		return elem;
	}
	
	
	public static void Click(String locator) throws Exception{
		unconditionalWait(1);
		By byLocator = elementLocator(locator);
		if(isElementExists(byLocator) & driver.findElement(byLocator).isEnabled())
			driver.findElement(byLocator).click();
		else
			verifyMethod("Unable to find/perform click action on the element using locator :"+locator, false);
			verifyResult();
	}
	
	
	public static void type(String locator, String text) throws Exception{
		clear(locator);
		By byLocator = elementLocator(locator);
		driver.findElement(byLocator).sendKeys(text);
	}
	
	
	public static void enterText(String locator, String text) throws Exception{
		By byLocator = elementLocator(locator);
		Actions act = new Actions(driver);
		act.sendKeys(text).build().perform();
		
	}
	
	
	public static void typeKeyStroke(String locator, Keys key) throws Exception{
		By byLocator = elementLocator(locator);
		if(isElementExists(byLocator))
			driver.findElement(byLocator).sendKeys(key);
		else
		{
			verifyMethod("Unable to find element using locator :"+locator, false);
			verifyResult();
		}
	}
	
	public static void clear(String locator) throws Exception{
		By byLocator = elementLocator(locator);
		if(isElementExists(byLocator))
			driver.findElement(byLocator).clear();
		else
		{
			verifyMethod("Unable to find element using locator :"+locator, false);
			verifyResult();
		}
	}
	
	
	/*
	 * return the extracted text at the specified location
	 */
	
	public static String getText(String locator) throws Exception{
		By byLocator = elementLocator(locator);
		if(isElementExists(byLocator)){
//			System.out.println(locator+" From web page:"+driver.findElement(byLocator).getText());
			return driver.findElement(byLocator).getText();
		}
		
		else
		{
			verifyMethod("Unable to find the element using locator :"+locator, false);
			verifyResult();
			return null;
		}
	}
	
	public static boolean isDisplayed(String locator) throws Exception{
		try{
			By byLocator = elementLocator(locator);
			return driver.findElement(byLocator).isDisplayed();
		}
		catch(NoSuchElementException e){
			return false;
		}
	}
	
	public static boolean isEnabled(String locator) throws Exception{
		try{
			By byLocator = elementLocator(locator);
			return driver.findElement(byLocator).isEnabled();
		}
		catch(NoSuchElementException e){
			return false;
		}
	}
	
	
	public static boolean isSelected(String locator) throws Exception{
		try{
			By byLocator = elementLocator(locator);
			return driver.findElement(byLocator).isSelected();
		}
		catch(NoSuchElementException e){
			return false;
		}
	}
	
	
	/*
	 * returns count of elements
	 */
	
	public static int countOfElements(String locator) throws Exception{
		By byLocator = elementLocator(locator);
		verifyMethod("Unable to find the element using locator :"+locator, isElementExists(byLocator));
		verifyResult();
		return driver.findElements(byLocator).size();
	}
	
	
	
	/*
	 * returns the current window URL
	 */
	
	public static String getCurrentURL() throws Exception{
		return driver.getCurrentUrl();
	}
	
	public static String getTitle() throws Exception{
		return driver.getTitle();
	}
	
	public static String getPageSource() throws Exception{
		return driver.getPageSource();
	}
	
	public static boolean isTextPresent(String text) throws Exception{
		String pageSource = getText(Body_tag);
		if(isElementExists(elementLocator(Form_tag))){
			String formSource = getText(Form_tag);
			pageSource = pageSource+formSource;
		}
		
		System.out.println(text+" isTextPresent:"+pageSource.contains(text));
		System.out.println("***********************");
		
		return pageSource.contains(text);
	}
	
	public static boolean isTextPresentIgnoreNewLine(String text) throws Exception{
		String pageSource = getText(Body_tag).replace("\n", " ");
		if(isElementExists(elementLocator(Form_tag))){
			String formSource = getText(Form_tag);
			pageSource = pageSource+formSource;
		}
		
		System.out.println(text+" isTextPresent:"+pageSource.contains(text));
		System.out.println("***********************");
		System.out.println(pageSource);
		System.out.println("***********************");

		return pageSource.contains(text);
	}
	
	
	public void browserRefresh() throws Exception{
		driver.navigate().refresh();
	}
	
	public void browserForward() throws Exception{
		driver.navigate().forward();
	}
	
	public void browserBackward() throws Exception{
		driver.navigate().back();
	}
	
	
	public static void switchToWindow(int windownumber) throws Exception{
		String[] windowHandles = driver.getWindowHandles().toArray(new String[driver.getWindowHandles().size()]);
		driver.switchTo().window(windowHandles[windownumber]);
		maximizeWindow();
	}
	
	public static void switchToFrame(String locator) throws Exception{
		By byLocator = elementLocator(locator);
		verifyMethod("Unable to find the element using locator :"+locator, isElementExists(byLocator));
		verifyResult();
		driver.switchTo().frame(driver.findElement(byLocator));
	}
	
	public static void alertOk() throws Exception{
		driver.switchTo().alert().accept();
	}
	
	public static void alertCancel() throws Exception{
		driver.switchTo().alert().dismiss();
	}
	
	public static void pageScroll(String loc) throws Exception{
		int pageScroll = 250;
		if(loc.equalsIgnoreCase("UP"))
			pageScroll=-250;
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeAsyncScript("scroll(0, "+pageScroll+");");
	}
	
	public static void verifyTextAttheLocation(String location, String msg) throws Exception{
		System.out.println("Actual :"+getText(location));
		System.out.println("Expected :"+msg);
		System.out.println(getText(location).equals(msg));
		System.out.println("=================================");
		verifyMethod("Text extracted is not matched with given text",getText(location).equals(msg));
		verifyResult();
		
	}
	
	public static void compareTheTextContent(String msg1, String msg2) throws Exception{
		verifyMethod(msg1+" is not matched with "+msg2,msg1.equals(msg2));
		verifyResult();
		
	}
	
	/*
	 * This method will maximize the window size 
	 */
	public static void maximizeWindow() throws Exception{
		driver.manage().window().maximize();
	}
	
	public static String getValueAttribute(String locator) throws Exception{
		By byLocator = elementLocator(locator);
		verifyMethod("Unable to find the element using locator :"+locator, isElementExists(byLocator));
		verifyResult();
		return driver.findElement(byLocator).getAttribute("value");
	}
	
	public static String getPlaceHolderAttribute(String locator) throws Exception{
		By byLocator = elementLocator(locator);
		verifyMethod("Unable to find the element using locator :"+locator, isElementExists(byLocator));
		verifyResult();
		return driver.findElement(byLocator).getAttribute("Placeholder");
	}
	
	
	public static String getClassAttribute(String locator) throws Exception{
		By byLocator = elementLocator(locator);
		verifyMethod("Unable to find the element using locator :"+locator, isElementExists(byLocator));
		verifyResult();
		return driver.findElement(byLocator).getAttribute("class");
	}
	
	
	public static String getAttribute(String locator, String attribute) throws Exception{
		By byLocator = elementLocator(locator);
		verifyMethod("Unable to find the element using locator :"+locator, isElementExists(byLocator));
		verifyResult();
		return driver.findElement(byLocator).getAttribute(attribute);
	}
	
	
	public static void selectFromDropdown(String locator, String option) throws Exception{
		waitForElement(locator);
		By byLocator = elementLocator(locator);
		verifyMethod("Unable to find the element using locator :"+locator, isElementExists(byLocator));
		verifyResult();
		Select dropdown = new Select(driver.findElement(byLocator));
		dropdown.selectByVisibleText(option);
	}
	
	
	public static WebElement getWebElementAtIndex(String locator, int Index) throws Exception{
		By byLocator = elementLocator(locator);
		List <WebElement> colWebElements = driver.findElements(byLocator);
		if(colWebElements.size()> 0){
			return (colWebElements.get(Index));
		}
		return null;
	}
	
	
	public static String getSelectedOptionFromDropdown(String locator) throws Exception{
		waitForElement(locator);
		By byLocator = elementLocator(locator);
		verifyMethod("Unable to find the element using locator :"+locator, driver.findElements(byLocator).size() > 0);
		verifyResult();
		Select dropdown = new Select(driver.findElement(byLocator));
		
		return dropdown.getFirstSelectedOption().getText();
	}
	
	public static void pressKeyBoardEnter() throws Exception{
		Robot robot = new Robot();
		
		robot.keyPress(KeyEvent.VK_ENTER);
	}
	
	public static HashMap<String, String> getInputData(String inputSheetName, String inputRowKeyword) throws Exception{
		HashMap<String, String> custDetails = new HashMap<String, String>();
		SafeBrowserClose = true;
		Sheet_Name = inputSheetName;
		Row_Name = inputRowKeyword;
		Currently_Running_Script_Name = inputRowKeyword;
		custDetails = getData(Sheet_Name, Row_Name);
		
		return custDetails;
	}
	
	public static HashMap<String, String> getData(String sheetName, String rowKeyWord) throws Exception{
		HashMap<String, String> inputData = new HashMap<String, String>();
		
		FileInputStream file = new FileInputStream(new File("testdata/Demo.xls"));
		
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		
		HSSFSheet sheet = workbook.getSheet(sheetName);
		
		int rowCount = sheet.getPhysicalNumberOfRows();
		
		int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
		
		System.out.println(rowCount+"*********"+columnCount);
		
		for(int i=0;i<rowCount;i++){
			if(sheet.getRow(i).getCell(0).toString().equals(rowKeyWord) && sheet.getRow(i).getCell(1).toString().equalsIgnoreCase("yes")){
				for(int k=0;k<columnCount;k++){
					System.out.println("reading data from column :"+sheet.getRow(0).getCell(k).toString());
					System.out.println(sheet.getRow(i).getCell(k).getStringCellValue());
					inputData.put(sheet.getRow(0).getCell(k).toString(), sheet.getRow(i).getCell(k).getStringCellValue());
				}
				break;
			}
		}
		file.close();
		
		return inputData;
	}
	
	
	public static void JavaScriptClick(String locator) throws Exception{
		By byLocator = elementLocator(locator);
		verifyMethod("Unable to find/perform click action on the element using locator :"+locator, isElementExists(byLocator));
		verifyResult();
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		js.executeScript("arguments[0].click();",driver.findElement(byLocator));
	}
	
	
	/*
	 * 
	 * waits for maximum of 10 seconds until the web element is visible on the page.
	 */
	public static void waitForElementElementToBeClickable(String locator) throws Exception{
		By byLocator = elementLocator(locator);
		wait = new WebDriverWait(driver,10);
		verifyMethod("Unable to find the element using locator :"+locator, driver.findElements(byLocator).size() > 0);
		verifyResult();
		wait.until(ExpectedConditions.elementToBeClickable(byLocator));
		
	}
	
	/*
	 * 
	 * waits for maximum of 10 seconds until the web element is visible on the page.
	 */
	public static void waitForElement(String locator) throws Exception{
		By byLocator = elementLocator(locator);
		wait = new WebDriverWait(driver,10);
		verifyMethod("Unable to find the element using locator :"+locator, driver.findElements(byLocator).size() > 0);
		verifyResult();
		wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
		
	}
	/*
	 * 
	 * waits for maximum of 30 seconds until the web element is visible on the page.
	 */
	public static void waitForElement30seconds(String locator) throws Exception{
		By byLocator = elementLocator(locator);
		wait = new WebDriverWait(driver,30);
		verifyMethod("Unable to find the element using locator :"+locator, driver.findElements(byLocator).size() > 0);
		verifyResult();
		wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
		
	}
	
	
	/*
	 * 
	 * waits for maximum of 10 seconds until the expected text is shown in element located.
	 */
	public static void waitForText(String locator, String text) throws Exception{
		By byLocator = elementLocator(locator);
		wait = new WebDriverWait(driver,10);
		verifyMethod("Unable to find the element using locator :"+locator +" Timestamp is :"+getCurrentTimeStamp(), driver.findElement(byLocator).isDisplayed());
		verifyResult();
		wait.until(ExpectedConditions.textToBePresentInElementLocated(byLocator,text ));
		
	}
	
//	public static void waitForOptionsUnderDropdown(String locator) throws Exception{
//		By byLocator = elementLocator(locator);
//		verifyMethod("Unable to find the element using locator :"+locator +" Timestamp is :"+getCurrentTimeStamp(), driver.findElement(byLocator).isDisplayed());
//		verifyResult();
//		final Select droplist = new Select (driver.findElement(byLocator));
//		new FluentWait<WebDriver>(driver).withTimeout(60,TimeUnit.SECONDS).pollingEvery(10,TimeUnit.MILLISECONDS).until(new Predicate<WebDriver>(){
//			
//			public boolean apply(WebDriver d){
//				return (!droplist.getOptions().isEmpty());
//			}
//		});
//	}
	
	public static void verifyDropdownOptions(String locator, String options[]) throws Exception{
		By byLocator = elementLocator(locator);
		verifyMethod("Unable to find the element using locator :"+locator +" Timestamp is :"+getCurrentTimeStamp(), driver.findElement(byLocator).isDisplayed());
		verifyResult();
		
		Select dropList = new Select(driver.findElement(byLocator));
		List<WebElement> actualOptions = dropList.getOptions();
		
		for(int i=0; i<options.length; i++){
			System.out.println(options[i].toString()+"*****"+actualOptions.get(i).getText().trim());
			verifyMethod(options[i]+" is not shown in the dropdown",options[i].toString().equals(actualOptions.get(i).getText().trim()));
		}
		verifyResult();
	}
	
	public static void unconditionalWait(int seconds) throws Exception{
		Thread.sleep(seconds*1000);
	}

	
	public static void putData(String sheetName, String rowName, HashMap<String, String> custDetails) throws Exception{
		
		FileInputStream file = new FileInputStream(new File("testData/TESTDATA_BBO_SIT.xls"));
		
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		
		HSSFSheet sheet = workbook.getSheet(sheetName);
		
		int rowCount = sheet.getPhysicalNumberOfRows();
		int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
		
		int rowMatch,cellMatch;
		for(rowMatch=0; rowMatch<rowCount;rowMatch++){
			if(sheet.getRow(rowMatch).getCell(0).toString().equals(rowName))
				break;
		}
		
		for(cellMatch=1; cellMatch<columnCount; cellMatch++){
			
			Cell cell = sheet.getRow(rowMatch).getCell(cellMatch);
			
			if(!cell.getStringCellValue().equals("NA"))
				cell.setCellValue(custDetails.get(sheet.getRow(0).getCell(cellMatch).toString()));
		}
		
		FileOutputStream outFile = new FileOutputStream(new File("testData/TESTDATA_BBO_SIT.xls"));
		workbook.write(outFile);
		outFile.close();
	}
	
	public static void mouseOver(String locator) throws Exception{
		Actions action = new Actions(driver);
		By byLocator = elementLocator(locator);
		verifyMethod("Unable to find the element using locator :"+locator+"TimeStamp is :"+getCurrentTimeStamp(),driver.findElement(byLocator).isDisplayed());
		verifyResult();
		action.moveToElement(driver.findElement(byLocator)).build().perform();
		
	}
	
	
	/*
	 * used to capture screenshot and adds it to reportNG report
	 */
	
	public static void captureScreenshot(String msg, boolean status) throws Exception{
		String fontColor = status?"green":"red";
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		String screenshot = msg;
		
		String failureImageFileName = screenshot.replace(" ", "_").replace(":", "").trim()+".png";
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File("test-output/html/screenshots/"+Output_Folder_Name+"/"+failureImageFileName));
		
		String userDirector = System.getProperty("user.dir")+"/test-output/html/screenshots/"+Output_Folder_Name+"/";
		Reporter.log("<a style=\"text-decoration: none;\" href=\""+userDirector+failureImageFileName+"\"><h4 style=\"color:"+fontColor+";font-weight: bold;\">"+msg+"&nbsp;<img src =\"file:///"+userDirector+failureImageFileName+"\" alt=\"\""+ "height='70' width='70'/> "+"<br />");
		
		
	}
	
	public static long getRandomNumber(long first, long last) throws Exception{
		Random r = new Random();
		long number = first+((long)(r.nextDouble()*(last-first)));
		return number;
	}
	
	public static String getRandomString() throws Exception{
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for(int i=0; i<10; i++){
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		
		return sb.toString();
	}
	
	public static String getRandomDOB(int start, int last) throws Exception{
		String DOBDay, DOBMonth, DOBYear;
		long day = getRandomNumber(1L,28L);
		long month = getRandomNumber(1L,12L);
		long year = getRandomNumber(1970L, 1990L);
		
		if(day<10)
			DOBDay=0+""+day;
		else
			DOBDay = ""+day;
		
		if(month<10)
			DOBMonth=0+""+month;
		else
			DOBMonth = ""+month;
		
		DOBYear = ""+year;
		
		return DOBDay+"/"+DOBMonth+"/"+DOBYear;
	
	}
	
	public static String getMonthNumber(String month) throws Exception{
		if(("January".contains(month))) 
			return "01";
		else if(("February".contains(month))) 
			return "02";
		else if(("March".contains(month))) 
			return "03";
		else if(("April".contains(month))) 
			return "04";
		else if(("May".contains(month))) 
			return "05";
		else if(("June".contains(month))) 
			return "06";
		else if(("July".contains(month))) 
			return "07";
		else if(("August".contains(month))) 
			return "08";
		else if(("September".contains(month))) 
			return "09";
		else if(("October".contains(month))) 
			return "10";
		else if(("November".contains(month))) 
			return "11";
		else if(("December".contains(month))) 
			return "12";
		
		else 
			return null;
		
		
		
	}
	
	public static String getMonthName(int month) throws Exception{
		String[] monthNames = {"January","February","March","April","May","June","July","August","September","October","November","December"};
		
		return monthNames[month];
		}
	
	public static File takeScreenshot(String picture) throws Exception{
		try{
			File temp = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(temp, new File(System.getProperty("user.dir")+"/target/screenshots/"+File.separator+picture+".jpeg"));
			
			return temp;
			}
		catch (Exception e){
			throw e;
		}
	}
	
	public static void embedScreenshot(Scenario result, String name) throws Exception{
		File screenshot = takeScreenshot(name);
		InputStream screenshotStream = new FileInputStream(screenshot);
		result.embed(IOUtils.toByteArray(screenshotStream), "image/jpeg");
	}
	
	
	/*
	 * used to verify the text on the page
	 */
	public static void verifyMethod(String errMsg, boolean status) throws Exception{
		verifyStatus = verifyStatus&&status;
		if(!status)
			verifyErrorMessage= verifyErrorMessage+"\n"+errMsg;
	}
	
	public static void verifyMethodFalse(String errMsg, boolean status) throws Exception{
		verifyStatus = verifyStatus||status;
		if(status)
			verifyErrorMessage= verifyErrorMessage+"\n"+errMsg;
	}
	
	/*
	 * used to write the assert statements andembeds the screenshot to the HTML report
	 */
	public static void verifyResult() throws Exception{
		if((!verifyStatus||verifyStatusFalse) && !(verifyErrorMessage.isEmpty())){
			HTML_Report.write("Error Details Captured at :"+getCurrentTimeStamp()+"\n");
			HTML_Report.write(verifyErrorMessage);
			embedScreenshot(HTML_Report, "Page content");
			
		}
	}
	
	public static boolean isElementExists(By by) throws Exception{
		boolean isExists = true;
		try{
			driver.findElement(by);
		}
		catch(NoSuchElementException e){
			isExists = false;
		}
		
		return isExists;
	}
	
	public static void moveToElementID(String elementID) throws Exception{
		WebElement element = driver.findElement(By.id(elementID));
		Actions action = new Actions(driver);
		action.moveToElement(element);
		action.perform();
	}
	
	public static String getCurrentTimeStamp() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy hh mm ss");
		Date date = new Date();
		String dateTimeValue = dateFormat.format(date).replace(" ", "").toUpperCase();
		
		return dateTimeValue.substring(0, 9)+"_"+dateTimeValue.substring(9);
	}
	
	public static String getDateDDMMYYYYFormat(String sDate) throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("d-MMM-yy");
		Date date;
		try{
			date = dateFormat.parse(sDate);
			System.out.println(date);
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String ddMMYYYY = formatter.format(date);
			System.out.println(ddMMYYYY);
			
			return ddMMYYYY;
		}
		catch(Exception e){
			e.printStackTrace();
			
			return null;
		}
	}
	
	public static boolean verifyElement(String xpath) throws Exception{
		By byLocator = elementLocator(xpath);
		if(isElementExists(byLocator))
			return true;
		else
			return false;
	}
	
	
	public static String getSnap() throws IOException, Exception{
		 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		 String snapPath = imagePath+"\\"+"img_"+getCurrentTimeStamp()+".png";
		 FileUtils.copyFile(scrFile, new File(snapPath));
		return snapPath;
	}
	
	public static void reportResult(String stepDescription, boolean stepStatus, boolean snapForPass) throws Exception{
		if(stepStatus){
			if(snapForPass){
				test.log(LogStatus.PASS, stepDescription+" - PASS", test.addScreenCapture(getSnap()));
				System.out.println(stepDescription+" is PASS");
			}
			else{
				test.log(LogStatus.FAIL, stepDescription+" - PASS");
				System.out.println(stepDescription+" is PASS");
			}
				
		}
		else
		{
			test.log(LogStatus.FAIL, stepDescription+" - FAIL", test.addScreenCapture(getSnap()));
			scenarioResultFlag = false;
			System.out.println(stepDescription+" is FAIL");
		}
	}
	
}
