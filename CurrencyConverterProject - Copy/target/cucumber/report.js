$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("CurrencyConverter.feature");
formatter.feature({
  "line": 1,
  "name": "This is a feature file to test Currency Converter application for Westpac NZ",
  "description": "",
  "id": "this-is-a-feature-file-to-test-currency-converter-application-for-westpac-nz",
  "keyword": "Feature"
});
formatter.before({
  "duration": 676395135,
  "status": "passed"
});
formatter.scenario({
  "line": 4,
  "name": "Currency_Converter Scenario_001_TestCase",
  "description": "",
  "id": "this-is-a-feature-file-to-test-currency-converter-application-for-westpac-nz;currency-converter-scenario-001-testcase",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 3,
      "name": "@Test_Westpac001"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "Customer access the Westpac application",
  "keyword": "Given "
});
formatter.step({
  "line": 7,
  "name": "Customer navigates to Currency Converter page",
  "keyword": "When "
});
formatter.step({
  "line": 8,
  "name": "Did not provide any amount to convert and clicks on Convert button",
  "keyword": "And "
});
formatter.step({
  "line": 9,
  "name": "Verify the error message",
  "keyword": "Then "
});
formatter.match({
  "location": "HomePageStepDefinitions.firstMethod()"
});
formatter.result({
  "duration": 39364866834,
  "status": "passed"
});
formatter.match({
  "location": "HomePageStepDefinitions.navigatesCurrencyConverter()"
});
formatter.result({
  "duration": 24052938266,
  "status": "passed"
});
formatter.match({
  "location": "CurrencyConverterStepDefinitions.convertBlankCurrencyAmount()"
});
formatter.result({
  "duration": 11204656232,
  "status": "passed"
});
formatter.match({
  "location": "CurrencyConverterStepDefinitions.verifyError()"
});
formatter.result({
  "duration": 344366603,
  "status": "passed"
});
formatter.after({
  "duration": 1948273207,
  "status": "passed"
});
formatter.before({
  "duration": 8484426,
  "status": "passed"
});
formatter.scenario({
  "line": 14,
  "name": "Currency_Converter Scenario_002_TestCase",
  "description": "",
  "id": "this-is-a-feature-file-to-test-currency-converter-application-for-westpac-nz;currency-converter-scenario-002-testcase",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 13,
      "name": "@Test_Westpac001"
    }
  ]
});
formatter.step({
  "line": 16,
  "name": "Customer access the Westpac application",
  "keyword": "Given "
});
formatter.step({
  "line": 17,
  "name": "Customer navigates to Currency Converter page",
  "keyword": "When "
});
formatter.step({
  "line": 18,
  "name": "Converts \u00271\u0027 from \u0027New Zealand Dollar\u0027 to \u0027United States Dollar\u0027",
  "keyword": "Then "
});
formatter.step({
  "line": 19,
  "name": "Verify and get the details of the \u0027New Zealand Dollar to United States Dollar\u0027 exchange rates",
  "keyword": "And "
});
formatter.step({
  "line": 20,
  "name": "Converts \u00271\u0027 from \u0027United States Dollar\u0027 to \u0027New Zealand Dollar\u0027",
  "keyword": "Then "
});
formatter.step({
  "line": 21,
  "name": "Verify and get the details of the \u0027United States Dollar to New Zealand Dollar\u0027 exchange rates",
  "keyword": "And "
});
formatter.step({
  "line": 22,
  "name": "Converts \u00271\u0027 from \u0027Pound Sterling\u0027 to \u0027New Zealand Dollar\u0027",
  "keyword": "Then "
});
formatter.step({
  "line": 23,
  "name": "Verify and get the details of the \u0027Pound Sterling to New Zealand Dollar\u0027 exchange rates",
  "keyword": "And "
});
formatter.step({
  "line": 24,
  "name": "Converts \u00271\u0027 from \u0027Swiss Franc\u0027 to \u0027Euro\u0027",
  "keyword": "Then "
});
formatter.step({
  "line": 25,
  "name": "Verify and get the details of the \u0027Swiss Franc to Euro\u0027 exchange rates",
  "keyword": "And "
});
formatter.match({
  "location": "HomePageStepDefinitions.firstMethod()"
});
formatter.result({
  "duration": 22230935495,
  "status": "passed"
});
formatter.match({
  "location": "HomePageStepDefinitions.navigatesCurrencyConverter()"
});
formatter.result({
  "duration": 22639966336,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "1",
      "offset": 10
    },
    {
      "val": "New Zealand Dollar",
      "offset": 19
    },
    {
      "val": "United States Dollar",
      "offset": 43
    }
  ],
  "location": "CurrencyConverterStepDefinitions.currencyConvertAmount(String,String,String)"
});
formatter.result({
  "duration": 6798470365,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "New Zealand Dollar to United States Dollar",
      "offset": 35
    }
  ],
  "location": "CurrencyConverterStepDefinitions.getDetailsOfExchangeRates(String)"
});
formatter.result({
  "duration": 3382879253,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "1",
      "offset": 10
    },
    {
      "val": "United States Dollar",
      "offset": 19
    },
    {
      "val": "New Zealand Dollar",
      "offset": 45
    }
  ],
  "location": "CurrencyConverterStepDefinitions.currencyConvertAmount(String,String,String)"
});
formatter.result({
  "duration": 6535285267,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "United States Dollar to New Zealand Dollar",
      "offset": 35
    }
  ],
  "location": "CurrencyConverterStepDefinitions.getDetailsOfExchangeRates(String)"
});
formatter.result({
  "duration": 3383414173,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "1",
      "offset": 10
    },
    {
      "val": "Pound Sterling",
      "offset": 19
    },
    {
      "val": "New Zealand Dollar",
      "offset": 39
    }
  ],
  "location": "CurrencyConverterStepDefinitions.currencyConvertAmount(String,String,String)"
});
formatter.result({
  "duration": 6517750295,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Pound Sterling to New Zealand Dollar",
      "offset": 35
    }
  ],
  "location": "CurrencyConverterStepDefinitions.getDetailsOfExchangeRates(String)"
});
formatter.result({
  "duration": 3403314210,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "1",
      "offset": 10
    },
    {
      "val": "Swiss Franc",
      "offset": 19
    },
    {
      "val": "Euro",
      "offset": 36
    }
  ],
  "location": "CurrencyConverterStepDefinitions.currencyConvertAmount(String,String,String)"
});
formatter.result({
  "duration": 6595442706,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Swiss Franc to Euro",
      "offset": 35
    }
  ],
  "location": "CurrencyConverterStepDefinitions.getDetailsOfExchangeRates(String)"
});
formatter.result({
  "duration": 3366139067,
  "status": "passed"
});
formatter.after({
  "duration": 1136453635,
  "status": "passed"
});
});