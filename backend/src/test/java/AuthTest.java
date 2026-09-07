import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Tag("functional")
public class AuthTest {
    String baseUrl = System.getenv().getOrDefault("BASE_URL", "http://localhost:8080/");
    boolean isDocker = System.getenv("BASE_URL") != null;

//    @Test
//    public void testSuccessfulRegistration() {
//        try (Playwright playwright = Playwright.create()) {
//            try (Browser browser = playwright.chromium().launch(
//                    new BrowserType.LaunchOptions()
//                            .setHeadless(isDocker)
//                            .setSlowMo(isDocker ? 0 : 1500)
//                            .setArgs(List.of("--no-sandbox", "--disable-setuid-sandbox"))
//            )) {
//
//                Page page = browser.newPage();
//                page.navigate(baseUrl+"WebLab4/");
//
//                page.click(".link-btn");
//                page.fill("input[name='login']", "NewUser3");
//                page.fill("input[name='password']", "Pass3");
//                page.click("button[type='submit']");
//
//                assertThat(page.locator(".status-msg")).hasText("Успешная регистрация");
//            }
//        }
//    }

    @Test
    public void testSuccessfulAuth() {
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(isDocker)
                            .setSlowMo(isDocker ? 0 : 1500)
                            .setArgs(List.of("--no-sandbox", "--disable-setuid-sandbox"))
            )) {

                Page page = browser.newPage();
                page.navigate(baseUrl+"WebLab4/");

                page.fill("input[name='login']", "Epstein");
                page.fill("input[name='password']", "ostrov");
                page.click("button[type='submit']");

                assertThat(page).hasURL(baseUrl+"WebLab4/main");
            }
        }
    }

    @Test
    public void testEmptyAuth() {
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(isDocker)
                            .setSlowMo(isDocker ? 0 : 1500)
                            .setArgs(List.of("--no-sandbox", "--disable-setuid-sandbox"))
            )) {

                Page page = browser.newPage();
                page.navigate(baseUrl+"WebLab4/");

                page.click("button[type='submit']");

                assertThat(page).hasURL(baseUrl+"WebLab4/");
            }
        }
    }

    @Test
    public void testInvalidRegistration() {
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(isDocker)
                            .setSlowMo(isDocker ? 0 : 1500)
                            .setArgs(List.of("--no-sandbox", "--disable-setuid-sandbox"))
            )) {

                Page page = browser.newPage();
                page.navigate(baseUrl+"WebLab4/");

                page.click(".link-btn");
                page.fill("input[name='login']", "Epstein");
                page.fill("input[name='password']", "ostrov");
                page.click("button[type='submit']");

                assertThat(page.locator(".status-msg")).hasText("Логин занят");
            }
        }
    }

    @Test
    public void testMainProtection() {
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(isDocker)
                            .setSlowMo(isDocker ? 0 : 1500)
                            .setArgs(List.of("--no-sandbox", "--disable-setuid-sandbox"))
            )) {

                Page page = browser.newPage();
                page.navigate(baseUrl+"WebLab4/main");

                assertThat(page.locator("body")).containsText("Not Found");
            }
        }
    }
}