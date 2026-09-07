import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Tag("functional")
public class TableTest {
    String baseUrl = System.getenv().getOrDefault("BASE_URL", "http://localhost:8080/");
    boolean isDocker = System.getenv("BASE_URL") != null;

    @Test
    public void testTable() {
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

                for (int i = 0; i < 15; i++) {
                    page.locator("section.graph-section svg").click(
                            new Locator.ClickOptions().setPosition(100, 100)
                    );
                }

                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Вперед")).click();
                assertThat(page.locator(".pagination span")).hasText("Страница 2");
            }
        }
    }
}
