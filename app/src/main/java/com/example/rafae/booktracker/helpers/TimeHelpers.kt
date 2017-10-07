package com.example.rafae.booktracker.helpers

import com.example.rafae.booktracker.objects.ReadingSessionDB

/**
 * Created by felgueiras on 06/10/2017.
 */

class TimeHelpers {

    companion object {
        /**
         * Get reading speed (in pages per minute)
         *
         * @param readingTime elapsed time in seconds
         * @param pagesRead
         * @return
         */
        fun getReadingSpeed(session: ReadingSessionDB): Float {
            // covert seconds to minutes
            val readingT: Float = session.readingTime * 1.0f / 60
            var readingSpeed = (session.endPage!! - session.startPage!!) / readingT
            readingSpeed = Math.round(readingSpeed * 100) / 100.toFloat()
            if (readingSpeed % 1 == 0f) {
                return Math.round(readingSpeed).toFloat()
            }
            return readingSpeed
        }

        /**
         * Get the average readng speed for a list of reading sessions.
         */
        fun getAverageReadingSpeed(sessions: List<ReadingSessionDB>): Float {
            var readingSpeed: Float = 0f
            for (session in sessions) {
                readingSpeed += TimeHelpers.getReadingSpeed(session)
            }

            return (Math.round(readingSpeed / sessions.size * 100) / 100).toFloat()
        }

    }
}
