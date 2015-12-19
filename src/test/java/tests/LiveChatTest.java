package tests;

import dataStore.ChatMatter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LiveChatTest extends BaseTest{

    @Test(description = "Verify that the message 'Invalid e-mail' appears when non ISO standard email is being added",
            dataProvider = "invalid emails")
    public void testEmailValidationMessage(String invalidEmail) {
        String name = "John";
        openBinaryOptionsTradingPage();
        openLiveChat();
        liveChatPage.typeName(name);
        liveChatPage.selectChatMatter(ChatMatter.GENERAL_INFORMATION);
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
