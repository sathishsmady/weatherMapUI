import Base.TestBase;
import Constants.Constants;
import DataProviders.TestDataProvider;
import PageObjects.HomePage;
import PageObjects.MapsPage;
import PageObjects.WeatherPage;
import Util.SeleniumWaits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

public class WeatherUITests extends TestBase {

    private static final Logger logger = LogManager.getLogger(WeatherUITests.class);


    @Test(description = "Validate Whether Search City Functionality is working fine",
            dataProviderClass = TestDataProvider.class,
            dataProvider = "weatherUITests")
    public void validateSearchCity(String caseId, String description, String cityName, String country) {

        getDriver().get(Constants.URL);
        getDriver().manage().window().maximize();
        logger.info(caseId + "  :: " + description);

        HomePage homePage = new HomePage(getDriver());
        homePage.searchCitySendKeys(cityName);
        homePage.searchButton();
        homePage.searchDropdown(cityName, country);
        homePage.validateWeatherParams(cityName, country);
    }

    @Test(description = "Navigate Maps and do the validations ")
    public void validateMapsFunctionality() {

        getDriver().get(Constants.URL);
        getDriver().manage().window().maximize();
        HomePage homePage = new HomePage(getDriver());
        homePage.searchCitySendKeys("Delhi");
        homePage.searchButton();
        homePage.searchDropdown("Delhi", "IN");

        String parent = getDriver().getWindowHandle();
        homePage.clickMap();

        // window handling for switch to the next window
        Set<String> s = getDriver().getWindowHandles();
        for (String child_window : s) {
            if (!parent.equals(child_window)) {
                getDriver().switchTo().window(child_window);
                logger.info("switched to child window :: " + getDriver().switchTo().window(child_window).getTitle());
            }
        }

        MapsPage mapsPage = new MapsPage(getDriver());
        mapsPage.clickCityInMap("Delhi");
        mapsPage.validateCity("Delhi");
        mapsPage.validateCityData("Delhi", "IN");

    }


    @Test(description = "Validate when we pass invalid city")
    public void validateWithInvalidCity() {

        getDriver().get(Constants.URL);
        getDriver().manage().window().maximize();

        HomePage homePage = new HomePage(getDriver());
        homePage.searchCitySendKeys("Sathish");
        homePage.searchButton();
        homePage.validateErrorMsg("Sathish");
        /**
         * Intentionally failed to check screenShot functionality
         */
        Assert.fail();
    }

    @Test(description = "Validate Weather in your city functionalities")
    public void validateWeatherInYourCity() {

        getDriver().get(Constants.URL);
        getDriver().manage().window().maximize();

        HomePage homePage = new HomePage(getDriver());
        String parent = getDriver().getWindowHandle();
        homePage.clickWeatherInYourCity("Delhi");

        Set<String> s = getDriver().getWindowHandles();
        for (String child_window : s) {
            if (!parent.equals(child_window)) {
                getDriver().switchTo().window(child_window);
                logger.info("switched to child window :: " + getDriver().switchTo().window(child_window).getTitle());
            }
        }

        WeatherPage weatherPage = new WeatherPage(getDriver());
        weatherPage.cityLinksValidate("Delhi", "IN");
        weatherPage.validateWeatherParams("Delhi", "IN");

    }

    @Test(description = "Validate when we send only 2 letters in the search box")
    public void validateCitySuggestionWith2Letter() {

        getDriver().get(Constants.URL);
        getDriver().manage().window().maximize();

        HomePage homePage = new HomePage(getDriver());
        homePage.searchCitySendKeys("ca");
        homePage.searchButton();
        /**
         * Validating the search dropdown shouldn't come when we type only 2 characters.
         */
        homePage.searchDropdownNotVisible();

    }

    @Test(description = "Validate when we send 3 letters in the search box")
    public void validateCitySuggestionWith3Letter() {

        getDriver().get(Constants.URL);
        getDriver().manage().window().maximize();

        HomePage homePage = new HomePage(getDriver());
        homePage.searchCitySendKeys("can");
        homePage.searchButton();
        /**
         * Validating the search dropdown should come when we type 3 characters.
         */
        homePage.searchDropdownIsVisible();

    }

}
