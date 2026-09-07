import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Tag("functional")
public class NewTest {

//    @Test
//    void registerTest() {
//        try (Playwright pw = Playwright.create()) {
//            Browser bro = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
//            Page page = bro.newPage();
//
//            page.navigate("http://localhost:8080/WebLab4/");
//
//            page.locator(".link-btn").click();
//
//            page.locator(".text-input[name='login']").fill("newLogin");
//            page.locator(".text-input[name='password']").fill("pass");
//            page.getByText("Зарегистрироваться").click();
//
//            assertThat(page.locator(".status-msg")).hasText("Успешная регистрация");
//
//        }
//    }
}
