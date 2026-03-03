package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TextBoxPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By fullNameInput = By.id("userName");
    private By emailInput = By.id("userEmail");
    private By currentAddressInput = By.id("currentAddress");
    private By permanentAddressInput = By.id("permanentAddress");
    private By submitButton = By.id("submit");
    private By outputBox = By.id("output");

    public TextBoxPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterFullName(String name) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(fullNameInput));
            element.clear();
            element.sendKeys(name);
        } catch (Exception e) {
            System.err.println("Error entering full name: " + e.getMessage());
        }
    }

    public void enterEmail(String email) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
            element.clear();
            element.sendKeys(email);
        } catch (Exception e) {
            System.err.println("Error entering email: " + e.getMessage());
        }
    }

    public void enterCurrentAddress(String address) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(currentAddressInput));
            element.clear();
            element.sendKeys(address);
        } catch (Exception e) {
            System.err.println("Error entering current address: " + e.getMessage());
        }
    }

    public void enterPermanentAddress(String address) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(permanentAddressInput));
            element.clear();
            element.sendKeys(address);
        } catch (Exception e) {
            System.err.println("Error entering permanent address: " + e.getMessage());
        }
    }

    public void scrollToSubmit() {
        try {
            WebElement element = driver.findElement(submitButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            System.err.println("Error scrolling to submit: " + e.getMessage());
        }
    }

    public void clickSubmit() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            element.click();
        } catch (Exception e) {
            System.err.println("Error clicking submit button: " + e.getMessage());
        }
    }

    public String getOutputText() {
        try {
            // Wait briefly for output to appear or check if it exists
            if (driver.findElements(outputBox).size() > 0) {
                return driver.findElement(outputBox).getText();
            }
        } catch (Exception e) {
            System.err.println("Error getting output text: " + e.getMessage());
        }
        return "";
    }

    public boolean isEmailInvalid() {
        try {
            WebElement emailField = driver.findElement(emailInput);
            String classAttribute = emailField.getAttribute("class");
            return classAttribute.contains("field-error");
        } catch (Exception e) {
            return false;
        }
    }
}
