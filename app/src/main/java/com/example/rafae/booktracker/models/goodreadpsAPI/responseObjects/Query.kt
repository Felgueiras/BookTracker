package com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 * Created by rafae on 18/09/2017.
 */

@Root(name = "query")
class Query(@get:Element(name = "total-results") var totalres: Int,
             @get:Element var query: Query) {

    val res = totalres
    val q = query

}
