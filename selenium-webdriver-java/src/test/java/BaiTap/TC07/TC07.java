package BaiTap.TC07;

import BaiTap.POM.LoginPage;
import BaiTap.POM.RegisterPage;
import driver.driverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Test;

import java.io.File;

import static BaiTap.TC01.TC01.captureScreenshot;

public class TC07 {
    @Test
    public static void testcase07() {

        //1. Init web-driver session
        WebDriver driver = new ChromeDriver();
        RegisterPage registerPage = new RegisterPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        try {
            //Step 1. Go to http://live.techpanda.org/
            driver.get("http://live.techpanda.org/index.php/");
            //Step 2. Click on My Account link
            registerPage.clickMyAccountLink();
            //Step 3. Login in application using previously created credential
            loginPage.enterEmailAddress("testuser102@example.com");
            loginPage.enterPassword("password");
            loginPage.clickLoginButton();
            Thread.sleep(1000);


            //Step 4. Click on 'My Orders'
            WebElement myOrder = driver.findElement(By.linkText(("MY ORDERS")));
            myOrder.click();
            Thread.sleep(1000);
            //Step 5. Click on 'View Order'
            WebElement viewOrder = driver.findElement(By.cssSelector("a[href='http://live.techpanda.org/index.php/sales/order/view/order_id/21187/']"));
            viewOrder.click();
            //Step 6. Click on 'Print Order' link
            WebElement print = driver.findElement(By.cssSelector(".link-print"));
            print.click();
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
            }
            Thread.sleep(5000);
            captureScreenshot(driver,"/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC07/PrintOrder.png");

        }catch (Exception e){
            e.printStackTrace();
        }
        //7. Quit browser session
        driver.quit();
    }
}
