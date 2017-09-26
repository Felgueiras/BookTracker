package com.example.rafae.booktracker.models

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.rafae.booktracker.objects.Book

/**
 * Created by felguiras on 25/09/2017.
 */
@Database(entities = arrayOf(Book::class), version = 1)
abstract class SampleDatabase : RoomDatabase() {
    abstract fun daoAccess(): DaoAccess
}