package sarajevoExpats.com;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VerifyHttps {
    String baseUrl = "https://www.sarajevoexpats.com/";
    static WebDriver webDriver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/User/Desktop/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        webDriver = new ChromeDriver(options);
    }

    @Test
    public void verifyHttps() throws InterruptedException {
        List<String> urls = new ArrayList<>(
                List.of("news", "events", "qaas","places","contact","")
        );
        final String baseUrlWithHttp = baseUrl.replace("https://", "http://");
        for(String url : urls){
            webDriver.get(baseUrlWithHttp + url);
            Thread.sleep(2000);

            final String currentUrl = webDriver.getCurrentUrl();

            assertEquals(baseUrl+url, currentUrl);
        }
    }

    @AfterAll
    public static void tearDown() {
        if(webDriver != null) webDriver.quit();
    }
}
