package com.example.rafae.booktracker.helpers

import android.util.Log
import com.example.rafae.booktracker.objects.ReadingSessionDB

/**
 * Created by felgueiras on 06/10/2017.
 */

class ReadingSessionHelpers {

    companion object {


        /**
         * Get the fastest reading session.
         */
        fun getFastestReadingSession(sessions: List<ReadingSessionDB>): ReadingSessionDB {
            var readingSpeedMax: Float = 0f
            var sess: ReadingSessionDB? = null
            for (session in sessions) {
                if (TimeHelpers.getReadingSpeed(session) > readingSpeedMax) {
                    readingSpeedMax = TimeHelpers.getReadingSpeed(session)
                    sess = session
                }

            }

            return sess!!
        }

        /**
         * Get the slowest reading session
         */
        fun getSlowestReadingSession(sessions: List<ReadingSessionDB>): ReadingSessionDB {
            var readingSpeedMin: Float = Float.MAX_VALUE
            var sess: ReadingSessionDB? = null

            for (session in sessions) {
                if (TimeHelpers.getReadingSpeed(session) < readingSpeedMin) {
                    readingSpeedMin = TimeHelpers.getReadingSpeed(session)
                    sess = session
                }
            }

            return sess!!
        }

        fun getLongestReadingSession(sessions: List<ReadingSessionDB>): ReadingSessionDB {
            var maxTime: Int = 0
            var sess: ReadingSessionDB? = null
            for (session in sessions) {
                if (session.readingTime > maxTime) {
                    maxTime = session.readingTime
                    sess = session
                }

            }

            return sess!!
        }

        fun getShortestReadingSession(sessions: List<ReadingSessionDB>): ReadingSessionDB {
            var minTime: Int = Int.MAX_VALUE
            var sess: ReadingSessionDB? = null
            for (session in sessions) {
                if (session.readingTime < minTime) {
                    minTime = session.readingTime
                    sess = session
                }
            }

            return sess!!
        }

        fun getMorePagesReadReadingSession(sessions: List<ReadingSessionDB>): ReadingSessionDB {
            var maxPages: Int = 0
            var sess: ReadingSessionDB? = null
            for (session in sessions) {
                if (session.pagesRead() > maxPages) {
                    maxPages = session.pagesRead()
                    sess = session
                }

            }

            return sess!!
        }

        fun getLessPagesReadReadingSession(sessions: List<ReadingSessionDB>): ReadingSessionDB {
            var minTime: Int = Int.MAX_VALUE
            var sess: ReadingSessionDB? = null
            for (session in sessions) {
                if (session.pagesRead() < minTime) {
                    minTime = session.pagesRead()
                    sess = session
                }
            }

            return sess!!
        }

    }
}