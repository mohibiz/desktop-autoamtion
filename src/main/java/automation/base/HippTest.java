package automation.base;
/**
 * @author Mohit Gupta
 */

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class HippTest {
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static volatile ExtentReports report;
    private static ConfigManager configManager = ConfigManager.instance();
    private static String root;
    private static String timeStamp = Helper.getCurrentTimestamp("dd/MM/yyyy HH:mm:ss");
    private static Status suiteStatus;
    private static List<Status> testcaseStatus;

    private Status tempStatus;
    private WiniumDriver driver;
    private String testName;
    private String testDescription;
    private ExtentTest extentTest;

    public HippTest() {
    }

    public static ExtentReports getReport() {
        return report;
    }

    public static void setReport(ExtentReports report) {
        HippTest.report = report;
    }

    @BeforeSuite(
            alwaysRun = true
    )
    public void beforeSuite() {
        report = new ExtentReports();
        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(root + "\\report.html");
        extentHtmlReporter.config().setCSS(".r-img { width: 40%; }");
        extentHtmlReporter.config().setDocumentTitle("HIPP Automation Report");
        extentHtmlReporter.config().setReportName("HIPP Automation Report");
        report.attachReporter(new ExtentReporter[]{extentHtmlReporter});
        suiteStatus = new Status();
        suiteStatus.setStartTime(LocalDateTime.now().toString());
        suiteStatus.setRunID(System.getProperty("artName") + "_" + System.getProperty("suite") + "_" + timeStamp);
        suiteStatus.setApplicationName(System.getProperty("applicationName"));
        suiteStatus.setType("Suite");
        suiteStatus.setArtName(System.getProperty("artName"));
        suiteStatus.setScriptName(System.getProperty("suite"));
        suiteStatus.setEnvironment(System.getProperty("environment"));
        suiteStatus.setCodeBase(System.getProperty("codeBase"));
        testcaseStatus = new ArrayList();
    }

    @BeforeClass
    public void beforeClass() {
    }

    @BeforeMethod(
            alwaysRun = true
    )
    public void before(Method method) {
        this.tempStatus = new Status();
        this.tempStatus.setStartTime(LocalDateTime.now().toString());
        this.tempStatus.setType("Test Case");
        this.tempStatus.setScriptName(((Test) method.getAnnotation(Test.class)).testName().isEmpty() ? method.getName() : ((Test) method.getAnnotation(Test.class)).testName());
        this.setTestName(((Test) method.getAnnotation(Test.class)).testName().isEmpty() ? method.getName() : ((Test) method.getAnnotation(Test.class)).testName());
        this.setTestDescription(((Test) method.getAnnotation(Test.class)).description().isEmpty() ? "" : "<b>Scenario: </b>" + ((Test) method.getAnnotation(Test.class)).description());
        this.setExtentTest(getReport().createTest(this.getTestName(), this.getTestDescription()));


    }

    @AfterMethod(
            alwaysRun = true
    )
    public void cleanup(ITestResult result) {
        this.tempStatus.setEndTime(LocalDateTime.now().toString());
        this.tempStatus.setRunTime(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(Duration.between(LocalDateTime.parse(this.tempStatus.getStartTime()), LocalDateTime.parse(this.tempStatus.getEndTime())).toMillis())));
        if (result.getStatus() == 3) {
            report.removeTest(this.extentTest);
        } else if (result.getStatus() == 1) {
            this.tempStatus.setStatus("Pass");
            this.extentTest.pass(this.testName + " passed");
        } else {
            MediaEntityModelProvider mediaEntityBuilder = null;

            try {
                mediaEntityBuilder = MediaEntityBuilder.createScreenCaptureFromPath(this.takeScreenshot()).build();
            } catch (IOException | AWTException var7) {
                var7.printStackTrace();
            } finally {
                this.tempStatus.setStatus("Fail");
                this.extentTest.fail(this.testName + " failed", mediaEntityBuilder);
                this.extentTest.info(result.getThrowable());
            }
        }

        if (this.driver != null) {
            this.driver.close();
            this.driver.quit();
            this.driver = null;
        }
/*
        if (this.visitor != null) {
            this.visitor.clearCache();
        }*/

        testcaseStatus.add(this.tempStatus);
        this.tempStatus = null;
    }

    @AfterSuite(
            alwaysRun = true
    )
    public void cleanupSuite(ITestContext testContext) {
        suiteStatus.setStatus("Pass");
        Iterator var2 = testcaseStatus.iterator();

        while (var2.hasNext()) {
            Status temp = (Status) var2.next();
            if (temp.getStatus() != "Pass") {
                suiteStatus.setStatus("Fail");
            }
        }

        suiteStatus.setEndTime(LocalDateTime.now().toString());
        suiteStatus.setRunTime(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(Duration.between(LocalDateTime.parse(suiteStatus.getStartTime()), LocalDateTime.parse(suiteStatus.getEndTime())).toMillis())));
        if (null != System.getProperty("performanceStatFlag") && System.getProperty("performanceStatFlag").equalsIgnoreCase("true")) {
            this.dataStore();
        }

        report.flush();
    }

    public void dataStore() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(new File(root + "\\performanceStat" + timeStamp + ".csv"), false));
            List<String> header = new ArrayList(Arrays.asList("RunId", "ApplicationName", "Test Type", "ART Name", "Status", "Script Name", "Start Time", "End Time", "Run Time", "Environment", "Code Base"));
            List<List<String>> data = new ArrayList();
            data.add(new ArrayList(Arrays.asList(suiteStatus.getRunID(), suiteStatus.getApplicationName(), suiteStatus.getType(), suiteStatus.getArtName(), suiteStatus.getStatus(), suiteStatus.getScriptName(), suiteStatus.getStartTime(), suiteStatus.getEndTime(), suiteStatus.getRunTime(), suiteStatus.getEnvironment(), suiteStatus.getCodeBase())));
            Iterator var4 = testcaseStatus.iterator();

            while (var4.hasNext()) {
                Status temp = (Status) var4.next();
                data.add(new ArrayList(Arrays.asList(temp.getRunID(), temp.getApplicationName(), temp.getType(), temp.getArtName(), temp.getStatus(), temp.getScriptName(), temp.getStartTime(), temp.getEndTime(), temp.getRunTime(), temp.getEnvironment(), temp.getCodeBase())));
            }

            String csvData = CSVHandler.createCSVString(header, data);
            pw.print(csvData);
            pw.close();
        } catch (Exception var6) {
            throw new RuntimeException("File Exception");
        }
    }

    public WiniumDriver getDriver() {
        return this.driver;
    }

    public void setDriver(WiniumDriver driver) {
        this.driver = driver;
    }

    public void given(String given) {
        this.extentTest.info("<b>Given</b> " + given);
    }

    public void when(String when) {
        this.extentTest.info("<b>When</b> " + when);
    }

    public void and(String and) {
        this.extentTest.info("<b>And</b> " + and);
    }

    public void then(String then) {
        this.extentTest.info("<b>Then</b> " + then);
    }

    protected String takeScreenshot() throws AWTException, IOException {
//        File scrFile = new File("testfile.png");
        File scrFile = OutputType.FILE.convertFromBase64Png("failed_test");
//        String base64Screenshot = driver.getScreenshotAs(OutputType.BASE64);

//        byte[] decodeScreenshot = Base64.decodeBase64(base64Screenshot.getBytes());
//        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//        Dimension dimension = new Dimension(driver.manage().window().getSize().getWidth(),driver.manage().window().getSize().getHeight());
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

//        Rectangle screenRect = new Rectangle(driver.manage().window().getPosition().getX(),driver.manage().window().getPosition().getY(),driver.manage().window().getSize().getWidth(),driver.manage().window().getSize().getHeight());
        BufferedImage capture = new Robot().createScreenCapture(screenRect);
        ImageIO.write(capture, "bmp", scrFile);

        File file = new File(root + "/" + this.testName + "_" + Helper.getCurrentTimestamp("dd/MM/yyyy HH:mm:ss") + ".png");

        try {
            FileUtils.copyFile(scrFile, file);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return "./" + file.getName();
    }

    public ExtentTest getExtentTest() {
        return this.extentTest;
    }

    public void setExtentTest(ExtentTest extentTest) {
        this.extentTest = extentTest;
    }

    public String getTestName() {
        return this.testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDescription() {
        return this.testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    static {
        root = (new File(configManager.getByKey("REPORT"))).getAbsolutePath() + "\\report_" + timeStamp;
        Helper.createFolder(root);
        System.setProperty("targetFolder", root);
    }
}

