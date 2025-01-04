package sarajevoExpats.com;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.* ;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class NavigationLinks {
    static WebDriver webDriver;
    String baseUrl = "https://www.sarajevoexpats.com/";

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/User/Desktop/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        webDriver = new ChromeDriver(options);
    }

    @Test
    public void navigationBarBtnsDesk() throws InterruptedException {
        webDriver.get(baseUrl);
        List<String> urls = new ArrayList<>(List.of("news", "events", "qaas"));
        List<WebElement> links = webDriver.findElements(By.xpath(
            "(//nav/div/div/div/a[contains(@class,'MuiButtonBase-root MuiButton-root')])"
        ));

        for(int i = 0; i < urls.size(); i++){
            links.get(i).click();

            Thread.sleep(3000);
            String expectedUrl = baseUrl + urls.get(i);
            assertEquals(expectedUrl, webDriver.getCurrentUrl());
        }
        Thread.sleep(2000);
    }

    @Test
    public void navigationBarBtnsMobile() throws InterruptedException {
        webDriver.get(baseUrl);
        webDriver.manage().window().setSize(new Dimension(375, 667));

        Thread.sleep(1000);

        List<String> urls = new ArrayList<>(List.of("news", "events", "qaas"));
        WebElement humburgerMenu = webDriver.findElement(By.xpath(
            "//button[@aria-label='Toggle mobile navigation']"
        ));

        for(int i = 0; i < urls.size(); i++){
            humburgerMenu.click();
            Thread.sleep(1000);

            WebElement link = webDriver.findElement(By.xpath(String.format(
                "(//a[contains(@class,'MuiButtonBase-root MuiListItemButton-root')])[%d]",
                i + 1
            )));
            Thread.sleep(500);

            link.click();
            Thread.sleep(3000);
            String expectedUrl = baseUrl + urls.get(i);
            assertEquals(expectedUrl, webDriver.getCurrentUrl());
        }
    }

    @Test
    public void navigationBarDropListsDesk() throws InterruptedException {
        webDriver.get(baseUrl);
        WebElement dropListBtn = webDriver.findElement(By.xpath(
            "//nav/div/div/div/button[@type='button']"
            ));
        List<String> placesUrl = new ArrayList<>(List.of(
            "places/Restaurants",
            "places/Museums"
        ));

        for(int i = 0; i < placesUrl.size(); i++){
            dropListBtn.click();
            Thread.sleep(500);

            WebElement place = webDriver.findElement(By.xpath(String.format(
                "(//div/div/ul/a[@role='menuitem'])[%d]", 
                i + 1
            )));
            place.click();
            Thread.sleep(2000);
            String expectedUrl = baseUrl + placesUrl.get(i);
            assertEquals(expectedUrl, webDriver.getCurrentUrl());
        }
    }

    @Test
    public void navigationBarDropListsMobile() throws InterruptedException {
        webDriver.get(baseUrl);
        webDriver.manage().window().setSize(new Dimension(375, 667));
        Thread.sleep(1000);

        WebElement humburgerMenu = webDriver.findElement(By.xpath(
            "//button[@aria-label='Toggle mobile navigation']"
            ));
        List<String> placesUrl = new ArrayList<>(List.of(
            "places/Restaurants",
            "places/Museums"
        ));

        for(int i = 0; i < placesUrl.size(); i++){
            humburgerMenu.click();

            if(i == 0){
                WebElement dropListBtn = webDriver.findElement(By.xpath(
                    "//div[contains(@class,'MuiButtonBase-root MuiListItemButton-root')]"
                ));
                dropListBtn.click();
            }
            Thread.sleep(500);

            WebElement place = webDriver.findElement(By.xpath(String.format(
                "(//a[contains(@class,'MuiButtonBase-root MuiListItemButton-root')])[%d]", 
                i + 2
            )));

            place.click();
            Thread.sleep(2000);

            String expectedUrl = baseUrl + placesUrl.get(i);
            assertEquals(expectedUrl, webDriver.getCurrentUrl());
        }
    }

    @Test
    public void navigationLinksInFooter() throws InterruptedException {
        webDriver.get(baseUrl);
        List<String> urls = new ArrayList<>(List.of("", "contact"));
        List<WebElement> links = webDriver.findElements(By.xpath(
            "(//a[contains(@class,'flex items-center')])"
        ));
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        for(int i = 3; i > 1; i--){
            String script = "arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });";

            js.executeScript(script, links.get(i));
            Thread.sleep(2000);
            links.get(i).click();
            Thread.sleep(2000);
            String expectedUrl = baseUrl + urls.get(i - 2);
            assertEquals(expectedUrl, webDriver.getCurrentUrl());
        }
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) webDriver.quit();
    }
}
