package com.example.rafae.booktracker.views

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.rafae.booktracker.R
import com.example.rafae.booktracker.StateMaintainer
import com.example.rafae.booktracker.objects.Book
import com.example.rafae.booktracker.objects.ReadingSession
import java.util.*
import android.widget.LinearLayout
import android.widget.EditText
import com.example.rafae.booktracker.BooksMVP
import com.example.rafae.booktracker.presenters.BooksListPresenter
import kotlin.collections.ArrayList


class BookSingleView : AppCompatActivity(), BooksMVP.BooksListViewOps {
    override fun newBookAdded(books: ArrayList<Book>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
    @BindView(R.id.bookTitle)
    lateinit var bookTitle: TextView
    @BindView(R.id.bookAuthor)
    lateinit var bookAuthor: TextView
    @BindView(R.id.bookDateAdded)
    lateinit var bookDateAdded: TextView
    @BindView(R.id.bookStartReading)
    lateinit var bookStartReading: Button


    lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startMVPOps()
        setContentView(R.layout.books_single_detail)

        ButterKnife.bind(this)

        // fetch book
        // get intent
        val extras = this.intent.getExtras();
        if (extras == null) {
            return;
        }
        // get data via the key
        book = extras.getSerializable(BOOK) as Book

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
        mPresenter = BooksListPresenter(view)
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

    @OnClick(R.id.bookStartReading)
    fun startReadingBook(v: View) {

        val btn = v as Button

        book.reading = !book.reading
        if (book.reading) {
            Log.d("Books", "start reading")
            //
            Log.d("Books", book.currentPage.toString())
            btn.text = "Stop"

            addReadingSession(book)
        } else {
            Log.d("Books", "stopped reading")
            btn.text = "Start"
            finishReadingSession(book)
        }

    }

    /**
     * Finish reading session.
     */
    private fun finishReadingSession(book: Book) {
        // get current time
        val current = Date()

        // new reading session
        readingSess!!.stop = current

        // prompt how many pages were read
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Hey man, nice reading!")
        alertDialog.setMessage("In which page are you?")


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
        // show info - time (start, stop, elapsed), pages read, reading percentage
        book.currentPage += currentP

        Log.d("Current page", book.currentPage.toString())


// check elapsed time

// prompt user about how many pages were read


        val diff: Long = readingSess!!.stop!!.time - readingSess!!.start!!.time
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


    var readingSess: ReadingSession? = null

    /**
     * Start reading session.
     */
    private fun addReadingSession(book: Book) {
        // get current time
        val current = Date()

        // new reading session
        readingSess = ReadingSession(current)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }


    companion object {
        val BOOK = "BOOK"
    }
}