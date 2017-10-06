package com.example.rafae.booktracker.models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.rafae.booktracker.models.goodreadpsAPI.UserStatus;
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book;
import com.example.rafae.booktracker.objects.ReadingSessionDB;

import java.util.List;

/**
 * Created by felgueiras on 21/09/2017.
 */

@Dao
public interface DaoAccess {

//    @Insert
//    void insertMultipleRecord(BookDB... books);

    @Insert
    void insertMultipleListRecord(List<UserStatus> statuses);

    @Insert
    void insertOnlySingleRecord(UserStatus status);

    @Query("SELECT * FROM user_status")
    LiveData<List<UserStatus>> fetchAllData();

    @Query("SELECT * FROM user_status WHERE user_status_id =:statusID")
    LiveData<UserStatus> getSingleRecord(int statusID);

//    @Query("SELECT * FROM user_status WHERE book_id =:statusID")
//    LiveData<UserStatus> getStatusForBook(Book book);

    @Update
    void updateRecord(UserStatus university);

    @Delete
    void deleteRecord(UserStatus university);


    // Reading Sessions

    @Insert
    void insertReadingSession(ReadingSessionDB sess);

    @Query("SELECT * FROM reading_session")
    LiveData<List<ReadingSessionDB>> fetchAllReadingSessions();

    @Query("SELECT * FROM reading_session WHERE book =:bookTitle")
    LiveData<List<ReadingSessionDB>> fetchReadingSessionsForBook(String bookTitle);

    @Delete
    void deleteReadingSession(ReadingSessionDB sess);
}
