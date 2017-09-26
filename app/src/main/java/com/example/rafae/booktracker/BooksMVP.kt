package com.example.rafae.booktracker

import com.example.rafae.booktracker.models.Note
import com.example.rafae.booktracker.objects.Book

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

    /**
     * Operations offered from View to View
     * View -> Presenter
     */
    interface PresenterOps {
        fun onConfigurationChanged(view: BooksListViewOps)

        fun onDestroy(isChangingConfig: Boolean)

        fun newBook(title: String, author: String, pages: Int)

        fun removeBook(nota: Note)

        fun fetchBooks()
    }

    /**
     * Operations offered from Presenter to Model
     * Model -> Presenter
     */
    interface RequiredPresenterOps {
        fun onBookInserted(novaNota: ArrayList<Book>)

        fun onNoteRemoved(notaRemovida: Note)

        fun onError(errorMsg: String)
        // Any other returning operation Model -> Presenter
    }

    /**
     * Model operations offered to Presenter
     * Presenter -> Model
     */
    interface ModelOps {
        fun insertBook(nota: Book)

        fun removeNote(nota: Note)

        fun onDestroy()

        fun fetchBooks()


    }
}