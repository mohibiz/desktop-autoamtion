package pageobjects;

/**
 * @author Mohit Gupta
 */

import org.openqa.selenium.winium.WiniumDriver;

public abstract class Page {
    protected WiniumDriver driver;

    public Page(WiniumDriver driver) {
        this.driver = driver;
    }

    public abstract boolean exists();
}

