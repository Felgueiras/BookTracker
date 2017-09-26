package com.example.rafae.booktracker.models

/**
 * Created by rafae on 24/09/2017.
 */

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.rafae.booktracker.BooksMVP
import com.example.rafae.booktracker.daggerExample.DaggerApplication
import com.example.rafae.booktracker.objects.Book
import javax.inject.Singleton


/**
 * Implements operations that Presenter executes on it.
 * Presenter -> Model
 */
@Singleton
class BooksModel private constructor() : BooksMVP.ModelOps {

    /**
     * - private constructor is used to ensure this class can’t be initialized anywhere except inside of this class.
     * - init will be called when this class is initialized the first time i.e. when Singleton.instance called the very first.
     * -  Holder object & lazy instance is used to ensure only one instance of Singleton is created.
     */

    init { println("This ($this) is a singleton") }

    private object Holder { val INSTANCE = BooksModel() }

    companion object {
        val instance: BooksModel by lazy { Holder.INSTANCE }
    }

    // Presenter reference
    var mPresenter:BooksMVP.RequiredPresenterOps? = null


//    private val sampleDatabase: SampleDatabase

    private val context: Context

    init {

        // fetch context
        val vehicleComponent = DaggerApplication.getComponent()
        val vehicle = vehicleComponent.provideVehicle()
        context = vehicle.context

        Toast.makeText(context, "SPEED", Toast.LENGTH_SHORT).show()

        // fetch data
//        DatabaseAsync().execute();

    }

    private inner class DatabaseAsync : AsyncTask<Void, Void, Void>() {

        override fun onPreExecute() {
            super.onPreExecute()

            //Perform pre-adding operation here.
        }

        override fun doInBackground(vararg voids: Void): Void? {
            //Let's add some dummy data to the database.
            val book = Book()
            book.title = "Booky"
            book.author = "Me!"

            Log.d("Books","fetched data!")

            return null
        }

        override fun onPostExecute(aVoid: Void) {
            super.onPostExecute(aVoid)

            //To after addition operation here.
        }
    }


    override fun fetchBooks() {
        mPresenter!!.onBookInserted(booksList)
    }


    // TODO use Room
    var booksList: ArrayList<Book> = ArrayList()

    /**
     * Sent from [MainPresenter.onDestroy]
     * Should stop/kill operations that could be running
     * and aren't needed anymore
     */
    override fun onDestroy() {
        // destroying actions
    }

    /**
     * Add a new Book.
     */
    override fun insertBook(book: Book) {
        // insert book
        booksList.add(book)
        mPresenter!!.onBookInserted(booksList)
    }

    // Removes Note from DB
    override fun removeNote(note: Note) {
        // data business logic
        // ...
        mPresenter!!.onNoteRemoved(note)
    }
}