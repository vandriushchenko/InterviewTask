package tests;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.BinaryOptionsTradingPage;
import pages.LiveChatPage;

import static dataStore.ValidationMessages.INVALID_EMAIL;

public abstract class BaseTest {
    protected WebDriver driver;
    protected BinaryOptionsTradingPage binaryOptionsTradingPage;
    protected LiveChatPage liveChatPage;
    public static final String APPLICATION_URL = "https://trade.24option.com/24option/?lang=en#Trade";
    public static final long TIMEOUT = 30;
    private static final String DRIVERS_PATH = "src/test/resources/drivers";
    private String browser;

    @BeforeSuite
    @Parameters("browser")
    public void setBrowser(@Optional(BrowserType.FIREFOX) String browser){
        this.browser = browser;
    }

    public void openLiveChat(){
        liveChatPage = binaryOptionsTradingPage.openLiveChat();
        binaryOptionsTradingPage.switchToAnotherWindow();
    }

    public void verifyInvalidEmailMessageAppears(){
        try {
            Assert.assertEquals(liveChatPage.getValidationMessageText(), INVALID_EMAIL, "Wrong validation message has been appeared");
        }
        catch (NoSuchElementException e){
            Assert.fail("Validation message hasn't been appeared");
        }
    }

    protected void initBrowser(){
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
