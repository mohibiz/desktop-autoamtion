package automation.base;


import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;

public class DriverManager {
    String winium_url = "http://localhost:9999";

    private DriverManager() {
        throw new IllegalStateException("DriverManager is a utility class");
    }

    public static WiniumDriver getDriver(DesktopOptions options) throws MalformedURLException {
        String winium_url = "http://localhost:9999";
        DesktopOptions desktopOptions = new DesktopOptions();
        WiniumDriver winiumDriver = new WiniumDriver(new URL(winium_url), desktopOptions);
        return winiumDriver;
    }

}

