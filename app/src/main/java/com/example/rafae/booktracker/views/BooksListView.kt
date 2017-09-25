package com.example.rafae.booktracker.views

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.rafae.booktracker.*
import com.example.rafae.booktracker.objects.Book
import com.example.rafae.booktracker.presenters.MainPresenter

class BooksListView : AppCompatActivity(), MainMVP.RequiredViewOps {

    /**
     * Tag for logging.
     */
    protected val TAG = "BooksListView"

    // Responsible to maintain the Objects state
    // during changing configuration
    private val mStateMaintainer = StateMaintainer(this.fragmentManager, TAG)

    // Presenter operations
    private var mPresenter: MainMVP.PresenterOps? = null

    // views
    @BindView(R.id.booksList)
    lateinit var booksList: RecyclerView
    @BindView(R.id.fabAddBook)
    lateinit var addBook: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startMVPOps()
        setContentView(R.layout.books_list)

        ButterKnife.bind(this)

        // TODO fetch books list
        val books = ArrayList<Book>()
        var book: Book = Book("Myself", "Hello world!")
        books.add(book)

        val adapter = BooksListAdapter(books, applicationContext)

        booksList = findViewById(R.id.booksList)
        booksList.layoutManager = LinearLayoutManager(this)
        booksList.adapter = adapter
    }

    /**
     * Handle clicking on button to submit new note.
     *
     * @param v
     */
    @OnClick(R.id.fabAddBook)
    fun addBookClicked(v: View) {

        //

    }


    /**
     * Initialize and restart the Presenter.
     * This method should be called after { Activity#onCreate(Bundle)}
     */
    fun startMVPOps() {
        try {
            if (mStateMaintainer.firstTimeIn()) {
                Log.d(TAG, "onCreate() called for the first time")
                initialize(this)
            } else {
                Log.d(TAG, "onCreate() called more than once")
                reinitialize(this)
            }
        } catch (e: Exception) {
            Log.d(TAG, "onCreate() " + e)
            throw RuntimeException(e)
        }

    }


    /**
     * Initialize relevant MVP Objects.
     * Creates a Presenter instance, saves the presenter in [StateMaintainer]
     */
    private fun initialize(view: MainMVP.RequiredViewOps) {
        mPresenter = MainPresenter(view)
        mStateMaintainer.put("TODO", mPresenter)
    }

    /**
     * Recovers Presenter and informs Presenter that occurred a config change.
     * If Presenter has been lost, recreates a instance
     */
    private fun reinitialize(view: MainMVP.RequiredViewOps) {
        mPresenter = mStateMaintainer.get<MainMVP.PresenterOps>("TODO")

        if (mPresenter == null) {
            Log.w(TAG, "recreating Presenter")
            initialize(view)
        } else {
            mPresenter!!.onConfigurationChanged(view)
        }
    }


    // Show AlertDialog
    override fun showAlert(msg: String) {
        // show alert Box
    }

    // Show Toast
    override fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }


}