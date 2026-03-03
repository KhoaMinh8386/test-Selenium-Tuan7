package dtm.pages;

import dtm.utils.WaitHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage {
    private WebDriver driver;
    private WaitHelper waitHelper;

    @FindBy(id = "email_create")
    private WebElement emailStep1;

    @FindBy(id = "SubmitCreate")
    private WebElement createAccountButton;

    @FindBy(id = "customer_firstname")
    private WebElement firstNameInput;

    @FindBy(id = "customer_lastname")
    private WebElement lastNameInput;

    @FindBy(id = "passwd")
    private WebElement passwordInput;

    @FindBy(id = "days")
    private WebElement dayDropdown;

    @FindBy(id = "months")
    private WebElement monthDropdown;

    @FindBy(id = "years")
    private WebElement yearDropdown;

    @FindBy(id = "id_state")
    private WebElement stateDropdown;

    @FindBy(id = "address1")
    private WebElement addressInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "postcode")
    private WebElement zipInput;

    @FindBy(id = "phone_mobile")
    private WebElement mobileInput;

    @FindBy(id = "submitAccount")
    private WebElement submitButton;

    @FindBy(xpath = "//div[contains(@class,'alert-danger')]//li")
    private WebElement errorMessage;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver, 10);
        PageFactory.initElements(driver, this);
    }

    public void nhapEmailStep1(String email) {
        waitHelper.waitForElementVisible(emailStep1);
        emailStep1.clear();
        emailStep1.sendKeys(email);
    }

    public void clickCreateAccount() {
        createAccountButton.click();
    }

    public void nhapThongTinCaNhan(String firstName, String lastName, String password, String address, String city,
            String zip, String mobile) {
        waitHelper.waitForElementVisible(firstNameInput);
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        passwordInput.sendKeys(password);
        addressInput.sendKeys(address);
        cityInput.sendKeys(city);
        zipInput.sendKeys(zip);
        mobileInput.sendKeys(mobile);
    }

    public void chonNgaySinh(String day, String month, String year) {
        new Select(dayDropdown).selectByValue(day);
        new Select(monthDropdown).selectByValue(month);
        new Select(yearDropdown).selectByValue(year);
    }

    public void chonState(String state) {
        new Select(stateDropdown).selectByVisibleText(state);
    }

    public void scrollToSubmitButton() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", submitButton);
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public String layThongBaoLoi() {
        try {
            return errorMessage.getText();
        } catch (Exception e) {
            return null;
        }
    }
}
