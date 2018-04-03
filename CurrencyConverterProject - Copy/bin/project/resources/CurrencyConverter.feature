Feature: This is a feature file to test Currency Converter application for Westpac NZ.

@Test_Westpac001
Scenario: Currency_Converter Scenario_001_TestCase

Given Customer access the Westpac application
When Customer navigates to Currency Converter page
And Did not provide any amount to convert and clicks on Convert button
Then Verify the error message



@Test_Westpac001
Scenario: Currency_Converter Scenario_002_TestCase

Given Customer access the Westpac application
When Customer navigates to Currency Converter page
Then Converts '1' from 'New Zealand Dollar' to 'United States Dollar'
And Verify and get the details of the 'New Zealand Dollar to United States Dollar' exchange rates
Then Converts '1' from 'United States Dollar' to 'New Zealand Dollar'
And Verify and get the details of the 'United States Dollar to New Zealand Dollar' exchange rates
Then Converts '1' from 'Pound Sterling' to 'New Zealand Dollar'
And Verify and get the details of the 'Pound Sterling to New Zealand Dollar' exchange rates
Then Converts '1' from 'Swiss Franc' to 'Euro'
And Verify and get the details of the 'Swiss Franc to Euro' exchange rates

