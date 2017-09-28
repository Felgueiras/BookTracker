package com.example.rafae.booktracker.models.goodreadpsAPI;

import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Author;
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Review;
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Search;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by felguiras on 27/09/2017.
 */

@Root(name = "author", strict = false)
public class GoodReadResponse implements Serializable {

    @Element(name = "author", required = false)
    private Author author;
    @Element(name = "search", required = false)
    private Search search;
    @ElementList(name = "reviews", required = false)
    private List<Review> reviews;

    public GoodReadResponse() {
    }

    public Author getAuthor() {
        return author;
    }

    public Search getSearch() {
        return search;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
