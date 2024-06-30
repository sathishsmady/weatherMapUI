package Base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

public class TestBase {

    protected static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(TestBase.class);
    static ExtentReports extentReports;
    static ExtentSparkReporter extentSparkReporter;
    static ExtentTest extentTest;

    static {

        extentSparkReporter  = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/extentReport.html");
        extentSparkReporter.config().setTheme(Theme.DARK);
        extentSparkReporter.config().setReportName("Weather API Regression");
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);

    }


    @BeforeMethod
    @Parameters("browser")
    public static void initializeBrowser(String browser, Method m) {

        logger.info( "STARTING TEST: "+ m.getName());
        extentTest = createTest(m);
        setupDriver(browser);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

    }

    public static ExtentTest createTest(Method m){
        return extentReports.createTest(m.getAnnotation(org.testng.annotations.Test.class).description());
    }



    @AfterMethod
    public static void tearDown(ITestResult result){

        int SUCCESS = 1;
        int FAILURE = 2;
        int SKIP = 3;

        if(result.getStatus()==SUCCESS){
            extentTest.log(Status.PASS, result.getMethod().getDescription());
            logger.info(result.getMethod().toString() + " is Passed");
            logger.info("==========================================================================================");
        }
        else if (result.getStatus()==FAILURE) {
            extentTest.log(Status.FAIL, result.getMethod().getDescription());
            extentTest.log(Status.FAIL,result.getThrowable());
            takeScreenShot(result.getTestName());
            logger.info(result.getMethod() + " is Failed");
            logger.error(result.getThrowable());
            logger.info("==========================================================================================");

        }
        else if (result.getStatus()==SKIP) {
            extentTest.log(Status.SKIP, result.getMethod().getDescription());
            extentTest.log(Status.SKIP, result.getThrowable());
            takeScreenShot(result.getTestName());
            logger.info(result.getMethod() + " is Skipped");
            logger.error(result.getThrowable());
            logger.info("==========================================================================================");

        }

        extentReports.flush();
        getDriver().quit();
        threadLocalDriver.remove();

    }

    //get thread-safe driver
    public static WebDriver getDriver(){
        return threadLocalDriver.get();
    }


    private static void setupDriver(String browser){

        WebDriver driver = null;
        if (browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            threadLocalDriver.set(driver);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            threadLocalDriver.set(driver);
        } else if (browser.equalsIgnoreCase("safari")) {
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
            threadLocalDriver.set(driver);
        } else {
            throw new RuntimeException("Invalid Browser");
        }

    }

    private static void takeScreenShot(String name){

        TakesScreenshot screenshot = (TakesScreenshot)getDriver();
        File source = screenshot.getScreenshotAs(OutputType.FILE);

        String path = System.getProperty("user.dir")+File.pathSeparator+"test-output"+name+".png";

        try {
            FileUtils.copyFile(source, new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Screenshot is captured");

        extentTest.addScreenCaptureFromPath(path);

    }



}
