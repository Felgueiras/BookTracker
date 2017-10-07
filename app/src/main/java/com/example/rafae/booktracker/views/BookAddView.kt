package com.example.rafae.booktracker.views

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.rafae.booktracker.BooksMVP
import com.example.rafae.booktracker.R
import com.example.rafae.booktracker.StateMaintainer
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.objects.BookDB
import com.example.rafae.booktracker.objects.ReadingSessionDB
import com.example.rafae.booktracker.presenters.ShelfPresenter
import java.util.*
import kotlin.collections.ArrayList

class BookAddView : AppCompatActivity(), BooksMVP.BooksListViewOps {


    override fun displayBooks(books: ArrayList<Book>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun newBookAddeda(books: ArrayList<BookDB>) {
        Toast.makeText(applicationContext, "BookDB added", Toast.LENGTH_SHORT).show()
        this.finish()
    }

    /**
     * Tag for logging.
     */
    protected val TAG = "BooksListView"

    // Responsible to maintain the Objects state
    // during changing configuration
    private val mStateMaintainer = StateMaintainer(fragmentManager, TAG)

    // Presenter operations
    private var mPresenter: BooksMVP.PresenterOps? = null

    // views
    @BindView(R.id.statusPage)
    lateinit var bookTitle: EditText
    @BindView(R.id.statusDate)
    lateinit var bookAuthor: EditText
    @BindView(R.id.numPages)
    lateinit var numPages: EditText
    @BindView(R.id.addBookBtn)
    lateinit var addBookBtn: Button


    lateinit var book: BookDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startMVPOps()
        setContentView(R.layout.book_add)

        ButterKnife.bind(this)
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
    private fun initialize(view: BooksMVP.BooksListViewOps) {
        mPresenter = ShelfPresenter(view)
        mStateMaintainer.put("TODO", mPresenter)
    }

    /**
     * Recovers Presenter and informs Presenter that occurred a config change.
     * If Presenter has been lost, recreates a instance
     */
    private fun reinitialize(view: BooksMVP.BooksListViewOps) {
        mPresenter = mStateMaintainer.get<BooksMVP.PresenterOps>("TODO")

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

    @OnClick(R.id.addBookBtn)
    fun startReadingBook(v: View) {

        val author = bookAuthor.text.toString()
        val title = bookTitle.text.toString()
        val pages = numPages.text.toString().toInt()

        // read EditText fields
        // TODO validate


        // call presenter
        mPresenter!!.newBook(author, title, pages)
    }

    /**
     * Finish reading session.
     */
    private fun finishReadingSession(book: BookDB) {
        // get current time
        val current = Date()

        // new reading session
        readingSess!!.stop = current

        // prompt how many pages were read
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Hey man, nice reading!")
        alertDialog.setMessage("In which elapsedTime are you?")


        val input = EditText(this)
        val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        input.layoutParams = lp
        alertDialog.setView(input)

//        alertDialog.setIcon(R.drawable.key)

        var currentP: Int

        alertDialog.setPositiveButton("YES"
        ) {

            dialog, which ->
            run {
                currentP = input.text.toString().toInt()
                dialog.cancel()
                showInfoOnScreen(currentP)
            }

        }

        alertDialog.setNegativeButton("NO"
        ) { dialog, which -> dialog.cancel() }

        alertDialog.show();
    }

    private fun showInfoOnScreen(currentP: Int) {
        // show info - time (start, stop, elapsed), pages read, reading pagesInfo
        book.currentPage += currentP

        Log.d("Current elapsedTime", book.currentPage.toString())


// check elapsed time

// prompt user about how many pages were read


        val diff: Long = readingSess!!.stop!!.time - readingSess!!.startTime!!.time
        val diffSeconds = diff / 1000 % 60
        val diffMinutes = diff / (60 * 1000) % 60
        val diffHours = diff / (60 * 60 * 1000)
        println("Time in seconds: $diffSeconds seconds.")
        println("Time in minutes: $diffMinutes minutes.")
        println("Time in hours: $diffHours hours.")

// add reading session to book
//        book.readingSessions!!.add(readingSess!!)
        Toast.makeText(this, "Elapsed time: $diffHours:$diffMinutes:$diffSeconds.", Toast.LENGTH_SHORT).show()
    }


    var readingSess: ReadingSessionDB? = null

    /**
     * Start reading session.
     */
    private fun addReadingSession(book: BookDB) {
        // get current time
        val current = Date()

        // new reading session
//        readingSess = ReadingSessionDB(current)
    }

    companion object {
        val BOOK = "BOOK"
    }
}