package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.lang.Object;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static java.awt.SystemColor.menu;
import static java.awt.SystemColor.text;
import static java.lang.String.format;
import static java.time.LocalDate.now;

public class CardDeliveryTest {
    DateGeneration generation = new DateGeneration();

    @BeforeEach
    public void openPage() {
        Selenide.open("http://localhost:9999");
    }

    //Задача №1: заказ доставки карты
    @Test
    public void successfulAppointmentBooking() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generation.plus3Days());
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + generation.plus3Days()));

    }

    @Test
    public void notFilledCity() {
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generation.plus3Days());
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(Condition.visible);
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void notFilledСalendar() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $(".input_invalid .input__sub").shouldBe(Condition.visible);
        $(".input_invalid .input__sub").shouldHave(text("Неверно введена дата"));
    }

    @Test
    public void notFilledName() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generation.plus3Days());
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(Condition.visible);
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void notFilledMobilePhone() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generation.plus3Days());
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(Condition.visible);
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void notInstalledCheckbox() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generation.plus3Days());
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldBe(Condition.visible);
        $("[data-test-id='agreement'].input_invalid .checkbox__text")
                .shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    public void invalidFormatCity() {
        $("[data-test-id='city'] input").setValue("Moskov");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generation.plus3Days());
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(Condition.visible);
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void invalidFormatName() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generation.plus3Days());
        $("[data-test-id='name'] input").setValue("Ivan Ivanov");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(Condition.visible);
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void invalidFormatNumberPhone() {
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generation.plus3Days());
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(Condition.visible);
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

// Задача №2: взаимодействие с комплексными элементами

    @Test
    public void selectionCityFromList() {
        $("[data-test-id='city'] input").setValue("Мо");
        $(Selectors.withText("Москва")).click();
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generation.plus3Days());
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + generation.plus3Days()));
    }

    @Test
    public void choosingDateForWeekAhead() {
        $("[data-test-id='date'] .input__icon").click();
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generation.plus1Week());
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79500000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + generation.plus1Week()));
    }
}