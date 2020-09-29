package HippTest;
/**
 * Tests for HIPP home Page
 *
 * @author Mohit Gupta
 */

import HIPPUtils.CommonSteps;
import HIPPUtils.User;
import automation.base.HippTest;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.annotations.Test;
import pageobjects.HIPPHomePage;
import pageobjects.HIPPSettingTogglePage;

public class HomePageDemo extends HippTest {

    CommonSteps commonSteps = new CommonSteps();


    @Test(testName = "CheckSidePanelFunctions")
    public void CheckSidePanelFunctions() {
        HIPPHomePage hippHomePage = new HIPPHomePage(commonSteps.SignIn(User.INOVOICER1));
        given("login successful");
        hippHomePage.SidePanelButtonClose.click();
        then("clicked on side panel close button");
        hippHomePage.SidePanelButtonOpen.click();
        then("clicked on side panel open button");
        hippHomePage.InvoicingButton.click();
        then("clicked on Invoicing Button");
    }

    @Test(testName = "CheckSettingFunction")
    public void CheckSettingFunction() {
        WiniumDriver winiumDriver = commonSteps.SignIn(User.INOVOICER1);
        HIPPHomePage hippHomePage = new HIPPHomePage(winiumDriver);
        given("login successful");
        hippHomePage.Toggle_Button.click();
        HIPPSettingTogglePage hippSettingTogglePage = new HIPPSettingTogglePage(winiumDriver);
        commonSteps.waitUntil(hippSettingTogglePage.SettingsButton.isDisplayed(), 200);
        hippSettingTogglePage.SettingsButton.click();
    }

    @Test(testName = "CheckSettingFunction")
    public void CheckRefreshDataFunction() {
        WiniumDriver winiumDriver = commonSteps.SignIn(User.INOVOICER1);
        HIPPHomePage hippHomePage = new HIPPHomePage(winiumDriver);
        given("login successful");
        hippHomePage.Toggle_Button.click();
        HIPPSettingTogglePage hippSettingTogglePage = new HIPPSettingTogglePage(winiumDriver);
        commonSteps.waitUntil(hippSettingTogglePage.RefreshData.isDisplayed(), 200);
        hippSettingTogglePage.RefreshData.click();
    }


    @Test(testName ="CheckSettingFunction")
    public void ChecksavedataFunction() {
    WiniumDriver winiumDriver =commonSteps.SignIn(User.INOVOICER1);
    HIPPHomePage hippHomePage =new HIPPHomePage(winiumDriver);
    given("login successful");
//    hippHomePage.Toggle_Button.click();
    hippHomePage.InvoicingButton.click();


    }


}
