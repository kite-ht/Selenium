package BaiTap.TC01;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import driver.driverFactory;
import java.io.File;

public class TC01 {
    @Test
    public static void testcase01() {
        // 1. Init web-driver session
        WebDriver driver = driverFactory.getChromeDriver();
        try {
            // 2. Open target page - Login Form
            driver.get("http://live.techpanda.org/index.php/");

            // 3: Verify Title of the page
            String expectedTitle = "Home page";
            String actualTitle = driver.getTitle();
            if (actualTitle.contains(expectedTitle)) {
                System.out.println("Title matches: " + actualTitle);
            } else {
                System.out.println("Title doesn't match: " + actualTitle);
            }

            // 4: Click on MOBILE menu
            WebElement mobileMenu = driver.findElement(By.linkText("MOBILE"));
            mobileMenu.click();
            captureScreenshot(driver, "/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC01/BeforeSort.png");

            // 5: Select SORT BY dropdown as name
            WebElement sortDropdown = driver.findElement(By.xpath("//select[@title='Sort By']"));
            Select sortSelect = new Select(sortDropdown);
            sortSelect.selectByVisibleText("Name");

            // 6: Take a screenshot after sort
            captureScreenshot(driver, "/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC01/AfterSort.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 7. Quit browser session
        driver.quit();
        // }
    }
    public static void captureScreenshot(WebDriver driver, String filePath) {
        TakesScreenshot sc = (TakesScreenshot) driver;
        File srcFile = sc.getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(srcFile, new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}