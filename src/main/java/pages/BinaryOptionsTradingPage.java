package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BinaryOptionsTradingPage extends BasePage{

    @FindBy(id = "chat")
    private WebElement liveChat;

    public BinaryOptionsTradingPage(WebDriver driver) {
        super(driver);
    }

    public LiveChatPage openLiveChat(){
        liveChat.click();
        return new LiveChatPage(driver);
    }
}
