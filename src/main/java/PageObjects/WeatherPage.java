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


public class WeatherPage {
    public static Logger logger = LogManager.getLogger(WeatherPage.class);


    WebDriver driver;
    @FindBy(xpath = "//input[@placeholder= 'Search city']")
    WebElement searchCity;

    @FindBy(xpath = "//div[@id='forecast_list_ul']/table/descendant::a[contains(@href,'city')]")
    List<WebElement> cityLink;

    @FindBy(xpath = "//div[@class='current-container mobile-padding']")
    WebElement contentBox;

    @FindBy(xpath = "//div[@class='current-temp']/span")
    WebElement temp;

    @FindBy(xpath = "//li/span[@class='symbol' and text()='Humidity:']/parent::li")
    WebElement humidity;

    @FindBy(xpath = "//li/span[@class='symbol' and text()='Visibility:']/parent::li")
    WebElement visibility;

    @FindBy(xpath = "//div[@class='wind-line']")
    WebElement wind;


    public WeatherPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void searchCitySendKeys(String s) {

        SeleniumWaits.isVisible(searchCity);
        SeleniumWaits.ExpWaitIsClickable(searchCity);
        searchCity.click();
        searchCity.sendKeys(s);
    }

    public void cityLinksValidate(String cityName, String country) {

        boolean isClicked = false;

        try {
        for(WebElement city : cityLink) {
            if (city.getText().contains(cityName) && city.getText().contains(country)) {
                SeleniumWaits.ExpWaitIsClickable(city);
                city.click();
                isClicked = true;
                logger.info(cityName + " : City is clicked");
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




}
