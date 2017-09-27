package com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by felguiras on 27/09/2017.
 */

@Root(name = "search", strict = false)
public class Work implements Serializable {

    @Element(name = "id")
    private int id;
    @Element(name = "books_count")
    private int booksCount;
    @Element(name = "ratings_count")
    private int ratings;
    @Element(name = "text_reviews_count")
    private int revsCount;
    @Element(name = "original_publication_day", required = false)
    private int pubDay;
    @Element(name = "original_publication_month", required = false)
    private int pubMonth;
    @Element(name = "original_publication_year", required = false)
    private int pubYear;
    @Element(name = "best_book")
    private Book bestBook;
    @Element(name = "average_rating")
    private float avgRating;

}
