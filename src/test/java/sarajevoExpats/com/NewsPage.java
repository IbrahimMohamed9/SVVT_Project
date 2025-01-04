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

public class NewsPage {
    String baseUrl = "https://www.sarajevoexpats.com/news";
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
    public void verifyNewsFound() throws InterruptedException {
        webDriver.get(baseUrl);
        int expectedValue = webDriver.findElements(
                By.xpath("/html/body/div/div[2]/div/div/a"
            )).size();

        WebElement newsFound = webDriver.findElement(By.xpath("/html/body/div/div[2]/div/p"));
        int value = Integer.parseInt(newsFound.getText().split(" ")[0]);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth'});",
                newsFound
        );

        Thread.sleep(1000);

        assertEquals(expectedValue, value);
    }

    @Test
    public void verifyCardNavigation() throws InterruptedException {
        webDriver.get(baseUrl);
        Actions actions = new Actions(webDriver);

        WebElement newsFound = webDriver.findElement(By.xpath("/html/body/div/div[2]/div/p"));
        int news = Integer.parseInt(newsFound.getText().split(" ")[0]);

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth'});",
                newsFound
        );
        for(int i = 0; i < news; i++){
            Thread.sleep(1000);

            WebElement card = webDriver.findElement(
                    By.xpath(String.format("/html/body/div/div[2]/div/div/a[%d]", i+1))
            );
            String title = card.getText().split("\n")[0];
            actions.moveToElement(card).perform();
            Thread.sleep(500);

            card.click();
            Thread.sleep(3000);
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
