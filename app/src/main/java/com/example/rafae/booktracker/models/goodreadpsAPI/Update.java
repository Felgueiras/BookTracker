package com.example.rafae.booktracker.models.goodreadpsAPI;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by felgueiras on 29/09/2017.
 */

@Root(name = "update", strict = false)
public class Update implements Serializable {

    @Attribute
    private String type;

    @Element
    private String action_text;
    @Element
    private String link;
    @Element
    private String image_url;
    @Element
    private UpdateObject object ;

    Update(){

    }

    public String getType() {
        return type;
    }

    public String getAction_text() {
        return action_text;
    }

    public String getLink() {
        return link;
    }

    public String getImage_url() {
        return image_url;
    }

    public UpdateObject getObject() {
        return object;
    }
}
