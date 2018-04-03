package project.commonfunction;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;

import project.action.WebAction;


public class CurrencyConverterCommonFunctions extends WebAction{
	public static String ConvertButtonID = "id=convert";
	public static String ErrorMessage = "xpath=//div[@id='errordiv']//li";
	public static String ConvertFromDropDownID = "id=ConvertFrom";
	public static String ConvertToDropDownID = "id=ConvertTo";
	public static String AmountTextBoxID = "id=Amount";
	public static String ExchangeRatesPanelXpath = "xpath=//em";
	
	
	public void convertBlankAmount() throws Exception{

		WebAction.Click(ConvertButtonID);
		unconditionalWait(10);
	}
	
	
	/*
	 * This method will get the error message from the application 
	 * and compares with the expected message from the test data sheet
	 */
	public void verifyErrorMessageForBlankAmpount() throws Exception{
		

		String errorMessage_actual =	WebAction.getText(ErrorMessage);
		String errorMessage_expected = testData.get("Error Message");
		
		System.out.println("actual "+errorMessage_actual);
		System.out.println("expected "+errorMessage_expected);
		
		//This is a checkpoint to validate the error message displayed on screen with the expected
		//message i.e., being fetched from test data
		if(errorMessage_actual.equals(errorMessage_expected))
			WebAction.reportResult("Error message is valid", true, true);
		else
			WebAction.reportResult("Error message is valid", false, true);
		
	}
	
	
	/*
	 * This method will convert one currency to another for the provided amount
	 */
	public void convertAmount(String value,String convertFrom,String convertTo) throws Exception{

		WebAction.selectFromDropdown(ConvertFromDropDownID, convertFrom);
		WebAction.type(AmountTextBoxID, value);
		WebAction.selectFromDropdown(ConvertToDropDownID, convertTo);
		WebAction.Click(ConvertButtonID);
		unconditionalWait(5);
	}
	
	
	/*
	 * This method will verify and embed the screen in report, if the currency conversion is successful
	 * by checking the exchange rate details is displayed and print the same details in console as well
	 */
	public void verifyAndGetDetailsOfExchangeRates(String conversion) throws Exception{
		unconditionalWait(3);
		
		//This is a checkpoint to check if the conversion is done.
        if(WebAction.isDisplayed(ExchangeRatesPanelXpath)){
        	reportResult("Exchange Rates for "+conversion+" currency details", true, true);
        	System.out.println("Exchange Rate details for "+conversion+" are: "+getText(ExchangeRatesPanelXpath));
        }
        else
        	reportResult("Exchange Rates for provided currency details", false, true);
	}
	

}
