import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Tag("functional")
public class OtherTest {

    @Test
    void clickButton() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
            Page page = browser.newPage();

            page.navigate("https://holyjs.ru/en/", new Page.NavigateOptions().setTimeout(60000));
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("schedule")).first().click();

            assertThat(page).hasURL("https://holyjs.ru/en/schedule/table/");
        }
    }
}