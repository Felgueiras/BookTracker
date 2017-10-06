package com.example.rafae.booktracker.models

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.rafae.booktracker.models.goodreadpsAPI.UserStatus
import com.example.rafae.booktracker.objects.BookDB
import com.example.rafae.booktracker.objects.ReadingSessionDB

/**
 * Created by felguiras on 25/09/2017.
 */
@Database(entities = arrayOf(UserStatus::class, ReadingSessionDB::class), version = 2)
abstract class SampleDatabase : RoomDatabase() {
    abstract fun daoAccess(): DaoAccess
}