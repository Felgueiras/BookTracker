package com.example.rafae.booktracker.helpers

/**
 * Created by felgueiras on 06/10/2017.
 */

class TimeHelpers {

    companion object {
        /**
         * Get reading speed (in pages per second)
         *
         * @param readingTime
         * @param pagesRead
         * @return
         */
        fun getReadingSpeed(readingTime: Int, pagesRead: Int): Float {
            return (pagesRead / readingTime).toFloat()
        }
    }


}
