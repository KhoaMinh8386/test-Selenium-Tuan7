package dtm.data;

import org.testng.annotations.DataProvider;

public class DangNhapData {
    @DataProvider(name = "du_lieu_dang_nhap")
    public Object[][] getData() {
        return new Object[][] {
                // Hợp lệ
                { "standard_user", "secret_sauce", "THANH_CONG", "Tài khoản thường hợp lệ" },
                { "visual_user", "secret_sauce", "THANH_CONG", "Tài khoản visual hợp lệ" },
                { "performance_glitch_user", "secret_sauce", "THANH_CONG", "Tài khoản performance hợp lệ" },
                { "error_user", "secret_sauce", "THANH_CONG", "Tài khoản error hợp lệ" },

                // Bị khóa
                { "locked_out_user", "secret_sauce", "BI_KHOA", "Tài khoản bị khóa" },

                // Sai thông tin
                { "non_existent_user", "wrong_password", "SAI_THONG_TIN", "Tài khoản không tồn tại" },
                { "standard_user", "wrong_pass", "SAI_THONG_TIN", "Sai mật khẩu" },

                // Để trống
                { "", "secret_sauce", "TRUONG_TRONG", "Để trống username" },
                { "standard_user", "", "TRUONG_TRONG", "Để trống password" },
                { "", "", "TRUONG_TRONG", "Để trống cả hai" },

                // Ký tự đặc biệt / Khoảng trắng
                { " standard_user ", "secret_sauce", "SAI_THONG_TIN", "Username có khoảng trắng đầu cuối" },
                { "admin@123", "password!", "SAI_THONG_TIN", "Username có ký tự đặc biệt" },

                // Giá trị null (cần xử lý trong test method)
                { null, "secret_sauce", "TRUONG_TRONG", "Username là null" },
                { "standard_user", null, "TRUONG_TRONG", "Password là null" }
        };
    }
}
