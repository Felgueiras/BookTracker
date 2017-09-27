package com.example.rafae.booktracker.models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.rafae.booktracker.objects.Book;

import java.util.List;

/**
 * Created by felgueiras on 21/09/2017.
 */

@Dao
public interface DaoAccess {

    @Insert
    void insertMultipleRecord(Book... books);

    @Insert
    void insertMultipleListRecord(List<Book> universities);

    @Insert
    void insertOnlySingleRecord(Book book);

    @Query("SELECT * FROM Book")
    LiveData<List<Book>> fetchAllData();

//    @Query("SELECT * FROM Book WHERE clgid =:college_id")
//    LiveData<Book> getSingleRecord(int college_id);

    @Update
    void updateRecord(Book university);

    @Delete
    void deleteRecord(Book university);
}
