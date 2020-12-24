package pl.webtest.test.homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.isEmptyString;

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

public class ShareVideoTest extends SeleniumTestBase {
    private static final Logger logger = LoggerFactory.getLogger(ShareVideoTest.class);
    private static final String HOME_ADDRESS = "https://theweatherforecast.today";
    private static final String EMAIL_TO_SIGN_IN = "test_login_email@gmail.com";
    private static final String PASSWORD_TO_SIGN_IN = "DYvgcCGVLRYLck4E";
    private static final String VIDEO_URL = "https://www.youtube.com/watch?v=vlxmiFF85yU";

    @Test
    public void shareVideoTest() throws InterruptedException {
        // Go to the home page
        getWebDriver().get(HOME_ADDRESS);

        List<WebElement> loginBtn = getWebDriver()
                .findElements(By.cssSelector("#root > div.header > nav > div > form > button.btn.btn-outline-success"));
        if (loginBtn.size() < 1) {
            WebElement logOutBtn = getWebDriver()
                    .findElement(By.cssSelector("#root > div.header > nav > div > button"));
            logOutBtn.click();
        }

        loginBtn = getWebDriver()
                .findElements(By.cssSelector("#root > div.header > nav > div > form > button.btn.btn-outline-success"));

        WebElement emailInput = getWebDriver().findElement(By.cssSelector("#root > div.header > nav > div > form > input:nth-child(1)"));
        WebElement passInput = getWebDriver().findElement(By.cssSelector("#root > div.header > nav > div > form > input:nth-child(2)"));

        emailInput.sendKeys(EMAIL_TO_SIGN_IN);
        passInput.sendKeys(PASSWORD_TO_SIGN_IN);
        loginBtn.get(0).click();
        
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

        WebElement shareBtn = getWebDriver().findElement(By.cssSelector("#root > div.header > nav > div > a"));
        shareBtn.click();

        Thread.sleep(500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div.share.row > div > div > h5")));

        getWebDriver().findElement(By.cssSelector("#inlineFormInputGroupVideoUrl"))
        .sendKeys(VIDEO_URL);

        // submit
        getWebDriver().findElement(By.cssSelector("#root > div.share.row > div > div > div > form > button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div.share.row > div:nth-child(2) > div > div.animate__animated.animate__fadeInRight.card-body > a")));
        
        WebElement videoTitle = getWebDriver().findElement(By.cssSelector("#root > div.share.row > div:nth-child(2) > div > div.animate__animated.animate__fadeInRight.card-body > a"));


        Thread.sleep(2000);

        assertThat(videoTitle.getText(), not(isEmptyString()));

        results.add("Expected: not null or empty");
        results.add("Actual: " + videoTitle.getText());

        Reporter.log("Results: " + StringUtils.join(results, ", "));
    }
}