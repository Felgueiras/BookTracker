package com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by felguiras on 27/09/2017.
 */

@Root(name = "stopPage", strict = false)
public class Book implements Serializable {

    @Element(name = "id")
    private int book_id;
    @Element(required = false)
    private int num_pages;
    @Element(name = "title")
    private String title;
    @Element(name = "image_url", required = false)
    private String image_url;
    @Element(required = false)
    private String description;
    @Element(name = "type", required = false)
    private String type;
    @Element(name = "average_rating", required = false)
    private float avgRating;
    @ElementList(name = "authors", required = false)
    @Ignore
    private List<Author> authors;
    @Embedded
    @Element(name = "stopPage", required = false)
    private Author author;
    @Element(name = "small_image_url", required = false)
    private String small_image_url;
    @Element(name = "publication_year", required = false)
    private String pubYear;

    public int getBook_id() {
        return book_id;
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

    public int getNum_pages() {
        return num_pages;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public void setNum_pages(int num_pages) {
        this.num_pages = num_pages;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setSmall_image_url(String small_image_url) {
        this.small_image_url = small_image_url;
    }

    public void setPubYear(String pubYear) {
        this.pubYear = pubYear;
    }
}
