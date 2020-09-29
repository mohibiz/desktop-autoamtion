package HIPPUtils;
/**
 * @author Mohit Gupta
 */

import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import pageobjects.HIPPLoginPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.with;

public class CommonSteps {
    HippConfig hippConfig = new HippConfig();
    private String application_path = hippConfig.getConfig("APPLICATION_PATH");
    private String winium_url = hippConfig.getConfig("WINIUM_URL");


    public WiniumDriver MaximizeWindow() {
        WiniumDriver winiumDriver = LaunchHIPPAppLite(application_path, winium_url);
        CustomWait(1000);
        HIPPLoginPage hippLoginPage = new HIPPLoginPage(winiumDriver);
//        winiumDriver.getScreenshotAs(OutputType.FILE);
        hippLoginPage.maximizeWindow();
        return winiumDriver;
    }

    public WiniumDriver SignIn(User Role) {
        WiniumDriver winiumDriver = LaunchHIPPAppLite(application_path, winium_url);
        CustomWait(1000);
        HIPPLoginPage hippLoginPage = new HIPPLoginPage(winiumDriver);
        hippLoginPage.login(Role);

        return winiumDriver;
    }


    public WiniumDriver LaunchHIPPAppLite(String application_path, String winium_url) {
        DesktopOptions desktopOptions = new DesktopOptions();
        desktopOptions.setApplicationPath(application_path);
        WiniumDriver winiumDriver = null;
        try {
            winiumDriver = new WiniumDriver(new URL(winium_url), desktopOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return winiumDriver;
    }

    public void CustomWait(int MiliSeconds) {
        try {
            Thread.sleep(MiliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitUntil(boolean expression, int timeOutInSeconds) {
        try {
            with().pollInterval(
                    2,
                    TimeUnit.SECONDS
            ).await().atMost(
                    timeOutInSeconds,
                    TimeUnit.SECONDS).untilTrue(
                    new AtomicBoolean(expression)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
