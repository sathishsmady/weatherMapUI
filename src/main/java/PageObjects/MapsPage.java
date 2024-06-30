package PageObjects;

import Util.SeleniumWaits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


public class MapsPage {
    public static Logger logger = LogManager.getLogger(MapsPage.class);


    WebDriver driver;




    public MapsPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //div[@class='city-data']/descendant::span[text()='Delhi']

    //*[@class='row city-full-info']/table/thead/tr/th[text()='Delhi']

    //*[@class='row city-full-info']/table/thead/tr/th[text()='Delhi']/ancestor::table/tbody/tr/td[text()='country']/following-sibling::td


    public void clickCityInMap(String city){

        WebElement cityClick = driver.findElement(By.xpath("//div[@class='city-data']/descendant::span[text()='"+city+"']"));
        SeleniumWaits.ExpWaitIsClickable(cityClick);
        cityClick.click();
    }


    public void validateCity(String city){

        WebElement header = driver.findElement(By.xpath("//*[@class='row city-full-info']/table/thead/tr/th[text()='"+city+"']"));
        SeleniumWaits.ExpWaitIsClickable(header);

        Assert.assertTrue(header.isDisplayed());
        Assert.assertEquals(header.getText(),city);
    }

    public void validateCityData(String city, String country){

        WebElement cityData = driver.findElement(By.xpath("//*[@class='row city-full-info']/table/thead/tr/th[text()='"+city+"']/ancestor::table/tbody/tr/td[text()='country']/following-sibling::td"));
        SeleniumWaits.ExpWaitIsClickable(cityData);
        Assert.assertTrue(cityData.isDisplayed());
        Assert.assertEquals(cityData.getText(),country);
        logger.info("City Data is validated  ::  " + cityData.getText());

        WebElement tempData = driver.findElement(By.xpath("//*[@class='row city-full-info']/table/thead/tr/th[text()='"+city+"']/ancestor::table/tbody/tr/td[text()='temp']/following-sibling::td"));
        SeleniumWaits.ExpWaitIsClickable(tempData);
        Assert.assertTrue(tempData.isDisplayed());
        Assert.assertNotNull(tempData.getText());
        logger.info("Temperature Data is validated  ::  " + tempData.getText());

        WebElement humidityData = driver.findElement(By.xpath("//*[@class='row city-full-info']/table/thead/tr/th[text()='"+city+"']/ancestor::table/tbody/tr/td[text()='humidity']/following-sibling::td"));
        SeleniumWaits.ExpWaitIsClickable(humidityData);
        Assert.assertTrue(humidityData.isDisplayed());
        Assert.assertNotNull(humidityData.getText());
        logger.info("Humidity Data is validated  ::  " + humidityData.getText());

        WebElement clouds = driver.findElement(By.xpath("//*[@class='row city-full-info']/table/thead/tr/th[text()='"+city+"']/ancestor::table/tbody/tr/td[text()='clouds']/following-sibling::td"));
        SeleniumWaits.ExpWaitIsClickable(clouds);
        Assert.assertTrue(clouds.isDisplayed());
        Assert.assertNotNull(clouds.getText());
        logger.info("Clouds Data is validated  ::  " + clouds.getText());

        WebElement pressure = driver.findElement(By.xpath("//*[@class='row city-full-info']/table/thead/tr/th[text()='"+city+"']/ancestor::table/tbody/tr/td[text()='pressure']/following-sibling::td"));
        SeleniumWaits.ExpWaitIsClickable(pressure);
        Assert.assertTrue(pressure.isDisplayed());
        Assert.assertNotNull(pressure.getText());
        logger.info("Pressure Data is validated  ::  " + pressure.getText());

        WebElement windData = driver.findElement(By.xpath("//*[@class='row city-full-info']/table/thead/tr/th[text()='"+city+"']/ancestor::table/tbody/tr/td[text()='wind direction']/following-sibling::td"));
        SeleniumWaits.ExpWaitIsClickable(windData);
        Assert.assertTrue(windData.isDisplayed());
        Assert.assertNotNull(windData.getText());
        logger.info("Wind Data is validated  ::  " + windData.getText());

        WebElement windSpeed = driver.findElement(By.xpath("//*[@class='row city-full-info']/table/thead/tr/th[text()='"+city+"']/ancestor::table/tbody/tr/td[text()='wind speed']/following-sibling::td"));
        SeleniumWaits.ExpWaitIsClickable(windSpeed);
        Assert.assertTrue(windSpeed.isDisplayed());
        Assert.assertNotNull(windSpeed.getText());
        logger.info("Wind Speed Data is validated  ::  " + windSpeed.getText());

    }




}
