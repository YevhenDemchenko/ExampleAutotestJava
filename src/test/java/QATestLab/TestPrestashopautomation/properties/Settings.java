package QATestLab.TestPrestashopautomation.properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import java.io.File;
import java.net.URISyntaxException;



public abstract class Settings {
    private static WebDriver getDriver(String browserName) {
        try {
            if(browserName == "chrome") {
                System.setProperty("webdriver.chrome.driver", new File(Settings.class.getResource("/chromedriver.exe").toURI()).toString());
                return new ChromeDriver();
			}
			else if(browserName == "firefox") {
                System.setProperty("webdriver.gecko.driver", new File(Settings.class.getResource("/geckodriver.exe").toURI()).toString());
				return new FirefoxDriver();
			}
			else if(browserName == "ie") {
                System.setProperty("webdriver.ie.driver", new File(Settings.class.getResource("/IEDriverServer.exe").toURI()).toString());
                return new InternetExplorerDriver();
			}
			else {
                System.setProperty("webdriver.chrome.driver", new File(Settings.class.getResource("/chromedriver.exe").toURI()).toString());
                return new ChromeDriver();
			}
        } 
		catch (URISyntaxException e) {
            e.printStackTrace();
            System.err.println("Something went wrong with the ULR to the file ...");
            System.exit(1);
            return null;
        }
    }
}