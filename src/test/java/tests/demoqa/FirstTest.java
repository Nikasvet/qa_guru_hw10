package tests.demoqa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import static java.lang.String.format;
import static config.Credentials.credentials;

import javax.swing.*;
import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class FirstTest {

    @BeforeAll
    static void setup() {

        String login = credentials.login();
        String password = credentials.password();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.browserCapabilities = capabilities;

        Configuration.startMaximized = true;
        Configuration.remote = format("https://%s:%s@" + System.getProperty("url"), login, password);
    }

    @AfterEach
    public void tearDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();

    }

    @Test
    void positiveFillTest() {
        open("https://demoqa.com/automation-practice-form");
        $("#firstName").setValue("Nika");
        $("#lastName").setValue("Nika");
        $("#userEmail").setValue("Nika@nike.ru");
        $("#genterWrapper").$(byText("Male")).click();
        $("#userNumber").setValue("9999999999");

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("November");
        $(".react-datepicker__year-select").selectOption("1991");
        $(".react-datepicker__day--011").click();

        $("#hobbiesWrapper").$(byText("Reading")).click();
        $("#uploadPicture").uploadFile(new File("src/test/resources/img/test.jpg"));
        $("#currentAddress").setValue("Testoviy pereulochek");
        $("#react-select-3-input").setValue("Haryana").pressEnter();
        $("#react-select-4-input").setValue("Karnal").pressEnter();
        $("#submit").scrollTo().click();

        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").shouldHave(text("Nika Nika"), text("Nika@nike.ru"), text("Male"),
                text("9999999999"), text("11 November,1991"), text("Testoviy pereulochek"),
                text("Reading"),  text("Haryana Karnal"), text("test.jpg"));

    }

    }

