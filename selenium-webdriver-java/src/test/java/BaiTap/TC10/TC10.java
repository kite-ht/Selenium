package BaiTap.TC10;

import BaiTap.POM.BackendLogin;
import BaiTap.POM.OrderMenuPage;
import driver.driverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static BaiTap.TC01.TC01.captureScreenshot;

public class TC10 {
    @Test
    public static void TC10() {
        WebDriver driver = driverFactory.getChromeDriver();
        try {
            // 1. Go to http://live.techpanda.org/index.php/backendlogin
            driver.get("http://live.techpanda.org/index.php/backendlogin");

            // 2. Login the credentials provided
            BackendLogin loginPage = new BackendLogin(driver);
            loginPage.enterUsername("user01");
            loginPage.enterPassword("guru99com");
            loginPage.clickLoginButton();

            // 3. Go to Sales-> Orders menu
            OrderMenuPage o = new OrderMenuPage(driver);
            //o.selectOrdersLink("//*[@id=\"nav\"]/li[1]/ul/li[1]/a/span");
            o.clickOrdersLinkLocator();

            // 4. Input OrderId and FromDate -> ToDate
            o.enterOrderId("100021247");
            o.enterFromDateInputLocator("11/7/2023");
            o.enterToDateInputLocator("11/10/2023");

            // 5. Click Search button
            o.clickSearchButtonLocator();

            captureScreenshot(driver,"/Users/hatuankiet/Downloads/Selenium/selenium-webdriver-java/src/test/java/BaiTap/TC10/searchOrder.png");
            Thread.sleep(2000);

        }  catch (Exception e) {
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
