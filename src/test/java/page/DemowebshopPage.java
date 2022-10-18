package page;

import com.codeborne.selenide.WebDriverRunner;
import data.FakerUser;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;


public class DemowebshopPage {

    private final String header = "__RequestVerificationToken";
    private final String headerValue = "PynBwHJHHyE7NvukB2MS2E9qsBJ__lB_Z1O6XpZ2-rKosYrhvIo11z4xz0vMDhoedru0FkeItqJcjckdwMKWILIJZrld-v_GQ5I1AEtRs0c1";
    private final String paramValue = "58mBp8S83XW634lGOjTPfqAtCiWD3Ff_YPwqRlJm-YwcfGXkw7U8g_BOd-Kja2n-uSVW8Mmx8dao38JdIj56b-Gb00uoy0Hg0e2dtIp2SKM1";
    private final String authCookieName = "NOPCOMMERCE.AUTH";



    public void registration(FakerUser fakerUser) {
        given()
                .when()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam(header, paramValue)
                .formParam("FirstName", fakerUser.firstName)
                .formParam("LastName", fakerUser.lastName)
                .formParam("Email", fakerUser.email)
                .formParam("Password", fakerUser.password)
                .formParam("ConfirmPassword", fakerUser.password)
                .cookie(header, headerValue)
                .post("/register")
                .then();
    }


    public String login(FakerUser fakerUser) {
        String authToken = given()
                .when()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("Email", fakerUser.email)
                .formParam("Password", fakerUser.password)
                .post("/login")
                .then()
                .extract()
                .cookie(authCookieName);
        return authToken;
    }


    public void openPageWithAuth(FakerUser fakerUser) {
        open("/Themes/DefaultClean/Content/images/logo.png");
        Cookie authCookie = new Cookie(authCookieName, login(fakerUser));
        WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        open("");
        $(".account").shouldHave(text(fakerUser.email));
    }


    public void editAndCheckProfile(FakerUser fakerUserAfterEdit) {
        open("/customer/info");
        $("#FirstName").setValue(fakerUserAfterEdit.firstName);
        $("#LastName").setValue(fakerUserAfterEdit.lastName);
        $("#Email").setValue(fakerUserAfterEdit.email);
        $("[value='Save']").click();
        refresh();
        $("#FirstName").shouldHave(value(fakerUserAfterEdit.firstName));
        $("#LastName").shouldHave(value(fakerUserAfterEdit.lastName));
        $("#Email").shouldHave(value(fakerUserAfterEdit.email));
    }
}
