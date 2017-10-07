package com.example.rafae.booktracker.objects

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.example.rafae.booktracker.helpers.TimeHelpers
import java.io.Serializable
import java.util.*

/**
 * Created by felguiras on 25/09/2017.
 */
@Entity(tableName = "reading_session")
class ReadingSessionDB(elapsedSeconds: Int, startPage: Int, currentPage: Int, bookTile: String,
                       startDate: Long) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var slNo: Int = 0

    var start: Long = startDate

    @Ignore
    var startTime: Date? = null

    @Ignore
    var stop: Date? = null

    var readingTime: Int = elapsedSeconds

    var startPage: Int? = startPage

    var endPage: Int? = currentPage

    var book: String? = bookTile

    constructor() : this(0, 0, 0, "", 0)

    override fun toString(): String {

        return TimeHelpers.getReadingSpeed(this).toString()
    }

    fun pagesRead(): Int {
        return endPage!! - startPage!!
    }


}