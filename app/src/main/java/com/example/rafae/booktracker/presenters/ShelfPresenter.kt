package com.example.rafae.booktracker.presenters

import com.example.rafae.booktracker.BooksMVP
import com.example.rafae.booktracker.models.BooksModel
import com.example.rafae.booktracker.models.Note
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.objects.ReadingSessionDB
//import com.example.rafae.booktracker.objects.BookDB
import com.example.rafae.booktracker.views.BooksListView

import java.lang.ref.WeakReference
import java.util.Date

/**
 * Created by rafae on 24/09/2017.
 */
class ShelfPresenter(mView: BooksMVP.BooksListViewOps) : BooksMVP.RequiredPresenterOps, BooksMVP.PresenterOps {

    /**
     * Call Model to add a ReadingSessionDB.
     */
    override fun addReadingSession(readingSession: ReadingSessionDB) {
        mModel.addReadingSession(readingSession)
    }


    /**
     * Fetch user status update for this book.
     */
    override fun fetchStatusForBook(book: Book) {
        val m = mModel as BooksModel
        m.mPresenter = this as Object
        mModel.fetchStatusForBook(book)
    }


    // Layer View reference
    private var mView: WeakReference<BooksMVP.BooksListViewOps>? = null
    // Layer Model reference
    private val mModel: BooksMVP.ModelOps

    // Configuration change state
    private var mIsChangingConfig: Boolean = false

    private val date: Date? = null

    init {
        this.mView = WeakReference<BooksMVP.BooksListViewOps>(mView)
        // access model
        this.mModel = BooksModel.instance
        // set the current presenter
        mModel.mPresenter = this as Object
    }

    /**
     * Sent from Activity after a configuration changes
     *
     * @param view View reference
     */
    override fun onConfigurationChanged(view: BooksMVP.BooksListViewOps) {
        mView = WeakReference<BooksMVP.BooksListViewOps>(view)
    }

    /**
     * Receives [BooksListView.onDestroy] event
     *
     * @param isChangingConfig Config change state
     */
    override fun onDestroy(isChangingConfig: Boolean) {
        mView = null
        mIsChangingConfig = isChangingConfig
        if (!isChangingConfig) {
            mModel.onDestroy()
        }
    }

    /**
     * Called by user interaction from [BooksListView]
     * creates a new Note and sends it to the model.
     */
    override fun newBook(author: String, title: String, pages: Int) {
//        val book =  Book(stopPage, elapsedTime, getStopPage(), pages)
//        mModel.insertBook(book)
    }

    /**
     * Called from [BooksListView],
     * Removes a Note
     */
    override fun removeBook(note: Note) {
        mModel.removeNote(note)
    }

    override fun fetchBooks(shelf: String) {
        // register as current presenter
        val m = mModel as BooksModel
        m.mPresenter = this as Object
        mModel.fetchBooks(shelf)
    }

    /**
     * Called from [MainModel]
     * when a Note is inserted successfully
     */
    override fun onBookInserted(books: ArrayList<Book>) {
//        mView!!.get()!!.showToast("New register added at " + books.stopPage)
        mView!!.get()!!.newBookAdded(books)
    }

    /**
     * Receives call from [MainModel]
     * when Note is removed
     */
    override fun onNoteRemoved(noteRemoved: Note) {
        mView!!.get()!!.showToast("Note removed")
    }

    /**
     * receive errors
     */
    override fun onError(errorMsg: String) {
        mView!!.get()!!.showAlert(errorMsg)
    }

    /**
     * Get current Date.
     *
     * @return
     */
    private fun getDate(): Date {
        return Date()
    }
}
