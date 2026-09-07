import com.microsoft.playwright.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Tag("functional")
public class PointFormTest {
    String baseUrl = System.getenv().getOrDefault("BASE_URL", "http://localhost:8080/");
    boolean isDocker = System.getenv("BASE_URL") != null;

    @Test
    public void testSimpleInput() {
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
                page.click("button:has-text('Очистить')");

                page.locator(".input-row").nth(0).locator("input").fill("0");
                page.locator(".input-row").nth(1).locator("input").fill("0");
                page.locator(".input-row").nth(2).locator("input").fill("1");
                page.click("button:has-text('Отправить')");

                assertThat(page.locator(".form-section p")).hasText("Точка отправлена");
                var firstRow = page.locator("table tbody tr").nth(0);
                assertThat(firstRow.locator("td").nth(3)).hasText("Попадание");
            }
        }
    }

    @Test
    public void testInputButton() {
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
                page.click("button:has-text('Очистить')");
                page.locator(".input-row").nth(0).locator("input").fill("0");
                page.locator(".input-row").nth(1).locator("input").fill("0");
                page.locator(".input-row").nth(2).locator("input").fill("1");

                page.locator(".input-row").nth(0).locator("button:has-text('+')").click();
                page.locator(".input-row").nth(1).locator("button:has-text('-')").click();
                page.click("button:has-text('Отправить')");

                assertThat(page.locator(".form-section p")).hasText("Точка отправлена");
                var firstRow = page.locator("table tbody tr").nth(0);
                assertThat(firstRow.locator("td").nth(3)).hasText("Промах");
            }
        }
    }

    @Test
    public void testClick() {
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

                page.locator("section.graph-section svg").click(
                        new Locator.ClickOptions().setPosition(150, 100)
                );

                assertThat(page.locator(".form-section p")).hasText("Точка отправлена");
            }
        }
    }

    @Test
    public void testEdgeInput() {
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

                page.locator(".input-row").nth(0).locator("input").fill("0.5");
                page.locator(".input-row").nth(1).locator("input").fill("1");
                page.locator(".input-row").nth(2).locator("input").fill("1");
                page.click("button:has-text('Отправить')");

                assertThat(page.locator(".form-section p")).hasText("Точка отправлена");
                var firstRow = page.locator("table tbody tr").nth(0);
                assertThat(firstRow.locator("td").nth(3)).hasText("Попадание");
            }
        }
    }

    @Test
    public void testManyZeroInput() {
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

                page.locator(".input-row").nth(0).locator("input").fill("0.500000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
                page.locator(".input-row").nth(1).locator("input").fill("1");
                page.locator(".input-row").nth(2).locator("input").fill("1");
                page.click("button:has-text('Отправить')");

                assertThat(page.locator(".form-section p")).hasText("Точка отправлена");
                var firstRow = page.locator("table tbody tr").nth(0);
                assertThat(firstRow.locator("td").nth(3)).hasText("Попадание");
            }
        }
    }

    @Test
    public void testEndOneInput() {
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

                page.locator(".input-row").nth(0).locator("input").fill("0.5000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001");
                page.locator(".input-row").nth(1).locator("input").fill("1");
                page.locator(".input-row").nth(2).locator("input").fill("1");
                page.click("button:has-text('Отправить')");

                assertThat(page.locator(".form-section p")).hasText("Точка отправлена");
                var firstRow = page.locator("table tbody tr").nth(0);
                assertThat(firstRow.locator("td").nth(3)).hasText("Промах");
            }
        }
    }

    @Test
    public void testBadInput() {
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

                page.locator(".input-row").nth(0).locator("input").fill("111111111111111111111111111");
                page.locator(".input-row").nth(1).locator("input").fill("1");
                page.locator(".input-row").nth(2).locator("input").fill("1");

                assertThat(page.locator(".input-row").nth(0).locator("input")).hasText("");
            }
        }
    }
}