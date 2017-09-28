package com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by felguiras on 27/09/2017.
 */

@Root(name = "review", strict = false)
public class Review implements Serializable {

    @Element
    private int id;
    @Element
    private Book book;
    @Element
    private int rating;
    @Element
    private int votes;
    @Element
    private String url;
    @Element(name = "started_at")
    private String startingDate;
    @Element(name = "date_added")
    private String dateAdded;

    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public int getRating() {
        return rating;
    }

    public int getVotes() {
        return votes;
    }

    public String getUrl() {
        return url;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public String getDateAdded() {
        return dateAdded;
    }
}
