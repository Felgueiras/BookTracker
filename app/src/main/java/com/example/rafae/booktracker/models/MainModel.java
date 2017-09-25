package com.example.rafae.booktracker.models;

/**
 * Created by rafae on 24/09/2017.
 */

import com.example.rafae.booktracker.MainMVP;
import com.example.rafae.booktracker.presenters.MainPresenter;

/**
 * Implements operations that Presenter executes on it.
 * Presenter -> Model
 */
public class MainModel implements MainMVP.ModelOps {

    // Presenter reference
    private MainMVP.RequiredPresenterOps mPresenter;

    public MainModel(MainMVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }

    /**
     * Sent from {@link MainPresenter#onDestroy(boolean)}
     * Should stop/kill operations that could be running
     * and aren't needed anymore
     */
    @Override
    public void onDestroy() {
        // destroying actions
    }

    // Insert Note in DB
    @Override
    public void insertNote(Note note) {
        // TODO data business logic
        // tell presenter that note was inserted
        mPresenter.onNoteInserted(note);
    }

    // Removes Note from DB
    @Override
    public void removeNote(Note note) {
        // data business logic
        // ...
        mPresenter.onNoteRemoved(note);
    }
}