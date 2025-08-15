package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.lang.Object;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static java.awt.SystemColor.menu;
import static java.lang.String.format;
import static java.time.LocalDate.now;

public class CardDeliveryTest {
    LocalDate date = LocalDate.now().plusDays(3);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String dateMeeting = date.format(formatter);

    LocalDate dateWeek = LocalDate.now().plusWeeks(1);
    DateTimeFormatter formatt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String dateWeekMeeting = dateWeek.format(formatter);

    //Задача №1: заказ доставки карты
    @Test
    public void successfulAppointmentBooking() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $(Selectors.withText("Встреча успешно забронирована на"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void notFilledCity() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition
                .text("Поле обязательно для заполнения"));
    }

    @Test
    public void notFilledСalendar() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(Condition
                .text("Неверно введена дата"));
    }

    @Test
    public void notFilledName() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition
                .text("Поле обязательно для заполнения"));
    }

    @Test
    public void notFilledMobilePhone() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition
                .text("Поле обязательно для заполнения"));
    }

    @Test
    public void notInstalledCheckbox() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldHave(Condition
                .text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    public void invalidFormatCity() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Moskov");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition
                .text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void invalidFormatName() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id='name'] input").setValue("Ivan Ivanov");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition
                .text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void invalidFormatNumberPhone() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition
                .text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

// Задача №2: взаимодействие с комплексными элементами

    @Test
    public void selectionCityFromList() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Мо");
        $(".popup .menu :nth-child(3)").click();
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $(Selectors.withText("Встреча успешно забронирована на"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void ChoosingDateForWeekAhead() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateWeekMeeting);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $(Selectors.withText("Встреча успешно забронирована на"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));

    }
}