package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helper.Attach;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static helper.Attach.*;
import static java.lang.String.format;

public class TestBase {

    @BeforeAll
    static void configure() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("browserName", System.getProperty("browserName", "chrome"));
        capabilities.setCapability("browserVersion", System.getProperty("browserVersion", "99"));
        Configuration.browserCapabilities = capabilities;
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.baseUrl = "https://demowebshop.tricentis.com/";
        RestAssured.baseURI = "https://demowebshop.tricentis.com/";

        if (System.getProperty("remote") != null)
            Configuration.remote = format("https://%s:%s@%s/wd/hub", System.getProperty("user"), System.getProperty("password"), System.getProperty("remote"));
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

}
