package com.example.rafae.booktracker.models.goodreadpsAPI;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by felgueiras on 29/09/2017.
 */

@Entity(tableName = "user_status")
@Root(name = "user_status", strict = false)
public class UserStatus implements Serializable {

    @Element(required = false)
    private int page ;
    @PrimaryKey()
    @Element(required = false, name = "id")
    private int user_status_id;
    @Element(required = false)
    private int percent;
    @Embedded
    @Element(required = false)
    private Book book;
    @Ignore
    @ElementList(required = false, name = "user_status", inline = true)
    public List<UserStatus> user_status;
//    @Element(inline=true, type=String.class, name="Author", required = false)
//    public List<String> authors;

    public UserStatus(){

    }

    public int getPage() {
        return page;
    }

    public int getPercent() {
        return percent;
    }

    public Book getBook() {
        return book;
    }


    public List<UserStatus> getUser_status() {
        return user_status;
    }

    @Override
    public String toString() {

        return "ID: " + user_status_id + "; elapsedTime: " + page +"; percent:" + percent+"%; numpages: " + book.getNum_pages();
    }

    public void setPage(int page) {
        this.page = page;
    }


    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setUser_status(List<UserStatus> user_status) {
        this.user_status = user_status;
    }

    public int getUser_status_id() {
        return user_status_id;
    }

    public void setUser_status_id(int user_status_id) {
        this.user_status_id = user_status_id;
    }
}

