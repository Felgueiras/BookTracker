package com.example.rafae.booktracker.presenters;

import com.example.rafae.booktracker.MainMVP;
import com.example.rafae.booktracker.models.Note;
import com.example.rafae.booktracker.models.MainModel;
import com.example.rafae.booktracker.views.BooksListView;

import java.lang.ref.WeakReference;
import java.util.Date;

/**
 * Created by rafae on 24/09/2017.
 */
public class MainPresenter implements MainMVP.RequiredPresenterOps, MainMVP.PresenterOps {

    // Layer View reference
    private WeakReference<MainMVP.RequiredViewOps> mView;
    // Layer Model reference
    private MainMVP.ModelOps mModel;

    // Configuration change state
    private boolean mIsChangingConfig;

    private Date date;

    public MainPresenter(MainMVP.RequiredViewOps mView) {
        this.mView = new WeakReference<>(mView);
        this.mModel = new MainModel(this);
    }

    /**
     * Sent from Activity after a configuration changes
     *
     * @param view View reference
     */
    @Override
    public void onConfigurationChanged(MainMVP.RequiredViewOps view) {
        mView = new WeakReference<>(view);
    }

    /**
     * Receives {@link BooksListView#onDestroy()} event
     *
     * @param isChangingConfig Config change state
     */
    @Override
    public void onDestroy(boolean isChangingConfig) {
        mView = null;
        mIsChangingConfig = isChangingConfig;
        if (!isChangingConfig) {
            mModel.onDestroy();
        }
    }

    /**
     * Called by user interaction from {@link BooksListView}
     * creates a new Note and sends it to the model.
     */
    @Override
    public void newNote(String noteText) {
        Note note = new Note();
        note.setText(noteText);
        note.setDate(getDate());
        mModel.insertNote(note);
    }

    /**
     * Called from {@link BooksListView},
     * Removes a Note
     */
    @Override
    public void removeNote(Note note) {
        mModel.removeNote(note);
    }

    /**
     * Called from {@link MainModel}
     * when a Note is inserted successfully
     */
    @Override
    public void onNoteInserted(Note newNote) {
        // tell view to shot a Toast
        mView.get().showToast("New register added at " + newNote.getDate());
    }

    /**
     * Receives call from {@link MainModel}
     * when Note is removed
     */
    @Override
    public void onNoteRemoved(Note noteRemoved) {
        mView.get().showToast("Note removed");
    }

    /**
     * receive errors
     */
    @Override
    public void onError(String errorMsg) {
        mView.get().showAlert(errorMsg);
    }

    /**
     * Get current Date.
     *
     * @return
     */
    private Date getDate() {
        return new Date();
    }
}
