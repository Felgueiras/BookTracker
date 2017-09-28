package com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by felguiras on 27/09/2017.
 */

@Root(name = "author", strict = false)
public class Book implements Serializable {

    @Element(name = "id")
    private int id;
    @Element(name = "title")
    private String title;
    @Element(name = "image_url")
    private String image_url;
    @Element(required = false)
    private String description;
    @Element(name = "type", required = false)
    private String type;
    @Element(name = "average_rating", required = false)
    private float avgRating;
    @ElementList(name = "authors", required = false)
    private List<Author> authors;
    @Element(name = "author", required = false)
    private Author author;
    @Element(name = "small_image_url")
    private String small_image_url;
    @Element(name = "publication_year", required = false)
    private String pubYear;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public Author getAuthor() {
        return author;
    }

    public String getSmall_image_url() {
        return small_image_url;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public String getPubYear() {

        return pubYear;
    }
}
