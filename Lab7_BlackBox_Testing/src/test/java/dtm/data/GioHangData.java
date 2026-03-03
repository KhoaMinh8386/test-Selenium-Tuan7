package dtm.data;

import org.testng.annotations.DataProvider;

public class GioHangData {
    @DataProvider(name = "cartData")
    public Object[][] getCartData() {
        return new Object[][] {
            {"Sauce Labs Backpack"},
            {"Sauce Labs Bike Light"}
        };
    }
}
