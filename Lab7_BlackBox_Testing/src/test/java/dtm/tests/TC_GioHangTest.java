package dtm.tests;

import dtm.base.BaseTest;
import dtm.pages.CartPage;
import dtm.pages.CheckoutPage;
import dtm.pages.InventoryPage;
import dtm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TC_GioHangTest extends BaseTest {
    InventoryPage inventoryPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    @BeforeMethod
    public void chuanBi() {
        getDriver().get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.dangNhap("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(getDriver());
        cartPage = new CartPage(getDriver());
        checkoutPage = new CheckoutPage(getDriver());
    }

    @Test(groups = { "smoke" }, description = "TC_CART_001: Thêm 1 sản phẩm – badge = 1")
    public void themMotSanPham() {
        inventoryPage.themNSanPhamDauTien(1);
        Assert.assertEquals(inventoryPage.laySoLuongBadge(), 1, "Số lượng badge không đúng sau khi thêm 1 sản phẩm!");
    }

    @Test(groups = { "smoke" }, description = "TC_CART_002: Thêm 3 sản phẩm – badge = 3")
    public void them3SanPham() {
        inventoryPage.themNSanPhamDauTien(3);
        Assert.assertEquals(inventoryPage.laySoLuongBadge(), 3, "Số lượng badge không đúng sau khi thêm 3 sản phẩm!");
    }

    @Test(groups = { "regression" }, description = "TC_CART_003: Xoá hết – giỏ trống")
    public void xoaHetSanPham() {
        inventoryPage.themNSanPhamDauTien(2);
        inventoryPage.clickGioHang();
        cartPage.clickXoaTatCa();
        Assert.assertEquals(cartPage.laySoLuongSanPham(), 0, "Giỏ hàng không trống sau khi xóa tất cả!");
    }

    @Test(groups = { "regression" }, description = "TC_CART_004: Sort giá tăng dần – đúng thứ tự")
    public void sortGiaTangDan() {
        inventoryPage.sortSanPham("lohi");
        List<Double> prices = inventoryPage.layDanhSachGiaSanPham();
        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);
        Assert.assertEquals(prices, sortedPrices, "Danh sách giá không được sắp xếp tăng dần!");
    }

    @Test(groups = { "regression" }, description = "TC_CART_010: Kiểm tra tổng tiền chính xác")
    public void kiemTraTongTien() {
        // Thêm 3 sản phẩm có giá khác nhau
        inventoryPage.themSanPhamTheoTen("Sauce Labs Backpack"); // 29.99
        inventoryPage.themSanPhamTheoTen("Sauce Labs Bike Light"); // 9.99
        inventoryPage.themSanPhamTheoTen("Sauce Labs Onesie"); // 7.99

        double expectedSubtotal = 29.99 + 9.99 + 7.99;

        inventoryPage.clickGioHang();
        cartPage.clickCheckout();

        checkoutPage.dienThongTin("John", "Doe", "12345");

        double itemTotal = checkoutPage.layItemTotal();
        double tax = checkoutPage.layTax();
        double total = checkoutPage.layTotal();

        // So sánh với delta 0.01
        Assert.assertEquals(itemTotal, expectedSubtotal, 0.01, "Item Total tính sai!");
        Assert.assertEquals(tax, Math.round(itemTotal * 0.08 * 100.0) / 100.0, 0.01, "Tax (8%) tính sai!");
        // Note: SauceDemo tax can vary by location but requirement says 8%
        Assert.assertEquals(total, itemTotal + tax, 0.01, "Total (ItemTotal + Tax) tính sai!");
    }

    // --- Bổ sung thêm các test method khác ---

    @Test(groups = {
            "regression" }, description = "TC_CART_011: Thêm sản phẩm trùng lặp (không tăng badge nếu site ko cho, nhưng ở đây mỗi click là 1 remove button hiện ra)")
    public void checkUniqueProductAdd() {
        inventoryPage.themSanPhamTheoTen("Sauce Labs Backpack");
        int initialBadge = inventoryPage.laySoLuongBadge();
        // Cố gắng thêm lại chính sản phẩm đó (nút đã chuyển thành Remove)
        inventoryPage.themSanPhamTheoTen("Sauce Labs Backpack");
        Assert.assertTrue(inventoryPage.laySoLuongBadge() < initialBadge,
                "Click vào nút Remove lẽ ra phải giảm badge!");
    }

    @Test(groups = { "regression" }, description = "TC_CART_012: Kiểm tra badge biến mất khi xóa hết")
    public void badgeDisappearsWhenEmpty() {
        inventoryPage.themNSanPhamDauTien(1);
        inventoryPage.xoaHetSanPham();
        Assert.assertEquals(inventoryPage.laySoLuongBadge(), 0, "Badge vẫn còn hiển thị khi giỏ hàng trống!");
    }

    @Test(groups = { "regression" }, description = "TC_CART_013: Sort tên Z-A")
    public void sortTenZA() {
        inventoryPage.sortSanPham("za");
        List<String> names = inventoryPage.layDanhSachTenSanPham();
        List<String> sortedNames = new ArrayList<>(names);
        Collections.sort(sortedNames, Collections.reverseOrder());
        Assert.assertEquals(names, sortedNames, "Danh sách tên không được sắp xếp Z-A!");
    }
}
