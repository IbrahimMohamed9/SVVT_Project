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
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlacesPage {
    String baseUrl = "https://www.sarajevoexpats.com/places";
    static WebDriver webDriver;

    @BeforeAll
    public static void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:/Users/User/Desktop/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
    }

    @Test
    public void verifyPlacesFound() throws InterruptedException {
        webDriver.get(baseUrl);
        int expectedValue = webDriver.findElements(
                By.xpath("/html/body/div/div/a")
        ).size();

        WebElement placesFound = webDriver.findElement(By.xpath("/html/body/div/p"));

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth'});",
                placesFound
        );

        int value = Integer.parseInt(placesFound.getText().split(" ")[0]);
        Thread.sleep(1000);

        assertEquals(expectedValue, value);
    }

    @Test
    public void verifyCardNavigation() throws InterruptedException {
        webDriver.get(baseUrl);
        Actions actions = new Actions(webDriver);

        WebElement placesFound = webDriver.findElement(By.xpath("//nav/following::div/p"));
        int places = Integer.parseInt(placesFound.getText().split(" ")[0]);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth'});",
                placesFound
        );

        for(int i = 0; i < places; i++){
            Thread.sleep(1000);

            WebElement card = webDriver.findElement(
                    By.xpath(String.format("/html/body/div/div/a[%d]", i+1))
            );
            String title = card.getText().split("\n")[0];
            actions.moveToElement(card).perform();
            Thread.sleep(500);

            card.click();
            Thread.sleep(1500);
            String articleTitle = webDriver.findElement(By.xpath(
                "/html/body/main/div/article/header/div/h1"
                )).getText();
            assertEquals(title, articleTitle);
            webDriver.navigate().back();
        }
    }

    @AfterAll
    public static void tearDown(){
        if(webDriver != null) webDriver.quit();
    }
}
