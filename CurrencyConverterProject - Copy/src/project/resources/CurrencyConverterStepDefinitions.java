package project.resources;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import project.commonfunction.CurrencyConverterCommonFunctions;

public class CurrencyConverterStepDefinitions extends CurrencyConverterCommonFunctions{
	@And ("^Did not provide any amount to convert and clicks on Convert button$")
	public void convertBlankCurrencyAmount() throws Exception{
		convertBlankAmount();
	}
	
	@Then ("^Verify the error message$")
	public void verifyError() throws Exception{
		verifyErrorMessageForBlankAmpount();
	}
	
	@Then ("^Converts '(.*)' from '(.*)' to '(.*)'$")
	public void currencyConvertAmount(String value, String convertFrom, String convertTo) throws Exception{
		convertAmount(value,convertFrom,convertTo);
	}
	
	@And ("^Verify and get the details of the '(.*)' exchange rates$")
	public void getDetailsOfExchangeRates(String conversion) throws Exception{
		verifyAndGetDetailsOfExchangeRates(conversion);
	}

}
