package BaiTap.TC08;

import BaiTap.POM.CartPage;
import BaiTap.POM.CheckoutPage;
import BaiTap.POM.LoginPage;
import BaiTap.POM.RegisterPage;
import driver.driverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.AssertJUnit.assertEquals;

public class TC08 {
    @Test
    public static void testcase08() {
        // 0. Init web-driver session
        WebDriver driver = driverFactory.getChromeDriver();
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

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

            // 4. Click on 'REORDER' link , change QTY & click Update
            Thread.sleep(2000);
            By reorderLinkLocator = By.xpath("//tr[@class='first odd']/td[@class='a-center view last']//a[@class='link-reorder']");
            driver.findElement(reorderLinkLocator).click();

            Thread.sleep(2000);
            CartPage cartPage = new CartPage(driver);
            cartPage.inputValueQuantityLocator("2");
            cartPage.clickUpdateQuantityButton();

            // 5. Verify Grand Total is changed
            double totalCost = parseCurrencyToDouble(cartPage.getTotalCost());
            double subtotal = parseCurrencyToDouble(cartPage.getSubtotalLabel());
            double shippingHandling = 0.0;
            try {
                shippingHandling = parseCurrencyToDouble(cartPage.getShippingHandlingLabel());
            } catch (Exception e) {
                // Do nothing within the catch block, so the program continues running
            }
            assertEquals(totalCost, subtotal + shippingHandling, 0.01);

            // 6. Complete Billing & Shipping Information
            cartPage.clickProceedToCheckout();

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

            checkoutPage.clickShippingInformationLink();
            checkoutPage.clickShippingInformationContinueButton();
            Thread.sleep(5000);

            checkoutPage.clickShippingMethodContinueButton();
            Thread.sleep(5000);

            checkoutPage.selectPaymentMethodCheckMoneyOrder();
            Thread.sleep(2000);
            checkoutPage.clickPaymentInformationContinueButton();
            Thread.sleep(2000);

            checkoutPage.clickPlaceOrderButton();
            Thread.sleep(2000);

            // 7. Verify order is generated and note the order number
            String orderVerificationMessage = checkoutPage.getOrderVerificationMessage();
            String expectedOrderVerificationMessage = "YOUR ORDER HAS BEEN RECEIVED.";
            assertEquals(expectedOrderVerificationMessage, orderVerificationMessage);
            System.out.println("Order number: "+ checkoutPage.getOrderNumberLinkText());

            // Debug purpose only
            Thread.sleep(2000);


        } catch (Exception e) {
            e.printStackTrace();
        }

        // 11. Quit browser session
        driver.quit();
    }

    public static double parseCurrencyToDouble(String currencyString) {
        // Remove currency symbols, commas, and other non-numeric characters
        String cleanedString = currencyString.replaceAll("[^0-9.]", "");
        // Parse the cleaned string as a double
        return Double.parseDouble(cleanedString);
    }
}
