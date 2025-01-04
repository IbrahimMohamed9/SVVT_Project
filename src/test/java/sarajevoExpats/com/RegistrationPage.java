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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RegistrationPage {
    static WebDriver webDriver;
    String baseUrl = "https://www.sarajevoexpats.com/register";

    private String getRandomString() {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder();
        Random rnd = new Random();
        while (result.length() < 8) {
            int index = (int) (rnd.nextFloat() * CHARS.length());
            result.append(CHARS.charAt(index));
        }
        return result.toString();

    }

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/User/Desktop/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        webDriver = new ChromeDriver(options);
    }

    @Test
    public void userRegistrationFrom() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(2000);

        String randomString = getRandomString();

        WebElement email = webDriver.findElement(By.name("email"));
        WebElement userName = webDriver.findElement(By.name("username"));
        WebElement password = webDriver.findElement(By.name("password"));
        WebElement confirmPassword = webDriver.findElement(By.name("confirmPassword"));
        WebElement submitButton = webDriver.findElement(By.xpath("/html/body/main/div/form/button"));

        email.sendKeys(randomString);
        userName.sendKeys("test");
        password.sendKeys("<PASSWORD>");
        confirmPassword.sendKeys("<PASSWORD>");

        assertEquals(randomString, email.getAttribute("value"));
        assertEquals("test", userName.getAttribute("value"));
        assertEquals("<PASSWORD>", password.getAttribute("value"));
        assertEquals("<PASSWORD>", confirmPassword.getAttribute("value"));

        Thread.sleep(2000);
        submitButton.click();

        Thread.sleep(4000);
        String expectedUrl = baseUrl.replace("register", "login");
        assertEquals(expectedUrl , webDriver.getCurrentUrl());
        Thread.sleep(2000);
    }

    @Test
    public void validateRequiredRegistrationFields() throws InterruptedException{
        webDriver.get(baseUrl);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        Thread.sleep(2000);
        WebElement email = webDriver.findElement(By.name("email"));
        WebElement userName = webDriver.findElement(By.name("username"));
        WebElement password = webDriver.findElement(By.name("password"));
        WebElement confirmPassword = webDriver.findElement(By.name("confirmPassword"));
        List<WebElement> webElements = new ArrayList<>(List.of(email, userName, password, confirmPassword));

        for(WebElement webElement : webElements){
            email.sendKeys("");
            String errorMsg = js.executeScript("return arguments[0].validationMessage", webElement).toString();
            assertEquals("Please fill out this field.", errorMsg);
            Thread.sleep(500);
        }
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
    public void validatePasswordsMatch() throws InterruptedException{
        webDriver.get(baseUrl);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        Thread.sleep(2000);
        WebElement password = webDriver.findElement(By.name("password"));
        WebElement confirmPassword = webDriver.findElement(By.name("confirmPassword"));

        password.sendKeys("password123");
        confirmPassword.sendKeys("password124");
        String errorMsg = js.executeScript("return arguments[0].validationMessage", confirmPassword).toString();
        assertNotEquals("", errorMsg);
        Thread.sleep(2000);
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) webDriver.quit();
    }
}
