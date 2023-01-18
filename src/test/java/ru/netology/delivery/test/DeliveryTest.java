package ru.netology.delivery.test;



import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;


import java.time.Duration;


import static com.codeborne.selenide.Condition.*;

import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    String name = DataGenerator.generateName();
    String phone = DataGenerator.generatePhone();
    String city = DataGenerator.generateCity();


    @Test
    void shouldTest() {
        open("http://localhost:9999/");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".notification_status_ok").shouldBe(visible);
        $("[data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(3)));
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(5));
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(15));
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification]").shouldHave(text("Необходимо подтверждение"));
        $("[data-test-id=replan-notification]").$$("button").find(exactText("Перепланировать")).click();
        $(".notification_status_ok").shouldBe(visible);
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(5)));


    }
}



