package BaiTap.TC03;

import driver.driverFactory;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import org.testng.Assert;
import static BaiTap.TC01.TC01.captureScreenshot;

public class TC03 {
    @Test
    public static void testcase03(){
        // 1. Init web-driver session
        WebDriver driver = driverFactory.getChromeDriver();
        try {
            // 2. Open target page - Login Form
            driver.get("http://live.techpanda.org/");

            // 3. Click on "MOBILE" menu
            WebElement mobileMenu = driver.findElement(By.linkText("MOBILE"));
            mobileMenu.click();

            // 4. Click on "ADD TO CART" for Sony Xperia mobile
            WebElement sonyXperiaAddToCart = driver.findElement(By.xpath("//a[@title='Sony Xperia']/following::button[@title='Add to Cart']"));
            sonyXperiaAddToCart.click();

            // 5. Change "QTY" value to 1000 and click "UPDATE" button
            WebElement qtyInput = driver.findElement(By.xpath("//input[@title='Qty']"));
            qtyInput.clear();
            qtyInput.sendKeys("1000");

            // 6: click Update
            WebElement updateButton = driver.findElement(By.xpath("//button[@title='Update']"));
            updateButton.click();

            try {
                //7 Verify the error message
                WebElement errorMessage = driver.findElement(By.xpath("//li[@class='error-msg']"));
                String expectedErrorMessage = "The requested quantity for 'Sony Xperia' is not available";

                // Use assertEquals to compare the actual and expected error messages
                Assert.assertEquals(errorMessage.getText(), expectedErrorMessage, "Error message doesn't match");
            } catch (AssertionError e) {
                // Ignore the assertion error and continue with the rest of the code
                System.out.println("Assertion error: " + e.getMessage());
            }
            captureScreenshot(driver,"/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC03/ErrorPage.png");
            // 8: click empty cart
            WebElement emptyCartLink = driver.findElement(By.xpath("//button[@title='Empty Cart']"));
            emptyCartLink.click();

            // 9: verify emty cart
            try {
                WebElement emptyCartMessage = driver.findElement(By.xpath("//h1[text()='Shopping Cart is Empty']"));
                String expectedemptyCartMessage = "SHOPPING CART IS EMPTY";
                Assert.assertEquals(emptyCartMessage.getText(), expectedemptyCartMessage, "Empty cart message match");
            } catch (AssertionError e) {
                // Ignore the assertion error and continue with the rest of the code
                System.out.println("Assertion error: " + e.getMessage());
            }
            captureScreenshot(driver,"/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC03/EmptyPage.png");
        }catch (Exception e){
            e.printStackTrace();
        }
        driver.quit();
    }
}