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
 * Created by felgueiras on 29/09/2017.
 */

@Root(name = "user", strict = false)
public class User implements Serializable {

    @Element
    private int id;
    @Element
    private String name;
    @Element
    private int age;
    @ElementList
    private List<Update> updates ;

    User(){

    }

    public User(int id, String name, int age, List<Update> updates) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.updates = updates;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<Update> getUpdates() {
        return updates;
    }
}
