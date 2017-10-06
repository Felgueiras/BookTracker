package com.example.rafae.booktracker

import com.example.rafae.booktracker.models.Note
import com.example.rafae.booktracker.models.goodreadpsAPI.UserStatus
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.objects.BookDB
import com.example.rafae.booktracker.objects.ReadingSessionDB

/**
 * Created by rafae on 24/09/2017.
 */

/*
 * Aggregates all communication operations between MVP pattern layer:
 * Model, View and Presenter
 */
interface BooksMVP {

    /**
     * View mandatory methods. Available to Presenter
     * Presenter -> BooksListView
     */
    interface BooksListViewOps {
        fun showToast(msg: String)

        fun newBookAdded(books: ArrayList<Book>)

        fun showAlert(msg: String)
    }

    interface BookDetailViewOps {
        fun showToast(msg: String)


        fun showAlert(msg: String)

        fun statusForBookretrieved(userStatusUpdates: MutableList<UserStatus>)

        fun onReadingSessionForBookRetrieved(sessions: List<ReadingSessionDB>)

    }

    /**
     * Operations offered from View to View
     * View -> Presenter
     */
    interface PresenterOps {
        fun onConfigurationChanged(view: BooksListViewOps)

        fun onDestroy(isChangingConfig: Boolean)

        fun newBook(title: String, author: String, pages: Int)

        fun removeBook(nota: Note)

        fun fetchBooks(shelf: String)

        fun fetchStatusForBook(book: Book)

        fun addReadingSession(readingSession: ReadingSessionDB)


    }

    interface BookDetailPresenterOps {
        fun onConfigurationChanged(view: BookDetailViewOps)

        fun onDestroy(isChangingConfig: Boolean)


        fun removeBook(nota: Note)

        fun fetchBooks(shelf: String)

        fun fetchStatusForBook(book: Book)

        fun fetchReadingSessionsForBook(book:Book)
    }

    /**
     * Operations offered from Presenter to Model
     * Model -> Presenter
     */
    interface RequiredPresenterOps {
        fun onBookInserted(novaNota: ArrayList<Book>)

        fun onNoteRemoved(notaRemovida: Note)

        fun onError(errorMsg: String)

    }

    interface RequiredBookDetailPresenterOps {

        fun onError(errorMsg: String)

        fun onStatusForBookRetrieved(userStatusUpdates: MutableList<UserStatus>)

        fun onReadingSessionForBookRetrieved(sessions: List<ReadingSessionDB>)
    }

    /**
     * Model operations offered to Presenter
     * Presenter -> Model
     */
    interface ModelOps {
        fun insertBook(nota: Book)

        fun removeNote(nota: Note)

        fun onDestroy()

        fun fetchBooks(shelf: String)

        fun fetchStatusForBook(book: Book)

        fun addReadingSession(readingSession: ReadingSessionDB)

        fun fetchReadingSessionsForBook(book:Book)


    }
}