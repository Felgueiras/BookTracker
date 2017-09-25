package com.example.rafae.booktracker.models;

import java.util.Date;

/**
 * Created by rafae on 24/09/2017.
 */

public class Note {

    String text;

    Date date;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
