package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    protected final WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void switchToAnotherWindow(){
        for(String winHandle : driver.getWindowHandles()){
                driver.switchTo().window(winHandle);
        }
    }
}
