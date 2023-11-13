package BaiTap.TC05;

import BaiTap.POM.RegisterPage;
import driver.driverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import static BaiTap.TC01.TC01.captureScreenshot;

public class TC05 {
    @Test
    public static void testcase05() {
        // 0. Init web-driver session
        WebDriver driver = driverFactory.getChromeDriver();
        try {
            // 1. Go to http://live.techpanda.org/
            driver.get("http://live.techpanda.org/");

            // 2. Click on My Account link
            RegisterPage registerPage = new RegisterPage(driver);
            registerPage.clickMyAccountLink();

            // 3. Click Create an Account link and fill New User information excluding the registered Email ID.
            registerPage.clickCreateAccountLink();
            registerPage.enterFirstName("John");
            registerPage.enterLastName("Doe");
            registerPage.enterEmail("testuser104@example.com"); // Enter a unique email
            registerPage.enterPassword("password");
            registerPage.enterConfirmPassword("password");

            // 4. Click Register
            registerPage.clickRegisterButton();

            // 5. Verify Registration is done. Expected account registration done.
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            By registrationConfirmationMessage = By.xpath("//*[@id='top']/body/div/div/div[2]/div/div[2]/div/div/ul/li/ul/li/span");
            WebElement registrationConfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(registrationConfirmationMessage));
            String confirmationText = registrationConfirmation.getText();
            if (confirmationText.equals("Thank you for registering with Main Website Store.")) {
                System.out.println("Registration successful. Test passed!");
            } else {
                System.out.println("Registration failed. Test failed.");
            }
            captureScreenshot(driver,"/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC05/Registersuccess.png");
            // 6. Go to TV menu
            By tvMenuLink = By.xpath("//*[@id='nav']/ol/li[2]/a");
            driver.findElement(tvMenuLink).click();

            // 7. Add product in your wish list - use product - LG LCD
            By addLGToWishlistLink = By.xpath("//*[@id='top']/body/div/div/div[2]/div/div[2]/div[1]/div[2]/ul/li[1]/div/div[3]/ul/li[1]/a");
            driver.findElement(addLGToWishlistLink).click();

            // 8. Click SHARE WISHLIST
            By shareWishlistButton = By.xpath("//*[@id='wishlist-view-form']/div/div/button[1]/span/span");
            driver.findElement(shareWishlistButton).click();

            // 9. In the next page, enter Email and a message and click SHARE WISHLIST
            By emailTextbox = By.id("email_address");
            driver.findElement(emailTextbox).sendKeys("recipient@example.com"); // Enter the recipient's email
            By messageTextbox = By.id("message");
            driver.findElement(messageTextbox).sendKeys("Check out this wishlist!");

            By shareWishlistButton2 = By.xpath("//*[@id='form-validate']/div[2]/button/span/span");
            driver.findElement(shareWishlistButton2).click();

            // 10. Check wishlist is shared. Expected wishlist shared successfully.
            By wishlistSharedMessage = By.xpath("//*[@id='top']/body/div/div/div[2]/div/div[2]/div/div/ul/li/ul/li/span");
            WebElement sharedConfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(wishlistSharedMessage));
            String sharedConfirmationText = sharedConfirmation.getText();
            if (sharedConfirmationText.equals("Your Wishlist has been shared.")) {
                System.out.println("Wishlist shared successfully. Test passed!");
            } else {
                System.out.println("Wishlist sharing failed. Test failed.");
            }
            captureScreenshot(driver,"/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC05/ShareWishlistSuccess.png");
            // Debug purpose only
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 11. Quit browser session
        driver.quit();
    }
}
