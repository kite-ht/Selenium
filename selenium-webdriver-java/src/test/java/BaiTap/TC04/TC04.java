package BaiTap.TC04;

import driver.driverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class TC04 {
    @Test
    public  static  void testcase04(){
        WebDriver driver = new ChromeDriver();
        try {

            // 1. Go to http://live.techpanda.org/
            driver.get("http://live.techpanda.org/");

            // 2. Click on "MOBILE" menu
            WebElement mobileMenu = driver.findElement(By.linkText("MOBILE"));
            mobileMenu.click();

            // 3. Click on "Add To Compare" for Sony Xperia and iPhone
            WebElement sonyXperiaCompare = driver.findElement(By.cssSelector("body > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > ul:nth-child(2) > li:nth-child(2) > div:nth-child(2) > div:nth-child(4) > ul:nth-child(2) > li:nth-child(2) > a:nth-child(2)"));
            sonyXperiaCompare.click();
            WebElement iPhoneCompare = driver.findElement(By.cssSelector("body > div:nth-child(1) > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(4) > ul:nth-child(2) > li:nth-child(3) > div:nth-child(2) > div:nth-child(4) > ul:nth-child(2) > li:nth-child(2) > a:nth-child(2)"));
            iPhoneCompare.click();
            TakesScreenshot addcompare =((TakesScreenshot)driver);
            File srcFile1= addcompare.getScreenshotAs(OutputType.FILE);
            FileHandler.copy(srcFile1, new File("/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC04/AddCompare.png"));
            // 4. Click on "COMPARE" button
            WebElement compareButton = driver.findElement(By.cssSelector("button[title='Compare']"));
            compareButton.click();

            // Wait for the pop-up window to open
            Thread.sleep(3000);

            // 5. Switch to the pop-up window
            driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());

            // Verify the heading of the pop-up window
            WebElement heading = driver.findElement(By.xpath("//h1[text()='Compare Products']"));
            Assert.assertTrue(heading.isDisplayed());

            // Verify that the two products are reflected in the pop-up window
            WebElement popUpContent = driver.findElement(By.id("product_comparison"));
            String popUpText = popUpContent.getText();
            Assert.assertTrue(popUpText.contains("SONY XPERIA"));
            Assert.assertTrue(popUpText.contains("IPhone"));
            TakesScreenshot comparePage =((TakesScreenshot)driver);
            File srcFile2 = comparePage.getScreenshotAs(OutputType.FILE);
            FileHandler.copy(srcFile2, new File("/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC04/ComparePage.png"));
            Thread.sleep(3000);

            // 6. Close the pop-up window
            driver.close();

            // Switch back to the main window
            driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        driver.quit();
    }
}
