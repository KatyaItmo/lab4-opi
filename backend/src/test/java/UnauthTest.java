import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Tag("functional")
public class UnauthTest {
    String baseUrl = System.getenv().getOrDefault("BASE_URL", "http://localhost:8080/");
    boolean isDocker = System.getenv("BASE_URL") != null;

    @Test
    public void testLogOut() {
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(isDocker)
                            .setSlowMo(isDocker ? 0 : 1500)
                            .setArgs(List.of("--no-sandbox", "--disable-setuid-sandbox"))
            )) {

                Page page = browser.newPage();
                page.navigate(baseUrl + "WebLab4/");
                page.fill("input[name='login']", "Epstein");
                page.fill("input[name='password']", "ostrov");
                page.click("button[type='submit']");

                page.click("button:has-text('Выйти')");

                assertThat(page).hasURL(baseUrl+"WebLab4");
            }
        }
    }
}
