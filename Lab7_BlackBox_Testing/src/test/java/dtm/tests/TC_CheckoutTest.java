package dtm.tests;

import dtm.base.BaseTest;
import org.testng.annotations.Test;

public class TC_CheckoutTest extends BaseTest {
    @Test
    public void testCheckout() {
        getDriver().get("https://www.saucedemo.com/");
        // Test logic using CheckoutPage...
    }
}
