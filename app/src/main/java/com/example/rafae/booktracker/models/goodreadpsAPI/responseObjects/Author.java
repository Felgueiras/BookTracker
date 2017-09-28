package com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by felguiras on 27/09/2017.
 */

@Root(name = "author", strict = false)
public class Author implements Serializable {

    @Element(name = "name")
    private String name;

    public String getmChannel() {
        return name;
    }

    public Author() {
    }

    public Author(String mChannel) {
        this.name = mChannel;
    }

    public String getName() {
        return name;
    }


}
