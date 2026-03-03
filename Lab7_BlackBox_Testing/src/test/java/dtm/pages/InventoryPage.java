package dtm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage {
    private WebDriver driver;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(xpath = "//button[text()='Add to cart']")
    private List<WebElement> addToCartButtons;

    @FindBy(xpath = "//button[text()='Remove']")
    private List<WebElement> removeButtons;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void themSanPhamTheoTen(String tenSanPham) {
        String xpath = String.format("//div[text()='%s']/ancestor::div[@class='inventory_item']//button", tenSanPham);
        driver.findElement(By.xpath(xpath)).click();
    }

    public void themNSanPhamDauTien(int n) {
        for (int i = 0; i < n && i < addToCartButtons.size(); i++) {
            addToCartButtons.get(i).click();
        }
    }

    public int laySoLuongBadge() {
        try {
            return Integer.parseInt(cartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public void clickGioHang() {
        cartLink.click();
    }

    public void sortSanPham(String option) {
        Select select = new Select(sortDropdown);
        select.selectByValue(option);
    }

    public List<String> layDanhSachTenSanPham() {
        List<String> names = new ArrayList<>();
        for (WebElement element : productNames) {
            names.add(element.getText());
        }
        return names;
    }

    public List<Double> layDanhSachGiaSanPham() {
        List<Double> prices = new ArrayList<>();
        for (WebElement element : productPrices) {
            String priceText = element.getText().replace("$", "");
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    public void xoaHetSanPham() {
        for (WebElement btn : removeButtons) {
            btn.click();
        }
    }
}
