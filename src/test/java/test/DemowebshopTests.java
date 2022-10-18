package test;

import base.TestBase;
import data.FakerUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.DemowebshopPage;

public class DemowebshopTests extends TestBase {
    static FakerUser fakerUser = new FakerUser ();
    static FakerUser fakerUserAfterEdit = new FakerUser ();
    static DemowebshopPage demowebshopPage = new DemowebshopPage();

    @Test
    @DisplayName("RegistrationNewUser")
    public void registrationTest() {
        demowebshopPage.registration(fakerUser);
        demowebshopPage.login(fakerUser);
        demowebshopPage.openPageWithAuth(fakerUser);
    }

    @Test
    @DisplayName("ChangeUser")
    public void editProfileTest() {
        demowebshopPage.registration(fakerUser);
        demowebshopPage.login(fakerUser);
        demowebshopPage.openPageWithAuth(fakerUser);
        demowebshopPage.editAndCheckProfile(fakerUserAfterEdit);
    }
}
