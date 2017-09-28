package com.example.rafae.booktracker.notifications

/**
 * Created by felguiras on 28/09/2017.
 */

class Stopwatch {
    private var startTime: Long = 0
    private var stopTime: Long = 0
    private var running = false


    fun start() {
        this.startTime = System.currentTimeMillis()
        this.running = true
    }


    fun stop() {
        this.stopTime = System.currentTimeMillis()
        this.running = false
    }


    // elaspsed time in milliseconds
    fun getElapsedTime(): Long {
        return if (running) {
            System.currentTimeMillis() - startTime
        } else stopTime - startTime
    }


    // elaspsed time in seconds
    fun getElapsedTimeSecs(): Long {
        return if (running) {
            (System.currentTimeMillis() - startTime) / 1000
        } else (stopTime - startTime) / 1000
    }

}