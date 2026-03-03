package dtm.tests;

import dtm.base.BaseTest;
import dtm.data.DangNhapData;
import dtm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_DangNhapTest extends BaseTest {
    @Test(dataProvider = "du_lieu_dang_nhap", dataProviderClass = DangNhapData.class, description = "Kiểm thử đăng nhập với nhiều bộ dữ liệu")
    public void kiemThuDangNhap(String username, String password, String ketQuaMongDoi, String moTa) {
        System.out.println("Running test for: " + moTa);

        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get("https://www.saucedemo.com/");

        loginPage.dangNhap(username, password);

        switch (ketQuaMongDoi) {
            case "THANH_CONG":
                Assert.assertTrue(loginPage.isDangOTrangSanPham(),
                        "LỖI: " + moTa + " - Lẽ ra phải đăng nhập thành công!");
                break;

            case "BI_KHOA":
                String errorLocked = loginPage.layThongBaoLoi();
                Assert.assertNotNull(errorLocked, "LỖI: " + moTa + " - Không thấy thông báo lỗi!");
                Assert.assertTrue(errorLocked.contains("Sorry, this user has been locked out"),
                        "LỖI: " + moTa + " - Thông báo lỗi không đúng nội dung bị khóa!");
                break;

            case "SAI_THONG_TIN":
                String errorInfo = loginPage.layThongBaoLoi();
                Assert.assertNotNull(errorInfo, "LỖI: " + moTa + " - Không thấy thông báo lỗi!");
                Assert.assertTrue(errorInfo.contains("Username and password do not match any user in this service"),
                        "LỖI: " + moTa + " - Thông báo lỗi không đúng nội dung sai thông tin!");
                break;

            case "TRUONG_TRONG":
                String errorEmpty = loginPage.layThongBaoLoi();
                Assert.assertNotNull(errorEmpty, "LỖI: " + moTa + " - Không thấy thông báo lỗi khi để trống!");
                Assert.assertTrue(errorEmpty.contains("is required"),
                        "LỖI: " + moTa + " - Thông báo lỗi không chứa text 'is required'!");
                break;

            default:
                Assert.fail("Kết quả mong đợi '" + ketQuaMongDoi + "' không được định nghĩa trong test logic!");
        }
    }
}
