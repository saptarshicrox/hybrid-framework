package test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.WebDriverFactory;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.createDriver();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}