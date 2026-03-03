package dtm.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

public abstract class BaseTest {
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod
    public void setUp(Method method) {
        System.out.println("Starting Test: " + method.getName());
        WebDriverManager.chromedriver().setup();

        org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();

        // Chạy trình duyệt ở chế độ ẩn danh
        options.addArguments("--incognito");
        options.addArguments("--remote-allow-origins=*");

        // Vô hiệu hóa thanh thông báo "Chrome is being controlled..."
        options.setExperimentalOption("excludeSwitches", new java.util.ArrayList<String>() {
            {
                add("enable-automation");
            }
        });
        options.setExperimentalOption("useAutomationExtension", false);

        // Vô hiệu hóa tính năng lưu mật khẩu
        java.util.Map<String, Object> prefs = new java.util.HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        WebDriver chromeDriver = new ChromeDriver(options);
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.set(chromeDriver);
        System.out.println("Driver initialized for thread: " + Thread.currentThread().getId());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result.getName());
        }

        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
            System.out.println("Driver closed for thread: " + Thread.currentThread().getId());
        }
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    private void takeScreenshot(String testName) {
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String filePath = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + System.currentTimeMillis()
                + ".png";
        try {
            FileUtils.copyFile(scrFile, new File(filePath));
            System.out.println("Screenshot saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
