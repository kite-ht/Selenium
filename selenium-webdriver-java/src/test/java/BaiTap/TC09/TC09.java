package BaiTap.TC09;

import BaiTap.POM.CartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import static BaiTap.TC01.TC01.captureScreenshot;

public class TC09 {
    @Test
    public static void testcase9() {

        //1. Init web-driver session
        WebDriver driver = new ChromeDriver();
        CartPage cartPage = new CartPage(driver);
        try {
            //Step 1. Go to http://live.techpanda.org/
            driver.get("http://live.techpanda.org/index.php/");
            //Step 2. Click on �MOBILE� menu
            WebElement mobile = driver.findElement(By.linkText("MOBILE"));
            mobile.click();
            Thread.sleep(1000);
            cartPage.addIphoneToCart();
            //Step 3. Enter Coupon Code
            double oldGrand = Double.parseDouble(cartPage.getInitGrandTotal());
            cartPage.enterDiscountCode("GURU50");
            cartPage.clickApplyDiscount();
            //Step 4. Verify the discount generated
            System.out.println(cartPage.discountGenerated());
            AssertJUnit.assertFalse(cartPage.discountGenerated().isEmpty());
            AssertJUnit.assertNotSame("",cartPage.discountGenerated());
            Thread.sleep(1000);
            captureScreenshot(driver,"/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC09/verifyDiscount.png");
            cartPage.verifyDiscount(oldGrand);
        }catch (Exception e){
            e.printStackTrace();
        }
        //7. Quit browser session
        driver.quit();
    }
}
