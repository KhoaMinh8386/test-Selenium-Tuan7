package dtm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage {
    private WebDriver driver;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(xpath = "//button[text()='Remove']")
    private List<WebElement> removeButtons;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickCheckout() {
        checkoutButton.click();
    }

    public void clickXoaTatCa() {
        for (int i = removeButtons.size() - 1; i >= 0; i--) {
            removeButtons.get(i).click();
        }
    }

    public int laySoLuongSanPham() {
        return cartItems.size();
    }
}
