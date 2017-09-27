package com.example.rafae.booktracker.models.goodreadpsAPI;

import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Author;
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Search;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by felguiras on 27/09/2017.
 */

@Root(name = "author", strict = false)
public class GoodReadResponse implements Serializable {

    @Element(name = "author", required = false)
    private Author author;
    @Element(name = "search", required = false)
    private Search search;

    public Author getmChannel() {
        return author;
    }

    public GoodReadResponse() {
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public GoodReadResponse(Author mChannel) {
        this.author = mChannel;
    }
}
