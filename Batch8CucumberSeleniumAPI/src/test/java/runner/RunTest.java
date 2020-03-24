package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(

		features="classpath:features/api",
		glue="stepdefs.api",
		plugin = {"pretty",
				"html:target/html/",
				"json:target/json/file.json",
		},
		dryRun=true

		)
public class RunTest {

	//"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
}
