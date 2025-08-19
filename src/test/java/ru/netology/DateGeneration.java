package ru.netology;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateGeneration {

    public String plusDays(int day) {
        LocalDate dateDay = LocalDate.now().plusDays(day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateMeeting = dateDay.format(formatter);
        return dateMeeting;
    }
}
