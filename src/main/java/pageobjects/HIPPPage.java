package pageobjects;
/**
 * @author Mohit Gupta
 */

import automation.base.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.WiniumDriver;


public abstract class HIPPPage extends Page {


    public HIPPPage(WiniumDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return this.driver.getTitle();
    }

    public boolean hasPopupWin() {
        return this.driver.findElements(By.className("PopupBean")).stream().anyMatch(WebElement::isDisplayed);
    }

    public WebElement closeButton = getElementById(driver, "Close");

    public WebElement MaximizeButton = getElementById(driver, "MaximizeBtn");

    public WebElement MinimizeButton = getElementById(driver, "MinimizeBtn");

    public void maximizeWindow() {
        this.driver.manage().window().maximize();
    }

    public abstract boolean exists();

    public WebElement getElementById(WiniumDriver driver, String elementId) {

        WebElement dummyElement = null;
        try {
            return driver.findElement(By.id(elementId));
        } catch (Exception e) {
            return dummyElement;
        }

    }

    public WebElement getElementByName(WiniumDriver driver, String elementName) {
        WebElement dummyElement = null;
        try {
            return driver.findElement(By.name(elementName));
        } catch (Exception e) {
            return dummyElement;
        }

    }
}
