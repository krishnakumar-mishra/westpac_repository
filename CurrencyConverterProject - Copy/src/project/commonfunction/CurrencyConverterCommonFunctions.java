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
	
	public void verifyErrorMessageForBlankAmpount() throws Exception{
		

		String errorMessage_actual =	WebAction.getText(ErrorMessage);
		String errorMessage_expected = "Please enter the amount you want to convert.";
		if(errorMessage_actual.equals(errorMessage_expected))
			WebAction.reportResult("Error message is valid", true, true);
		else
			WebAction.reportResult("Error message is valid", false, true);
		
	}
	
	public void convertAmount(String value,String convertFrom,String convertTo) throws Exception{

		WebAction.selectFromDropdown(ConvertFromDropDownID, convertFrom);
		WebAction.type(AmountTextBoxID, value);
		WebAction.selectFromDropdown(ConvertToDropDownID, convertTo);
		WebAction.Click(ConvertButtonID);
		unconditionalWait(5);
	}
	
	public void verifyAndGetDetailsOfExchangeRates(String conversion) throws Exception{
		unconditionalWait(3);
        if(WebAction.isDisplayed(ExchangeRatesPanelXpath)){
        	reportResult("Exchange Rates for "+conversion+" currency details", true, true);
        	System.out.println("Exchange Rate details for "+conversion+" are: "+getText(ExchangeRatesPanelXpath));
        }
        else
        	reportResult("Exchange Rates for provided currency details", false, true);
	}
	

}
