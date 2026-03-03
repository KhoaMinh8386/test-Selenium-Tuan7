package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TextBoxTest extends BaseTest {

    @Test(priority = 1, description = "TC01: Valid data submission")
    public void TC01_Valid_Data() {
        System.out.println("Running TC01_Valid_Data...");
        TextBoxPage textBoxPage = new TextBoxPage(driver);

        String name = "Nguyen Van A";
        String email = "vana@example.com";
        String curAddr = "123 Street, District 1";
        String perAddr = "456 Avenue, District 2";

        textBoxPage.enterFullName(name);
        textBoxPage.enterEmail(email);
        textBoxPage.enterCurrentAddress(curAddr);
        textBoxPage.enterPermanentAddress(perAddr);
        textBoxPage.scrollToSubmit();
        textBoxPage.clickSubmit();

        String output = textBoxPage.getOutputText();
        Assert.assertTrue(output.contains(name), "Output should contain name");
        Assert.assertTrue(output.contains(email), "Output should contain email");
        System.out.println("TC01 Passed.");
    }

    @Test(priority = 2, description = "TC02: Invalid email format")
    public void TC02_Invalid_Email() {
        System.out.println("Running TC02_Invalid_Email...");
        TextBoxPage textBoxPage = new TextBoxPage(driver);

        textBoxPage.enterEmail("invalid-email");
        textBoxPage.scrollToSubmit();
        textBoxPage.clickSubmit();

        Assert.assertTrue(textBoxPage.isEmailInvalid(), "Email field should have error class");
        System.out.println("TC02 Passed.");
    }

    @Test(priority = 3, description = "TC03: Empty name field")
    public void TC03_Empty_Name() {
        System.out.println("Running TC03_Empty_Name...");
        TextBoxPage textBoxPage = new TextBoxPage(driver);

        textBoxPage.enterFullName("");
        textBoxPage.enterEmail("test@test.com");
        textBoxPage.scrollToSubmit();
        textBoxPage.clickSubmit();

        String output = textBoxPage.getOutputText();
        Assert.assertFalse(output.contains("Name:"), "Output box should not show name if empty");
        System.out.println("TC03 Passed.");
    }

    @Test(priority = 4, description = "TC04: Long address submission")
    public void TC04_Long_Address() {
        System.out.println("Running TC04_Long_Address...");
        TextBoxPage textBoxPage = new TextBoxPage(driver);

        StringBuilder longAddr = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            longAddr.append("Long Address Part " + i + " ");
        }

        textBoxPage.enterCurrentAddress(longAddr.toString());
        textBoxPage.scrollToSubmit();
        textBoxPage.clickSubmit();

        String output = textBoxPage.getOutputText();
        Assert.assertTrue(output.contains("Current Address :"), "Output should be visible without crash");
        System.out.println("TC04 Passed.");
    }

    @Test(priority = 5, description = "TC05: Special characters in name")
    public void TC05_Special_Character_Name() {
        System.out.println("Running TC05_Special_Character_Name...");
        TextBoxPage textBoxPage = new TextBoxPage(driver);

        String specialName = "John @#%^&*() Doe";
        textBoxPage.enterFullName(specialName);
        textBoxPage.scrollToSubmit();
        textBoxPage.clickSubmit();

        String output = textBoxPage.getOutputText();
        Assert.assertTrue(output.contains(specialName), "Output should handle special characters correctly");
        System.out.println("TC05 Passed.");
    }

    @Test(priority = 6, description = "TC06: Only required fields (Simulated)")
    public void TC06_Only_Required_Field() {
        System.out.println("Running TC06_Only_Required_Field...");
        TextBoxPage textBoxPage = new TextBoxPage(driver);

        String name = "Requirement Test";
        String email = "req@test.com";

        textBoxPage.enterFullName(name);
        textBoxPage.enterEmail(email);
        textBoxPage.scrollToSubmit();
        textBoxPage.clickSubmit();

        String output = textBoxPage.getOutputText();
        Assert.assertTrue(output.contains(name), "Output should contain name");
        Assert.assertTrue(output.contains(email), "Output should contain email");
        System.out.println("TC06 Passed.");
    }
}
