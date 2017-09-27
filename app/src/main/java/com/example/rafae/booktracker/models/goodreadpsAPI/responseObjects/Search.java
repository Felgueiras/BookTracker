package com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by felguiras on 27/09/2017.
 */

@Root(name = "search", strict = false)
public class Search implements Serializable {

    @Element(name = "total-results")
    private int totalRes;
    @Element(name = "query")
    private String query;
    @Element(name = "results-start")
    private int resStart;
    @Element(name = "results-end")
    private int resEnd;
    @Element(name = "source")
    private String resSource;
    @Element(name = "query-time-seconds")
    private String querySeconds;
    @ElementList(name = "results")
    private List<Work> results;

}
