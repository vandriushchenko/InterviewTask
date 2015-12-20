package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.BinaryOptionsTradingPage;

import java.util.concurrent.TimeUnit;

import static dataStore.ChatMatter.GENERAL_INFORMATION;

public class LiveChatTest extends BaseTest{

    @BeforeMethod(alwaysRun = true)
    public void openBinaryOptionsTradingPage(){
        initBrowser();
        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
        driver.get(APPLICATION_URL);
        binaryOptionsTradingPage = new BinaryOptionsTradingPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void closeApplication(){
        driver.manage().deleteAllCookies();
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "Verify that the message 'Invalid e-mail' appears when non ISO standard email is being added",
            dataProvider = "invalid emails")
    public void testEmailValidationMessage(String invalidEmail){
        String name = "John";
        openLiveChat();
        liveChatPage.typeName(name);
        liveChatPage.selectChatMatter(GENERAL_INFORMATION);
        liveChatPage.typeEmail(invalidEmail);
        liveChatPage.clickStartTheChat();
        verifyInvalidEmailMessageAppears();
    }

    @DataProvider(name = "invalid emails")
    public Object[][] getInvalidEmails(){
        return new String[][]{
                {"abc.example.com"},
                {"a@b@c@example.com"},
                {"a\"b(c)d,e:f;g<h>i[j\\k]l@example.com"},
                {"just\"not\"right@example.com"},
                {"john..doe@example.com"},
                {"john.doe@example..com"},
                {"abc"}
        };
    }
}
