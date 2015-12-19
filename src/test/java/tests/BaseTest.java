package tests;

import dataStore.ValidationMessages;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.BinaryOptionsTradingPage;
import pages.LiveChatPage;

import java.util.concurrent.TimeUnit;

public abstract class BaseTest {
    protected WebDriver driver;
    protected BinaryOptionsTradingPage binaryOptionsTradingPage;
    protected LiveChatPage liveChatPage;
    private static final String APPLICATION_URL = "https://trade.24option.com/24option/?lang=en#Trade";
    private static final long TIMEOUT = 5;
    private static final String DRIVERS_PATH = "src/test/resources/drivers";

    @BeforeSuite
    @Parameters("browser")
    public void setUp(@Optional(BrowserType.FIREFOX) String browser){
        initBrowser(browser);
        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
        driver.get(APPLICATION_URL);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }

    public void openBinaryOptionsTradingPage(){
        binaryOptionsTradingPage = new BinaryOptionsTradingPage(driver);
    }

    public void openLiveChat(){
        liveChatPage = binaryOptionsTradingPage.openLiveChat();
        binaryOptionsTradingPage.switchToAnotherWindow();
    }

    public void verifyInvalidEmailMessageAppears(){
        try {
            Assert.assertEquals(liveChatPage.getValidationMessageText(), ValidationMessages.INVALID_EMAIL);
        }
        catch (NoSuchElementException e){
            Assert.fail("Validation message hasn't been appeared");
        }
    }

    private void initBrowser(String browser){
        switch (browser.toLowerCase()){
            case BrowserType.FIREFOX :
                driver = new FirefoxDriver();
                break;
            case BrowserType.CHROME :
                System.setProperty("webdriver.chrome.driver", DRIVERS_PATH+"/chrome/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case BrowserType.IE :
                System.setProperty("webdriver.ie.driver", DRIVERS_PATH+"/ie/IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser type " + browser);
        }
    }
}
