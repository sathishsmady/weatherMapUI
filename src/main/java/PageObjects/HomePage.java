package PageObjects;

import Util.SeleniumWaits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;


public class HomePage {
    public static Logger logger = LogManager.getLogger(HomePage.class);


    WebDriver driver;
    @FindBy(xpath = "//input[@placeholder= 'Search city']")
    WebElement searchCity;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement searchButton;

    @FindBy(xpath = "//ul[@class='search-dropdown-menu']")
    WebElement searchDropdown;

    @FindBy(xpath = "//div[@class='current-container mobile-padding']")
    WebElement contentBox;

    @FindBy(xpath = "//div[@class='current-temp']/span")
    WebElement temp;

    @FindBy(xpath = "//li/span[@class='symbol' and text()='Humidity:']/parent::li")
    WebElement humidity;

    @FindBy(xpath = "//li/span[@class='symbol' and text()='Visibility:']/parent::li")
    WebElement visibility;

    @FindBy(xpath = "//li/span[@class='symbol' and text()='UV:']/parent::li")
    WebElement uv;

    @FindBy(xpath = "//div[@class='wind-line']")
    WebElement wind;

    @FindBy(xpath = "//a[@class='map-info-block']")
    WebElement map;

    @FindBy(xpath = "//div[@class='current-container mobile-padding']/descendant::span[contains(text(),'No results')]")
    WebElement alert;

    @FindBy(xpath = "//li[@id='desktop-menu']//input[@name='q']")
    WebElement weatherInYourCity;


    public HomePage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void searchCitySendKeys(String s) {

        SeleniumWaits.isVisible(searchCity);
        SeleniumWaits.ExpWaitIsClickable(searchCity);
        searchCity.click();
        searchCity.sendKeys(s);
    }

    public void searchButton() {
        SeleniumWaits.isVisible(searchButton);
        searchButton.click();
    }

    public void searchDropdown(String cityName, String country) {
        SeleniumWaits.isVisible(searchDropdown);

        boolean isClicked = false;

        List<WebElement> webElements = driver.findElements(By.xpath("//ul[@class='search-dropdown-menu']/li"));
        try {
            for (int i = 1; i <= webElements.size(); i++) {

                WebElement webElement = driver.findElement(By.xpath("//ul[@class='search-dropdown-menu']/li[" + i + "]/span[1]"));
                if (webElement.getText().equals(cityName + ", " + country)) {
                    webElement.click();
                    logger.info("Clicked city : " + cityName +", "+ country);
                    isClicked = true;
                    break;
                }

            }
        }
        catch (ElementClickInterceptedException e){
            logger.info(e.getMessage());
            throw new ElementClickInterceptedException("Element not Clickable");
        }
        catch (Exception e) {
            logger.info(e.getMessage());
            throw new RuntimeException("Invalid City Name");
        }


        Assert.assertTrue(isClicked,"Element not clicked");
    }


    public void validateWeatherParams(String cityName, String country){

        SeleniumWaits.isVisible(contentBox);
        String city = cityName+", "+country;
        WebElement cityNames = driver.findElement(By.xpath("//h2[text()='"+city+"']"));
        SeleniumWaits.isVisible(cityNames);
        Assert.assertEquals(city,cityNames.getText());

        String temperature = temp.getText();

        logger.info("Today temperature : " + temperature );

        String[] temps = temperature.split("°");

        int t = Integer.parseInt(temps[0]);
        Assert.assertTrue(t > Integer.MIN_VALUE && t < Integer.MAX_VALUE );
        Assert.assertTrue(temps[1].equalsIgnoreCase("C"));


        Assert.assertNotNull(humidity.getText());
        logger.info(humidity.getText());
        Assert.assertNotNull(visibility.getText());
        logger.info(visibility.getText());
        Assert.assertNotNull(wind.getText());
        logger.info("wind : "+wind.getText());


    }

    public void clickMap(){

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,150)", "");

        SeleniumWaits.isVisible(map);
        map.click();
    }

    public void validateErrorMsg(String s){

        SeleniumWaits.isVisible(alert);

        Assert.assertTrue(alert.getText().equalsIgnoreCase("No Results for " + s));

    }

    public void clickWeatherInYourCity(String s){
        SeleniumWaits.isVisible(weatherInYourCity);
        weatherInYourCity.click();
        weatherInYourCity.sendKeys(s);

        Actions a = new Actions(driver);
        a.sendKeys(Keys.ENTER);
        a.build().perform();

    }

    public void searchDropdownNotVisible() {
        SeleniumWaits.isNotVisible(searchDropdown);
    }

    public void searchDropdownIsVisible() {
        SeleniumWaits.isVisible(searchDropdown);
    }


}
