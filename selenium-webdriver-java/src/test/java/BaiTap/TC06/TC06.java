package BaiTap.TC06;

import BaiTap.POM.CartPage;
import BaiTap.POM.CheckoutPage;
import BaiTap.POM.LoginPage;
import BaiTap.POM.RegisterPage;
import driver.driverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.AssertJUnit.assertEquals;

public class TC06 {
    @Test
    public static void testcase06() {
        // 0. Init web-driver session
        WebDriver driver = driverFactory.getChromeDriver();
        try {
            // 1. Go to http://live.techpanda.org/
            driver.get("http://live.techpanda.org/");

            // 2. Click on My Account link
            RegisterPage registerPage = new RegisterPage(driver);
            registerPage.clickMyAccountLink();

            // 3. Login in application using previously created credential
            LoginPage loginPage = new LoginPage(driver);
            loginPage.enterEmailAddress("testuser102@example.com");
            loginPage.enterPassword("password");
            loginPage.clickLoginButton();

            // 4. Click on MY WISHLIST link
            By myWishlistLinkLocator = By.xpath("//*[@id='top']/body/div/div/div[2]/div/div[1]/div/div[2]/ul/li[8]/a");
            WebElement myWishlistLink = driver.findElement(myWishlistLinkLocator);
            myWishlistLink.click();


            // 5. In next page, Click ADD TO CART link
            By addToCartLinkLocator = By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div[1]/form[1]/div/table/tbody/tr/td[5]/div/button/span");
            WebElement addToCartLink = driver.findElement(addToCartLinkLocator);
            addToCartLink.click();

            // 6. Enter general shipping country, state/province and zip for the shipping cost estimate
            CartPage cartPage = new CartPage(driver);
            cartPage.selectCountryByXPath("//*[@id=\"country\"]/option[234]");
            cartPage.selectStateByXPath("//*[@id='region_id']/option[13]");
            cartPage.enterZipCode("700");
            Thread.sleep(2000);

            // 7. Click Estimate
            cartPage.clickEstimateShipping();

            // 8. Verify Shipping cost generated
            By shippingCostLocator = By.xpath("//*[@id='co-shipping-method-form']/dl/dd/ul/li/label/span");
            WebElement shippingCostElement = driver.findElement(shippingCostLocator);
            String shippingCost = shippingCostElement.getText();
            assertEquals(5.00 * parseCurrencyToDouble(cartPage.getValueOfQuantityLocator()), parseCurrencyToDouble(shippingCost));

            // 9. Select Shipping Cost, Update Total
            cartPage.selectFlatRateShipping();
            cartPage.clickUpdateTotal();

            // 10. Verify shipping cost is added to total
            double totalCost = parseCurrencyToDouble(cartPage.getTotalCost());
            double subtotal = parseCurrencyToDouble(cartPage.getSubtotalLabel());
            double shippingHandling = parseCurrencyToDouble(cartPage.getShippingHandlingLabel());

            assertEquals(totalCost, subtotal + shippingHandling, 0.01);


            // 11. Click "Proceed to Checkout"
            cartPage.clickProceedToCheckout();

            // 12a. Enter Billing Information, and click Continue
            CheckoutPage checkoutPage = new CheckoutPage(driver);
            checkoutPage.selectXByXPath();
            checkoutPage.enterBillingAddress("Your Billing Address");
            checkoutPage.enterBillingCity("Your Billing City");
            checkoutPage.selectBillingState("//*[@id=\"billing:region_id\"]/option[13]");
            checkoutPage.enterBillingZipCode("Your Billing Zip Code");
            checkoutPage.selectBillingCountry("//*[@id=\"billing:country_id\"]/option[234]");
            checkoutPage.enterBillingTelephone("Your Billing Telephone");
            checkoutPage.clickContinueButton();
            Thread.sleep(2000);
            // 12b. Enter Shipping Information, and click Continue
            checkoutPage.clickShippingInformationLink();
            Thread.sleep(2000);
            checkoutPage.clickShippingInformationContinueButton();Thread.sleep(5000);


//            // 12
//            CheckoutPage checkoutPage = new CheckoutPage(driver);
//            checkoutPage.clickShipToThisAddressButton();
//            checkoutPage.clickBillingInformationButtonLocator();
//            Thread.sleep(2000);

            // 13. In Shipping Method, Click Continue
            checkoutPage.clickShippingMethodContinueButton();
            Thread.sleep(5000);

            // 14. In Payment Information select 'Check/Money Order' radio button. Click Continue
            checkoutPage.selectPaymentMethodCheckMoneyOrder();
            checkoutPage.clickPaymentInformationContinueButton();
            Thread.sleep(2000);

            // 15. Click 'PLACE ORDER' button
            checkoutPage.clickPlaceOrderButton();
            Thread.sleep(2000);

            // 16. Verify Order is generated. Note the order number
            String orderVerificationMessage = checkoutPage.getOrderVerificationMessage();
            String expectedOrderVerificationMessage = "YOUR ORDER HAS BEEN RECEIVED.";
            assertEquals(expectedOrderVerificationMessage, orderVerificationMessage);
            System.out.println("Order number: "+ checkoutPage.getOrderNumberLinkText());
            TakesScreenshot orderSC =((TakesScreenshot)driver);
            File srcFile1= orderSC.getScreenshotAs(OutputType.FILE);
            FileHandler.copy(srcFile1, new File("/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC06/Order.png"));

            // Debug purpose only
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 17. Quit browser session
        driver.quit();
    }
    public static double parseCurrencyToDouble(String currencyString) {
        // Remove currency symbols, commas, and other non-numeric characters
        String cleanedString = currencyString.replaceAll("[^0-9.]", "");
        // Parse the cleaned string as a double
        return Double.parseDouble(cleanedString);
    }
}
