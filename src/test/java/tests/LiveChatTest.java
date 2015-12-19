package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.BinaryOptionsTradingPage;

import static dataStore.ChatMatter.GENERAL_INFORMATION;

public class LiveChatTest extends BaseTest{

    @BeforeMethod
    public void openBinaryOptionsTradingPage(){
        driver.get(APPLICATION_URL);
        binaryOptionsTradingPage = new BinaryOptionsTradingPage(driver);
    }

    @Test(description = "Verify that the message 'Invalid e-mail' appears when non ISO standard email is being added",
            dataProvider = "invalid emails")
    public void testEmailValidationMessage(String invalidEmail) {
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

    @AfterMethod(alwaysRun = true)
    public void closeLiveChatWindow(){
        if (driver != null) {
            driver.close();
            liveChatPage.switchToAnotherWindow();
        }
    }

}
