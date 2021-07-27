package ru.gb.shipitsina.myapplication2;

import java.util.Calendar;

public class Note {
    private String title;
    private String content;
    private Calendar  calendar;

    public Note() {
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
