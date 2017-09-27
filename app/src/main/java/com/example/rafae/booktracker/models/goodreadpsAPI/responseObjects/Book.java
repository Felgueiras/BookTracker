package com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

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
    @Element(name = "type", required = false)
    private String type;
    @Element(name = "author")
    private Author author;
    @Element(name = "small_image_url")
    private String small_image_url;

}
