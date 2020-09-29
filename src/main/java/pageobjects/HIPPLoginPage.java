package pageobjects;
/**
 * @author Mohit Gupta
 */

import HIPPUtils.User;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.WiniumDriver;

public class HIPPLoginPage extends HIPPPage {


    public WebElement user = super.getElementById(driver, "Username");


    public WebElement password = super.getElementById(driver, "Password");


    public WebElement LoginButton = super.getElementById(driver, "LoginBtn");

    public WebElement SingupButton = super.getElementById(driver, "SignupBtn");

    public WebElement login_label = super.getElementById(driver, "Login_Label");

    public HIPPLoginPage(WiniumDriver driver) {
        super(driver);
    }

    public boolean exists() {
        return true;
    }

    public void login(User user) {
        String username = user.getCredentials().get("USERNAME");
        String password = user.getCredentials().get("PASSWORD");
        if (username.isEmpty() || password.isEmpty()) {
            throw new RuntimeException("Username or password for user " + user.name() + " is empty. please set " +
                    "credentials in configuration.xml");
        }
        login(username, password);
    }

    public void login(final String userName, final String passWord) {
        user.sendKeys(userName);
        password.sendKeys(passWord);
        LoginButton.click();
    }

/*    public WebElement getElementById(WiniumDriver driver, String elementId) {
        return driver.findElement(By.id(elementId));
    }*/
}