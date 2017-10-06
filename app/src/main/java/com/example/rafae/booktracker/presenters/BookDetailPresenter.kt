package com.example.rafae.booktracker.presenters

import com.example.rafae.booktracker.BooksMVP
import com.example.rafae.booktracker.models.BooksModel
import com.example.rafae.booktracker.models.Note
import com.example.rafae.booktracker.models.goodreadpsAPI.UserStatus
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.objects.ReadingSessionDB
import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by rafae on 24/09/2017.
 */
class BookDetailPresenter(mView: BooksMVP.BookDetailViewOps) : BooksMVP.RequiredBookDetailPresenterOps, BooksMVP.BookDetailPresenterOps {

    override fun onReadingSessionForBookRetrieved(sessions: List<ReadingSessionDB>) {
        mView!!.get()!!.onReadingSessionForBookRetrieved(sessions)
    }

    /**
     * Fetch reading sessions for a book.
     */
    override fun fetchReadingSessionsForBook(book: Book) {
        val m = mModel as BooksModel
        m.mPresenter = this as Object
        mModel.fetchReadingSessionsForBook(book)
    }

    /**
     * Give status updates to the view.
     */
    override fun onStatusForBookRetrieved(userStatusUpdates: MutableList<UserStatus>) {
        mView!!.get()!!.statusForBookretrieved(userStatusUpdates)

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
    private var mView: WeakReference<BooksMVP.BookDetailViewOps>? = null
    // Layer Model reference
    private val mModel: BooksMVP.ModelOps

    // Configuration change state
    private var mIsChangingConfig: Boolean = false

    private val date: Date? = null

    init {
        this.mView = WeakReference<BooksMVP.BookDetailViewOps>(mView)
        // access model
        this.mModel = BooksModel.instance
        // set the current presenter
//        mModel.mPresenter = this
    }

    /**
     * Sent from Activity after a configuration changes
     *
     * @param view View reference
     */
    override fun onConfigurationChanged(view: BooksMVP.BookDetailViewOps) {
        mView = WeakReference<BooksMVP.BookDetailViewOps>(view)
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
     * Called from [BooksListView],
     * Removes a Note
     */
    override fun removeBook(note: Note) {
        mModel.removeNote(note)
    }

    override fun fetchBooks(shelf: String) {
        // register as current presenter
        val m = mModel as BooksModel
//        m.mPresenter = this
        mModel.fetchBooks(shelf)
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