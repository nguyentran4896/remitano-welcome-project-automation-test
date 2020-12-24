package pl.webtest.test.homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.comparesEqualTo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Test;

import pl.webtest.test.SeleniumTestBase;

public class VoteVideoTest extends SeleniumTestBase {
    private static final String HOME_ADDRESS = "https://theweatherforecast.today";
    private static final String EMAIL_TO_SIGN_IN = "test_login_email@gmail.com";
    private static final String PASSWORD_TO_SIGN_IN = "DYvgcCGVLRYLck4E";

    @Test
    public void voteUpVideoTest() throws InterruptedException {
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

        Thread.sleep(500);

        WebElement likeCount = getWebDriver().findElement(By.cssSelector("#root > div.homepage.container > div > div:nth-child(1) > div.animate__animated.animate__fadeInRight.card-body > div > div > span.like-count"));
        int likeCountNumber = Integer.parseInt(likeCount.getText());

        // like button
        WebElement likeBtn = getWebDriver().findElement(By.cssSelector("#root > div.homepage.container > div > div:nth-child(1) > div.animate__animated.animate__fadeInRight.card-body > div > a:nth-child(1)"));
        likeBtn.click();

        Thread.sleep(1000);

        assertThat(Integer.parseInt(likeCount.getText()), comparesEqualTo(likeCountNumber + 1));

        results.add("Expected: " + (likeCountNumber + 1));
        results.add("Actual: " + likeCount.getText());

        Reporter.log("Results: " + StringUtils.join(results, ", "));
    }

    @Test(dependsOnMethods={"voteUpVideoTest"})
    public void unVoteVideoTest() throws InterruptedException {
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

        Thread.sleep(500);

        WebElement likeCount = getWebDriver().findElement(By.cssSelector("#root > div.homepage.container > div > div:nth-child(1) > div.animate__animated.animate__fadeInRight.card-body > div > div > span.like-count"));
        int likeCountNumber = Integer.parseInt(likeCount.getText());

        // un-like button
        WebElement unLikeBtn = getWebDriver().findElement(By.cssSelector("#root > div.homepage.container > div > div:nth-child(1) > div.animate__animated.animate__fadeInRight.card-body > div > a"));
        unLikeBtn.click();

        Thread.sleep(1000);

        // get like count again
        likeCount = getWebDriver().findElement(By.cssSelector("#root > div.homepage.container > div > div:nth-child(1) > div.animate__animated.animate__fadeInRight.card-body > div > div > span.like-count"));

        assertThat(Integer.parseInt(likeCount.getText()), comparesEqualTo(likeCountNumber - 1));

        results.add("Expected: " + (likeCountNumber - 1));
        results.add("Actual: " + likeCount.getText());

        Reporter.log("Results: " + StringUtils.join(results, ", "));
    }

    @Test(dependsOnMethods={"unVoteVideoTest"})
    public void voteDownVideoTest() throws InterruptedException {
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

        Thread.sleep(500);

        WebElement dislikeCount = getWebDriver().findElement(By.cssSelector("#root > div.homepage.container > div > div:nth-child(1) > div.animate__animated.animate__fadeInRight.card-body > div > div > span.dislike-count"));
        int dislikeCountNumber = Integer.parseInt(dislikeCount.getText());

        // like button
        WebElement dislikeBtn = getWebDriver().findElement(By.cssSelector("#root > div.homepage.container > div > div:nth-child(1) > div.animate__animated.animate__fadeInRight.card-body > div > a:nth-child(2)"));
        dislikeBtn.click();

        Thread.sleep(1000);

        assertThat(Integer.parseInt(dislikeCount.getText()), comparesEqualTo(dislikeCountNumber + 1));

        results.add("Expected: " + (dislikeCountNumber + 1));
        results.add("Actual: " + dislikeCount.getText());

        Reporter.log("Results: " + StringUtils.join(results, ", "));
    }
}