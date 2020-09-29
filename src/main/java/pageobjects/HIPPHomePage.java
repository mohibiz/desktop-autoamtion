package pageobjects;
/**
 * @author Mohit Gupta
 */

import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.WiniumDriver;

public class HIPPHomePage extends HIPPPage {


    public WebElement SidePanelButtonClose = super.getElementById(driver, "SidePanel_BtnClose");
    public WebElement SidePanelButtonOpen = super.getElementById(driver, "SidePanel_BtnOpen");
    public WebElement InvoicingButton = super.getElementById(driver, "InvoicingBtn");

    public WebElement Toggle_Button = super.getElementById(driver, "PART_Toggle");


    public HIPPHomePage(WiniumDriver driver) {
        super(driver);
    }

    public boolean exists() {
        return true;
    }


}