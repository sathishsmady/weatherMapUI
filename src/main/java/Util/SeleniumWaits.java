package Util;

import Base.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumWaits extends TestBase {

    public static void isVisible(WebElement element) {

            WebDriverWait expWait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
            expWait.until(ExpectedConditions.visibilityOf(element));

    }

    public static void isNotVisible(WebElement element) {

        WebDriverWait expWait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        expWait.until(ExpectedConditions.invisibilityOf(element));

    }

    public static void ExpWaitIsClickable(WebElement element)
    {
        WebDriverWait expWait=new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        expWait.until(ExpectedConditions.elementToBeClickable(element));

    }
    public static void ExpWaitForWebElement(WebElement element)
    {
        WebDriverWait expWait=new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        expWait.until(ExpectedConditions.elementToBeClickable(element));

    }

}
