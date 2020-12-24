package pl.webtest.test.homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static final String HOME_ADDRESS = "https://theweatherforecast.today"; 
    private static final String EMAIL_TO_SIGN_IN = "test_login_email@gmail.com"; 
    private static final String PASSWORD_TO_SIGN_IN = "DYvgcCGVLRYLck4E";
    @Test
    public void signUpTest() {

        String EMAIL_TO_SIGN_UP = this.generateRandomEmail();
        String PASSWORD_TO_SIGN_UP = this.randomString();

        // Go to the home page
    	getWebDriver().get(HOME_ADDRESS);
        
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
        
        assertThat(spanWelcome.getText(), containsString(EMAIL_TO_SIGN_UP));

        List<String> results = new ArrayList<String>();
        results.add("Expected contain: " + EMAIL_TO_SIGN_IN);
        results.add("Actual: " + spanWelcome.getText());

        Reporter.log("Signed up SUCCESSFULLY");
        
        Reporter.log("Results: " + StringUtils.join(results, ", "));
    }

    @Test
    public void logOutTest() {
        List<String> results = new ArrayList<String>();

        // Go to the home page
    	getWebDriver().get(HOME_ADDRESS);
        
        WebElement spanWelcome = getWebDriver().findElement(By.cssSelector("#root > div.header > nav > div > form > span"));
        
        assertThat(spanWelcome.getText(), containsString(EMAIL_TO_SIGN_IN));
        results.add("User logged in");

        WebElement logOutBtn = getWebDriver().findElement(By.cssSelector("#root > div.header > nav > div > button"));
        logOutBtn.click();
        
        // Sleep until the div we want is visible or 5 seconds is over
        // We need to wait as div with search results is loaded dynamically on every key input
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div.header > nav > div > form > button.btn.btn-outline-primary")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div.header > nav > div > form > button.btn.btn-outline-success")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div.header > nav > div > form > input:nth-child(1)")));

        
        results.add("Expected: Show Input, Login, Sign Up button");
        results.add("Actual: Show Input, Login, Sign Up button");

        Reporter.log("Logged in SUCCESSFULLY");
        
        Reporter.log("Results: " + StringUtils.join(results, ", "));
    }

    @Test
    public void logInTest() {
        // Go to the home page
        getWebDriver().get(HOME_ADDRESS);

        // Enter the query string
        WebElement emailInput = getWebDriver().findElement(By.cssSelector("#root > div.header > nav > div > form > input:nth-child(1)"));
        WebElement passInput = getWebDriver().findElement(By.cssSelector("#root > div.header > nav > div > form > input:nth-child(2)"));
        WebElement loginBtn = getWebDriver().findElement(By.cssSelector("#root > div.header > nav > div > form > button.btn.btn-outline-success"));

        if (!loginBtn.isDisplayed()) {
            logOutTest();
        }
        
        emailInput.sendKeys(EMAIL_TO_SIGN_IN);
        passInput.sendKeys(PASSWORD_TO_SIGN_IN);
        loginBtn.click();
        
        // Sleep until the div we want is visible or 5 seconds is over
        // We need to wait as div with search results is loaded dynamically on every key input
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div.header > nav > div > form > span")));

        WebElement spanWelcome = getWebDriver().findElement(By.cssSelector("#root > div.header > nav > div > form > span"));
        
        assertThat(spanWelcome.getText(), containsString(EMAIL_TO_SIGN_IN));

        List<String> results = new ArrayList<String>();
        results.add("Expected contain: " + EMAIL_TO_SIGN_IN);
        results.add("Actual: " + spanWelcome.getText());

        Reporter.log("Logged in SUCCESSFULLY");
        
        Reporter.log("Results: " + StringUtils.join(results, ", "));
    }

    private String generateRandomEmail() {
        return "test-".concat(this.randomString()).concat("@gmail.com");
    }

    private String randomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }
}