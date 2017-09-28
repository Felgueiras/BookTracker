package com.example.rafae.booktracker.models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.rafae.booktracker.objects.BookDB;

import java.util.List;

/**
 * Created by felgueiras on 21/09/2017.
 */

@Dao
public interface DaoAccess {

    @Insert
    void insertMultipleRecord(BookDB... books);

    @Insert
    void insertMultipleListRecord(List<BookDB> universities);

    @Insert
    void insertOnlySingleRecord(BookDB book);

    @Query("SELECT * FROM Book")
    LiveData<List<BookDB>> fetchAllData();

//    @Query("SELECT * FROM BookDB WHERE clgid =:college_id")
//    LiveData<BookDB> getSingleRecord(int college_id);

    @Update
    void updateRecord(BookDB university);

    @Delete
    void deleteRecord(BookDB university);
}
