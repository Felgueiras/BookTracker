package com.example.rafae.booktracker.objects

import java.io.Serializable
import java.util.*

/**
 * Created by felguiras on 25/09/2017.
 */
class ReadingSession(start: Date) : Serializable {

    internal var start: Date? = start

    internal var stop: Date? = null

    internal var pagesRead: Int? = 0


}