package com.example.rafae.booktracker.models.goodreadpsAPI;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by felgueiras on 29/09/2017.
 */

@Root(name = "object", strict = false)
public class UpdateObject implements Serializable {

    @Element(required = false)
    private UserStatus user_status ;

    UpdateObject(){

    }

    public UserStatus getUser_status() {
        return user_status;
    }
}
