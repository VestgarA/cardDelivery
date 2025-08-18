package ru.netology;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateGeneration {

    public String plus3Days() {
        LocalDate dateDay = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateMeeting = dateDay.format(formatter);
        return dateMeeting;
    }

    public String plus1Week() {
        LocalDate dateWeek = LocalDate.now().plusDays(10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateWeekMeeting = dateWeek.format(formatter);
        return dateWeekMeeting;
    }
}