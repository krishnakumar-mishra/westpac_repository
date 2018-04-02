package project;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions( features = "src/project/resources/", 
		monochrome = true,
				 format = {"pretty", "html:target/cucumber", "json:target/cucumber.json"},
				 tags = {"@Test_Westpac001", "~@Ignore"}
				 
				 				
				)

		

public class RunTest {

}
