package BaiTap.TC02;

import driver.driverFactory;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import static BaiTap.TC01.TC01.captureScreenshot;

public class TC02 {
    @Test
    public static void testcase02() {
        // 1. Init web-driver session
        WebDriver driver = driverFactory.getChromeDriver();
        try {
            // 2. Open target page - Login Form
            driver.get("http://live.techpanda.org/index.php/");
            // 3: Click on MOBILE menu
            WebElement mobileMenu = driver.findElement(By.linkText("MOBILE"));
            mobileMenu.click();
            // 4: Read the cost of Sony Xperia mobile
            WebElement sonyXperiaPriceElement = driver.findElement(By.xpath("//a[contains(text(),'Sony Xperia')]/../following-sibling::div/span"));
            String sonyXperiaPrice = sonyXperiaPriceElement.getText();
            System.out.println("Price in mobile page: " + sonyXperiaPrice);
            //Take screenshot
            captureScreenshot(driver, "/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC02/MobilePage.png");
            // 5: Click on Sony Xperia mobile
            WebElement sonyXperiaLink = driver.findElement(By.xpath("//a[contains(text(),'Sony Xperia')]"));
            sonyXperiaLink.click();
            //Take screenshot
            captureScreenshot(driver, "/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC02/DetailPage.png");
            // 6: Read the Sony Xperia mobile from the detail page
            WebElement sonyXperiaDetailPriceElement = driver.findElement(By.xpath("//span[@class='price']"));
            String sonyXperiaDetailPrice = sonyXperiaDetailPriceElement.getText();
            // 7: Compare the product value in list and details page
            if (sonyXperiaPrice.equals(sonyXperiaDetailPrice)) {
                System.out.println("Product value in list and details page is equal: " + sonyXperiaPrice);
            } else {
                System.out.println("Product value in list and details page is not equal.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 8. Quit browser session
        driver.quit();
        // }
    }
}

