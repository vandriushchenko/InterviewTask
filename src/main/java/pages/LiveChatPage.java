package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class LiveChatPage extends BasePage{

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "skill")
    private WebElement chatMatter;

    @FindBy(xpath = "//input[@value='Start the chat']")
    private WebElement startTheChatButton;

    @FindBy(id = "notification-content")
    private WebElement validationMessage;

    public LiveChatPage(WebDriver driver) {
        super(driver);
    }

    public void typeName(String name){
        nameField.sendKeys(name);
    }

    public void typeEmail(String email){
        emailField.sendKeys(email);
    }

    public void selectChatMatter(String item){
        Select chatMatterCombobox = new Select(chatMatter);
        chatMatterCombobox.selectByVisibleText(item);
    }

    public void clickStartTheChat(){
        startTheChatButton.click();
    }

    public String getValidationMessageText(){
        return validationMessage.getText();
    }
}
