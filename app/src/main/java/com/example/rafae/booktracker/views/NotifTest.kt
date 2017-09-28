package com.example.rafae.booktracker.views

import android.arch.lifecycle.LifecycleFragment
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.android.notificationchannels.NotificationHelper
import com.example.rafae.booktracker.BooksMVP
import com.example.rafae.booktracker.R
import com.example.rafae.booktracker.StateMaintainer
import com.example.rafae.booktracker.daggerExample.DaggerApplication
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.notifications.BookStopwatchService
import com.example.rafae.booktracker.notifications.messages.MessageEvent
import com.example.rafae.booktracker.objects.BookDB
import com.example.rafae.booktracker.objects.ReadingSession
import com.example.rafae.booktracker.presenters.BooksListPresenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class NotifTest : LifecycleFragment(), BooksMVP.BooksListViewOps, ServiceConnection {

    // views
    @BindView(R.id.bookTitle)
    lateinit var bookTitle: TextView
    @BindView(R.id.bookDateAdded)
    lateinit var bookDateAdded: TextView
    @BindView(R.id.bookAuthor)
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.books_detail_counter, container, false)


        ButterKnife.bind(this, rootView)
        startMVPOps()

        // pass lifecycle
        DaggerApplication.passLifeCycle(this)

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


    lateinit var book: BookDB

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        startMVPOps()
//        setContentView(R.layout.books_single_detail)
//
//        ButterKnife.bind(this)
//
//        // fetch book
//        // get intent
//        val extras = this.intent.getExtras();
//        if (extras == null) {
//            return;
//        }
//        // get data via the key
//        book = extras.getSerializable(BOOK) as BookDB
//
//    }


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
        mPresenter = BooksListPresenter(view)
//        mStateMaintainer.put("TODO", mPresenter)
    }

    /**
     * Recovers Presenter and informs Presenter that occurred a config change.
     * If Presenter has been lost, recreates a instance
     */
//    private fun reinitialize(view: BooksMVP.BooksListViewOps) {
//        mPresenter = mStateMaintainer.get<BooksMVP.PresenterOps>("TODO")
//
//        if (mPresenter == null) {
//            Log.w(TAG, "recreating Presenter")
//            initialize(view)
//        } else {
//            mPresenter!!.onConfigurationChanged(view)
//        }
//    }


    // Show AlertDialog
    override fun showAlert(msg: String) {
        // show alert Box
    }


//    @OnClick(R.id.bookStartReading)
//    fun startReadingBook(v: View) {
//
//        val btn = v as Button
//
//        book.reading = !book.reading
//        if (book.reading) {
//            Log.d("Books", "start reading")
//            //
//            Log.d("Books", book.currentPage.toString())
//            btn.text = "Stop"
//
//            addReadingSession(book)
//        } else {
//            Log.d("Books", "stopped reading")
//            btn.text = "Start"
//            finishReadingSession(book)
//        }
//
//        // TODO notification
//
//
//    }

    private val NOTIFICATION_ID: Int = 1

    enum class State {
        RUNNING, NOT_RUNNING
    }

    private var state: NotifTest.State = State.NOT_RUNNING

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
            }
            State.RUNNING -> {
                // stop service
                startStopText.text = "Start"

                state = State.NOT_RUNNING

                activity.unbindService(this)

            }
        }

    }


    private lateinit var mNotificationHelper: NotificationHelper


    /**
     * Finish reading session.
     */
    private fun finishReadingSession(book: BookDB) {
        // get current time
        val current = Date()

        // new reading session
        readingSess!!.stop = current

        // prompt how many pages were read
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Hey man, nice reading!")
        alertDialog.setMessage("In which page are you?")


        val input = EditText(context)
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
        Toast.makeText(context, "Elapsed time: $diffHours:$diffMinutes:$diffSeconds.", Toast.LENGTH_SHORT).show()
    }


    var readingSess: ReadingSession? = null

    /**
     * Start reading session.
     */
    private fun addReadingSession(book: BookDB) {
        // get current time
        val current = Date()

        // new reading session
        readingSess = ReadingSession(current)
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
        s = b.service;
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        s = null
    }

    override fun onPause() {
        super.onPause()
        activity.unbindService(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        Log.d("Event", "Received event!")
        val elapsedSeconds = s!!.counter
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
        val BOOK = "BOOK"

        private val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int, supportFragmentManager: FragmentManager): NotifTest {
            val fragment = NotifTest()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args


            return fragment
        }
    }
}
