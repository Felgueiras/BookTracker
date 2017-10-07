package com.example.rafae.booktracker.views.BookReadingSession

import android.app.Dialog
import android.app.Fragment
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.rafae.booktracker.BooksMVP
import com.example.rafae.booktracker.R
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.notifications.BookStopwatchService
import com.example.rafae.booktracker.notifications.messages.MessageEvent
import com.example.rafae.booktracker.objects.BookDB
import com.example.rafae.booktracker.objects.ReadingSessionDB
import com.example.rafae.booktracker.presenters.ShelfPresenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class BookUndergoReadingSessionView : Fragment(), BooksMVP.BooksListViewOps, ServiceConnection {


    // views
    @BindView(R.id.statusPage)
    lateinit var bookTitle: TextView
    @BindView(R.id.bookDateAdded)
    lateinit var bookDateAdded: TextView
    @BindView(R.id.statusDate)
    lateinit var bookAuthor: TextView
    @BindView(R.id.startStopText)
    lateinit var startStopText: TextView


    //    @BindView(R.id.bookStartReading)
//    lateinit var bookStartReading: Button
    @BindView(R.id.counterValue)
    lateinit var counterValue: TextView

    override fun showToast(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun newBookAdded(books: ArrayList<Book>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun newBookAddeda(books: ArrayList<BookDB>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (container != null) {
//            container.removeAllViews()
        }

        // get book

        val rootView = inflater!!.inflate(R.layout.books_detail_counter, container, false)

        ButterKnife.bind(this, rootView)
        // get book from arguments
        var args = arguments
        // get data via the key
        book = args.getSerializable(ARG_BOOK) as Book
        startPage = args.getInt(ARG_BOOK_CURRENT_PAGE)

        activity.title = book.title


        // set views
        bookTitle.text = book.title
        bookAuthor.text = "by " + book.authors[0].name


        startMVPOps()

        // pass lifecycle
//        DaggerApplication.passLifeCycle(this)

        return rootView
    }


    /**
     * Tag for logging.
     */
    protected val TAG = "BooksListView"

    // Responsible to maintain the Objects state
    // during changing configuration
//    private val mStateMaintainer = StateMaintainer(fragmentManager, TAG)

    // Presenter operations
    private var mPresenter: BooksMVP.PresenterOps? = null


    lateinit var book: Book


    /**
     * Initialize and restart the Presenter.
     * This method should be called after { Activity#onCreate(Bundle)}
     */
    fun startMVPOps() = try {

        initialize(this)

    } catch (e: Exception) {
        Log.d(TAG, "onCreate() " + e)
        throw RuntimeException(e)
    }


    /**
     * Initialize relevant MVP Objects.
     * Creates a Presenter instance, saves the presenter in [StateMaintainer]
     */
    private fun initialize(view: BooksMVP.BooksListViewOps) {
        mPresenter = ShelfPresenter(view)
//        mStateMaintainer.put("TODO", mPresenter)
    }


    // Show AlertDialog
    override fun showAlert(msg: String) {
        // show alert Box
    }


    enum class State {
        RUNNING, NOT_RUNNING
    }

    private var state: State = State.NOT_RUNNING

    private lateinit var startTime: Date

    @RequiresApi(Build.VERSION_CODES.O)
    @OnClick(R.id.startCounter)
    fun startReadingService(view: View) {


        val intent = Intent(activity, BookStopwatchService::class.java)

        when (state) {
            State.NOT_RUNNING -> {
                // start service
                activity.bindService(intent, this, Context.BIND_AUTO_CREATE)

                startStopText.text = "Stop"

                state = State.RUNNING

                // get this time

                startTime = Date()
            }
            State.RUNNING -> {
                // stop service
                startStopText.text = "Start"

                state = State.NOT_RUNNING

                activity.unbindService(this)

                finishReadingSession()
            }
        }

    }

    /**
     * Finish reading session.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun finishReadingSession() {


        // custom dialog
        val dialog: Dialog = Dialog(context);
        dialog.setContentView(R.layout.finished_session_dialog);
        dialog.setTitle("Title...");

        val checkBox: CheckBox = dialog.findViewById(R.id.checkBox)
        val pageNumber: EditText = dialog.findViewById(R.id.pageNumber)
        val okButton: Button = dialog.findViewById(R.id.okButton)

        var currentP: Int

        okButton.setOnClickListener {
            // TODO validate page or use other form of control (slide with values, f.e.)
            currentP = pageNumber.text.toString().toInt()
            dialog.cancel()
            showInfoOnScreen(currentP)
        }

        checkBox.setOnClickListener {
            currentP = book.num_pages
            dialog.cancel()
            showInfoOnScreen(currentP)
            // TODO signal book was read
        }

        dialog.show()
    }

    var startPage: Int = 0

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showInfoOnScreen(currentPage: Int) {
        // show info - time (start, stop, elapsed), pages read, reading pagesInfo
//        book.currentPage += currentPage

//        Log.d("Current elapsedTime", book.currentPage.toString())


// check elapsed time

// prompt user about how many pages were read

        val diff: Long = elapsedSeconds.toLong()
        val diffSeconds = diff % 60
        val diffMinutes = diff / (60) % 60
        val diffHours = diff / (60 * 60)
        println("Time in seconds: $diffSeconds seconds.")
        println("Time in minutes: $diffMinutes minutes.")
        println("Time in hours: $diffHours hours.")


        Toast.makeText(context, "Elapsed time: $diffHours:$diffMinutes:$diffSeconds.", Toast.LENGTH_SHORT).show()


        // store ReadingSessionDB in room
        val readSess = ReadingSessionDB(elapsedSeconds, startPage, currentPage, book.title, startTime.time)

        mPresenter!!.addReadingSession(readSess)

    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.navigation, menu)
//        return true
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }


    private var s: BookStopwatchService? = null

    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
        val b: BookStopwatchService.MyBinder = binder as BookStopwatchService.MyBinder
        s = b.service

        // set book name
        s!!.bookName = book.title
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        s = null
    }

    override fun onPause() {
        super.onPause()
//        activity.unbindService(this)
    }

    var elapsedSeconds = 0

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        Log.d("Event", "Received event!")
        elapsedSeconds = s!!.counter
        // update Counter value
        counterValue.setText(elapsedSeconds.toString() + " s")
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    companion object {

        private val ARG_BOOK = "book"
        private val ARG_BOOK_CURRENT_PAGE = "page_current"


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(book: Book, currentPage: Int): Fragment {
            val fragment = BookUndergoReadingSessionView()
            val args = Bundle()
            args.putSerializable(ARG_BOOK, book)
            args.putInt(ARG_BOOK_CURRENT_PAGE, currentPage)


            args.putSerializable(ARG_BOOK, book)


            fragment.arguments = args


            return fragment
        }
    }
}