package dtm.tests;

import dtm.base.BaseTest;
import dtm.pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_RegisterTest extends BaseTest {

    @Test(description = "TC_REG_01: Email hợp lệ → sang Step 2")
    public void testValidEmailStep1() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.nhapEmailStep1("test" + System.currentTimeMillis() + "@gmail.com");
        registerPage.clickCreateAccount();
        // Giả sử chuyển sang Step 2 (form thông tin cá nhân hiện ra)
        // Assert bằng cách kiểm tra một element ở Step 2 xuất hiện
        Assert.assertNull(registerPage.layThongBaoLoi(), "Lẽ ra không có lỗi với email hợp lệ!");
    }

    @Test(description = "TC_REG_02: Email invalid → hiển thị lỗi")
    public void testInvalidEmailStep1() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.nhapEmailStep1("invalid_email");
        registerPage.clickCreateAccount();
        String error = registerPage.layThongBaoLoi();
        Assert.assertNotNull(error, "Lỗi không hiển thị khi nhập email sai định dạng!");
        Assert.assertTrue(error.contains("Invalid email"), "Thông báo lỗi không đúng nội dung!");
    }

    @Test(description = "TC_REG_03: Password < 5 ký tự → lỗi")
    public void testShortPassword() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.nhapEmailStep1("test" + System.currentTimeMillis() + "@gmail.com");
        registerPage.clickCreateAccount();

        registerPage.nhapThongTinCaNhan("John", "Doe", "123", "123 Street", "New York", "10001", "0123456789");
        registerPage.scrollToSubmitButton();
        registerPage.clickSubmit();

        String error = registerPage.layThongBaoLoi();
        Assert.assertTrue(error != null && error.contains("passwd"), "Phải báo lỗi mật khẩu ngắn!");
    }

    @Test(description = "TC_REG_04: First Name > 32 ký tự → lỗi")
    public void testLongFirstName() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.nhapEmailStep1("test" + System.currentTimeMillis() + "@gmail.com");
        registerPage.clickCreateAccount();

        String longName = "ThisIsAVeryVeryVeryVeryVeryLongFirstNameThatExceeds32Characters";
        registerPage.nhapThongTinCaNhan(longName, "Doe", "password123", "123 Street", "New York", "10001",
                "0123456789");
        registerPage.scrollToSubmitButton();
        registerPage.clickSubmit();

        String error = registerPage.layThongBaoLoi();
        Assert.assertTrue(error != null && error.contains("firstname"), "Phải báo lỗi tên quá dài!");
    }

    @Test(description = "TC_REG_05: Zip code không hợp lệ → lỗi")
    public void testInvalidZip() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.nhapEmailStep1("test" + System.currentTimeMillis() + "@gmail.com");
        registerPage.clickCreateAccount();

        registerPage.nhapThongTinCaNhan("John", "Doe", "password123", "123 Street", "New York", "ABCDE", "0123456789");
        registerPage.scrollToSubmitButton();
        registerPage.clickSubmit();

        String error = registerPage.layThongBaoLoi();
        Assert.assertTrue(error != null && error.contains("postcode"), "Phải báo lỗi Zip code không hợp lệ!");
    }

    @Test(description = "TC_REG_06: Submit form trống → lỗi required")
    public void testEmptyForm() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.nhapEmailStep1("test" + System.currentTimeMillis() + "@gmail.com");
        registerPage.clickCreateAccount();

        registerPage.scrollToSubmitButton();
        registerPage.clickSubmit();

        String error = registerPage.layThongBaoLoi();
        Assert.assertTrue(error != null && error.toLowerCase().contains("required"),
                "Phải hiển thị các lỗi trường bắt buộc!");
    }

    @Test(description = "TC_REG_07: Đăng ký hợp lệ đầy đủ → thành công")
    public void testValidRegistration() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.nhapEmailStep1("user" + System.currentTimeMillis() + "@test.com");
        registerPage.clickCreateAccount();

        registerPage.nhapThongTinCaNhan("Alice", "Wonder", "Pass12345", "456 Avenue", "Los Angeles", "90001",
                "0987654321");
        registerPage.chonNgaySinh("10", "5", "1995"); // 10, May, 1995 (values in dropdown)
        registerPage.chonState("California");
        registerPage.scrollToSubmitButton();
        registerPage.clickSubmit();

        // Assert thành công (thường là chuyển đến trang My Account hoặc thấy tên User
        // hiển thị)
        Assert.assertNull(registerPage.layThongBaoLoi(), "Đăng ký hợp lệ không được báo lỗi!");
    }

    @Test(description = "TC_REG_08: Chọn dropdown ngày/tháng/năm + state → không lỗi")
    public void testDropdownSelection() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.nhapEmailStep1("dropdown" + System.currentTimeMillis() + "@test.com");
        registerPage.clickCreateAccount();

        registerPage.chonNgaySinh("1", "1", "2000");
        registerPage.chonState("Alabama");

        // Kiểm tra xem lỗi không xuất hiện ngay khi chọn (unlikely to have error just
        // by selecting)
        Assert.assertNull(registerPage.layThongBaoLoi());
    }
}
