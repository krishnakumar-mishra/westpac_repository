package project.resources;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import project.commonfunction.CurrencyConverterCommonFunctions;

public class CurrencyConverterStepDefinitions extends CurrencyConverterCommonFunctions{
	
	/*
	 * This step is created to click convert button when user tries to convert currency without providing any values.
	 */
	
	@And ("^Did not provide any amount to convert and clicks on Convert button$")
	public void convertBlankCurrencyAmount() throws Exception{
		convertBlankAmount();
	}
	
	
	/*
	 * This step is created to check the error message when user tries to convert the currency without providing any values.
	 */
	@Then ("^Verify the error message$")
	public void verifyError() throws Exception{
		verifyErrorMessageForBlankAmpount();
	}
	
	
	
	/*
	 * This step is created to convert one currency to the other
	 */
	@Then ("^Converts '(.*)' from '(.*)' to '(.*)'$")
	public void currencyConvertAmount(String value, String convertFrom, String convertTo) throws Exception{
		convertAmount(value,convertFrom,convertTo);
	}
	
	/*
	 * This step is created to check if the currency conversion is done and get the details of the same.
	 */
	@And ("^Verify and get the details of the '(.*)' exchange rates$")
	public void getDetailsOfExchangeRates(String conversion) throws Exception{
		verifyAndGetDetailsOfExchangeRates(conversion);
	}

}
