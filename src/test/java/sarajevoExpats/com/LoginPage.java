package sarajevoExpats.com;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class LoginPage {
    static WebDriver webDriver;
    String baseUrl = "https://www.sarajevoexpats.com/login";

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/User/Desktop/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        webDriver = new ChromeDriver(options);
    }

    @Test
    public void loginFrom() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(2000);

        WebElement email = webDriver.findElement(By.name("email"));
        WebElement password = webDriver.findElement(By.name("password"));
        WebElement submitButton = webDriver.findElement(By.xpath("/html/body/main/div/form/button"));

        email.sendKeys("JBYEVHOT");
        password.sendKeys("<PASSWORD>");

        assertEquals("JBYEVHOT", email.getAttribute("value"));
        assertEquals("<PASSWORD>", password.getAttribute("value"));

        Thread.sleep(2000);
        submitButton.click();

        Thread.sleep(4000);
        String expectedUrl = baseUrl.replace("login", "");
        assertEquals(expectedUrl , webDriver.getCurrentUrl());
        Thread.sleep(2000);
    }

    @Test
    public void validateEmailFormat() throws InterruptedException{
        webDriver.get(baseUrl);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        Thread.sleep(2000);
        WebElement email = webDriver.findElement(By.name("email"));
        email.sendKeys("test");
        String errorMsg = js.executeScript("return arguments[0].validationMessage", email).toString();
        assertNotEquals("", errorMsg);
    }

    @Test
    public void incorrectEmailOrPassword() throws InterruptedException{
        webDriver.get(baseUrl);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        Thread.sleep(2000);
        WebElement email = webDriver.findElement(By.name("email"));
        WebElement password = webDriver.findElement(By.name("password"));
        WebElement submitButton = webDriver.findElement(By.xpath("/html/body/main/div/form/button"));

        email.sendKeys("testTestTestTest");
        Thread.sleep(500);
        password.sendKeys("testTestTestTest");
        Thread.sleep(500);
        submitButton.click();

        Thread.sleep(1000);
        WebElement toaster = webDriver.findElement(By.xpath(
                "/html/body/div/div/div[2]"
        ));

        String expectedMsg = "The email or password is incorrect";
        Thread.sleep(1000);
        assertEquals(expectedMsg, toaster.getText());
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) webDriver.quit();
    }
}
