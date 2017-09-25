package com.example.rafae.booktracker.objects

import java.io.Serializable
import java.util.Date

/**
 * Created by rafae on 25/09/2017.
 */

class Book(author: String, title: String) : Serializable {

    internal var title: String? = title

    internal var author: String? = author

    internal var dateAdded: Date? = null

    internal var numPages: Int = 100

    internal var currentPage: Int = 50

    internal var reading: Boolean = false

    internal var readingSessions: ArrayList<ReadingSession>? = null
}
