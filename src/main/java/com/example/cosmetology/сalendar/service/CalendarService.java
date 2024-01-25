package com.example.cosmetology.сalendar.service;

import com.example.cosmetology.сalendar.model.Calendar;

import java.util.List;

public interface CalendarService {
    void calendarAdd(String firstname, String name, String lastname, String number, String date, String time);
    List<Calendar> caledarSelectDate(String date);
    List<Calendar> caledarSelectName(String firstname, String name);
    void calendarDelete(long id);

    List<Calendar> nextDate(String date);
    List<Calendar> backDate(String date);
}
