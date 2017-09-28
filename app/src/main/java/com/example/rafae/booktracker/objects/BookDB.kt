package com.example.rafae.booktracker.objects

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
import java.util.Date

/**
 * Created by rafae on 25/09/2017.
 */
@Entity(tableName = "book")
class BookDB(author: String, title: String, date: Date, pages: Int) : Serializable {

    constructor() : this("", "", Date(), 0)


    @PrimaryKey(autoGenerate = true)
    var slNo: Int = 0

    var title: String? = title

    var author: String? = author

    @Ignore
    var dateAdded: Date? = date

    var numPages: Int = pages

    var currentPage: Int = 50

    @Ignore
    var reading: Boolean = false
    @Ignore
    var readingSessions: ArrayList<ReadingSession>? = null


}
