package utilities;

import java.io.IOException;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.PropertyManager;

public class WebDriverFactory {
    private static final Logger logger = LogManager.getLogger(WebDriverFactory.class);

    public static WebDriver createDriver() {
        String browserType = PropertyManager.getProperty("web.browser");
        boolean isHeadless = PropertyManager.getBooleanProperty("web.headless");

        logger.info("Creating {} driver, headless: {}", browserType, isHeadless);

        return switch (browserType.toLowerCase()) {
            case "chrome" -> createChromeDriver(isHeadless);
            case "firefox" -> createFirefoxDriver(isHeadless);
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserType);
        };
    }

    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (headless) options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        configureDriver(driver);
        return driver;
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        if (headless) options.addArguments("--headless");
        WebDriver driver = new FirefoxDriver(options);
        configureDriver(driver);
        return driver;
    }

    private static void configureDriver(WebDriver driver) {
        if (PropertyManager.getBooleanProperty("web.windowMaximize")) {
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                PropertyManager.getIntProperty("web.implicitWait")));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                PropertyManager.getIntProperty("web.pageLoadTimeout")));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(
                PropertyManager.getIntProperty("web.scriptTimeout")));
    }
}

