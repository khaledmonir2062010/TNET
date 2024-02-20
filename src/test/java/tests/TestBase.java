package tests;

import java.sql.*;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.Helper;

public class TestBase {
    static Connection connection = null;
    static Statement statement;
    static ResultSet rs;
    public static WebDriver driver;
    public static String downloadPath = System.getProperty("user.dir") + "\\Downloads";
    public static String env;
    public static String baseURL;

    public static FirefoxOptions firefoxOption() {
        FirefoxOptions option = new FirefoxOptions();
        option.addPreference("browser.download.folderList", 2);
        option.addPreference("browser.download.dir", downloadPath);
        option.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
        option.addPreference("browser.download.manager.showWhenStarting", false);
        option.addPreference("pdfjs.disabled", true);
        return option;
    }

    public static ChromeOptions chromeOption() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default.content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadPath);
        options.setExperimentalOption("prefs", chromePrefs);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        return options;
    }

    ////////////////////////////////Here is the configuration to change on the browser//////////////////////////
    @BeforeSuite
    @Parameters({"browser", "env"})
    public void startDriver(@Optional("chrome") String browserName,
                            @Optional("ENBD") String Environment) {
        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("Edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

////////////////////////////////Here is the configuration to change on the Environment URL//////////////////////////
        switch (Environment) {
            case "ENBD":
                baseURL = "http://weblogictn20-qe.tradenet.com:7002/IMOREGATEWAY/IMORECORE/TN20/BackOffice/ENBD/landing#";
                driver.get(baseURL);
                driver.manage().window().maximize();
                break;
            case "FAB":
                baseURL = "http://yahoo.com/";
                driver.get(baseURL);
                driver.manage().window().maximize();
                break;
            default:
        }
    }

    @AfterSuite
    public void stopDriver() {
        driver.quit();
    }

    // take screenshot when test case fail and add it in the Screenshot folder
    @AfterMethod
    public void screenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Failed!");
            System.out.println("Taking Screenshot....");
            Helper.captureScreenshot(driver, result.getName());
        }
    }

    @BeforeMethod
    public void setUpOracleDBConnection() {

        String databaseURL = "jdbc:oracle:thin:@knet-srv:1522:QEDB1";
        String user = "TRADESUPER";
        String password = "NewEmptyTN";
//        connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Connecting to Database...");
            connection = DriverManager.getConnection(databaseURL, user, password);
            if (connection != null) {
                System.out.println("Connected to the Database...");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (connection != null) {
            try {
                System.out.println("Closing Database Connection...");
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
