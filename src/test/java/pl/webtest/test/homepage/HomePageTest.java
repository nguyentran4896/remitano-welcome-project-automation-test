package pl.webtest.test.homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import org.testng.annotations.Test;

import pl.webtest.test.SeleniumTestBase;

public class HomePageTest extends SeleniumTestBase {
	private static final Logger logger = LoggerFactory.getLogger(HomePageTest.class);
	
	// TestNG allows you to use @DataProvider annotation, which allows you to parameterize your tests however you like and
	// improve your test coverage dramatically with less lines of code in your tests.
	//
	// Each test class (and method) can belong to multiple groups, and with TestNG it's very easy to run only selected group with
	// @Test (group) annotation. You can easily set in CI which group of tests you want to run - smoke, GUI, API, etc.
	//
	// Test dependencies. It's not desirable practice with unit tests, but when it comes to integration tests,
	// pretty often you cannot avoid it. This is where TestNG becomes very helpful and easy to use.
	//
	// You can use @Test annotation with ThreadPoolSize, and invocationCount parameters to run parallel tests.
	//
	// All these settings and tweaks can be specified in textng.xml file, which  is used by TestNG to run tests both
	// on your local dev machine and on CI Server. It will declare which tests should be run or skipped and contains
	// information about groups, packages, and classes. This file is located in the root project folder.
	
    @Test
    public void getHomePageTest() {

        String EMAIL_TO_SIGN_UP = "nguyen123@gmail.com";
        String PASSWORD_TO_SIGN_UP = "nguyen123@gmail.com";
        
        // Go to the Google Suggest home page
    	getWebDriver().get("https://theweatherforecast.today");
        
        // Enter the query string
        WebElement emailInput = getWebDriver().findElement(By.cssSelector("#root > div.header > nav > div > form > input:nth-child(1)"));
        emailInput.sendKeys(EMAIL_TO_SIGN_UP);

        WebElement passInput = getWebDriver().findElement(By.cssSelector("#root > div.header > nav > div > form > input:nth-child(2)"));
        passInput.sendKeys(PASSWORD_TO_SIGN_UP);

        WebElement signUpBtn = getWebDriver().findElement(By.cssSelector("#root > div.header > nav > div > form > button.btn.btn-outline-primary"));
        signUpBtn.click();
        
        // Sleep until the div we want is visible or 5 seconds is over
        // We need to wait as div with search results is loaded dynamically on every key input
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div.header > nav > div > form > span")));

        WebElement spanWelcome = getWebDriver().findElement(By.cssSelector("#root > div.header > nav > div > form > span"));
        
        /* The above is the same as below but uglier
        long end = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < end) {
            WebElement resultsDiv = driver.findElement(By.className("gssb_e"));

            // If results have been returned, the results are displayed in a drop down.
            if (resultsDiv.isDisplayed()) {
              break;
            }
        }
        */

        // And now list the suggestions
        // List<WebElement> allSuggestions = getWebDriver().findElements(By.className("sbsb_b"));
        
        assertThat(spanWelcome.getText(), containsString(EMAIL_TO_SIGN_UP));


        List<String> results = new ArrayList<String>();
        results.add("Expected contain: " + EMAIL_TO_SIGN_UP);
        results.add("Actual: " + spanWelcome.getText());

        Reporter.log("Sign up successful");
        
        // // Report results
        
        // for (WebElement suggestion : allSuggestions) {
        // 	results.add(suggestion.getText());
        // }
        Reporter.log("Results: " + StringUtils.join(results, ", "));
    }
}