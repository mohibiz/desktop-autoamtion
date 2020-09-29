package pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.WiniumDriver;

/**
 * @author Mohit Gupta
 */

public class HIPPSettingTogglePage extends HIPPPage {

    public WebElement SettingsButton = super.getElementById(driver, "SettingsBtn");
    public WebElement HelpButton = super.getElementById(driver, "HelpBtn");
    public WebElement CloseButton = super.getElementById(driver, "CloseBtn");
    public WebElement RefreshData = super.getElementByName(driver, "Refresh Data");


    public HIPPSettingTogglePage(WiniumDriver driver) {
        super(driver);
    }

    public WebElement SaveData =super.getElementByName(driver,"SaveChangesBtn");

    public boolean exists() {
        return true;
    }
}
