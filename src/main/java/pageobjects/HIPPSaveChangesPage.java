package pageobjects;
/**
 * @author Mohit Gupta
 */

import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.WiniumDriver;


public class HIPPSaveChangesPage extends HIPPPage {

    public WebElement Yes = super.getElementByName(driver, "Yes");

    public WebElement No = super.getElementByName(driver, "No");

    public WebElement Cancel = super.getElementByName(driver, "Cancel");

    public HIPPSaveChangesPage(WiniumDriver driver) {
        super(driver);
    }

    public boolean exists() {
        return true;
    }


/*    public WebElement getElementById(WiniumDriver driver, String elementId) {
        return driver.findElement(By.id(elementId));
    }*/
}