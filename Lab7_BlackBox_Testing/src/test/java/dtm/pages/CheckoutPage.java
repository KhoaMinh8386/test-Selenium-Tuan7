package dtm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {
    private WebDriver driver;

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement zipCodeInput;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void dienThongTin(String fName, String lName, String zip) {
        firstNameInput.sendKeys(fName);
        lastNameInput.sendKeys(lName);
        zipCodeInput.sendKeys(zip);
        continueButton.click();
    }

    public double layItemTotal() {
        return parsePrice(itemTotalLabel.getText());
    }

    public double layTax() {
        return parsePrice(taxLabel.getText());
    }

    public double layTotal() {
        return parsePrice(totalLabel.getText());
    }

    private double parsePrice(String text) {
        // String format usually: "Item total: $29.99"
        String price = text.replaceAll("[^0-9.]", "");
        return Double.parseDouble(price);
    }
}
