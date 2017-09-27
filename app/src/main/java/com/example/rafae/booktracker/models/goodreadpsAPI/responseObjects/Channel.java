package com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rafae on 18/09/2017.
 */

@Root(name = "channel", strict = false)
public class Channel implements Serializable {
    @ElementList(inline = true, name="item")
    private List<Work> mFeedItems;

    public List<Work> getFeedItems() {
        return mFeedItems;
    }

    public Channel() {
    }

    public Channel(List<Work> mFeedItems) {
        this.mFeedItems = mFeedItems;
    }
}
