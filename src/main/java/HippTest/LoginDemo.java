package HippTest;
/**
 * Tests for HIPP Login Page
 *
 * @author Mohit Gupta
 */

import HIPPUtils.CommonSteps;
import HIPPUtils.User;
import automation.base.HippTest;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.HIPPLoginPage;


public class LoginDemo extends HippTest {
    private String incorrect_credentials_message = "Incorrect Username or Password";


    CommonSteps commonSteps = new CommonSteps();

    @Test(testName = "InvoicerSignIn_Attempt1")
    public void InvoicerSignIn1() {
        WiniumDriver winiumDriver = commonSteps.SignIn(User.INOVOICER1);

    }

    @Test(testName = "InvoicerSignIn_Attempt2")
    public void InvoicerSignIn2() {
        WiniumDriver winiumDriver = commonSteps.SignIn(User.INOVOICER2);
        winiumDriver.close();
    }

    @Test(testName = "AdminSignIn")
    public void AdminSignIn() {
        WiniumDriver winiumDriver = commonSteps.SignIn(User.ADMIN);
        winiumDriver.close();
    }

    @Test(testName = "PMASignIn")
    public void PMASignIn() {
        WiniumDriver winiumDriver = commonSteps.SignIn(User.PMA);
        winiumDriver.close();
    }

    @Test(testName = "InvalidSignIn")
    public void InvalidSignIn() {
        HIPPLoginPage hippLoginPage = new HIPPLoginPage(commonSteps.SignIn(User.INVALID));
        String login_message = hippLoginPage.login_label.getAttribute("Name");
        Assert.assertEquals(login_message, incorrect_credentials_message);
        hippLoginPage.closeButton.click();

    }

/*    @AfterTest
    public  void Cleanup(){
        WiniumDriver winiumDriver = getDriver();
        winiumDriver.close();
    }*/


}
