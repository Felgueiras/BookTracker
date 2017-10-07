package com.example.rafae.booktracker.views.BookDetail

import android.app.Fragment
import android.app.FragmentTransaction
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.*
import butterknife.BindView
import butterknife.OnClick
import com.example.rafae.booktracker.BooksMVP
import com.example.rafae.booktracker.DrawerActivity
import com.example.rafae.booktracker.R
import com.example.rafae.booktracker.StateMaintainer
import com.example.rafae.booktracker.helpers.TimeHelpers
import com.example.rafae.booktracker.models.goodreadpsAPI.UserStatus
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.notifications.BookStopwatchService
import com.example.rafae.booktracker.notifications.messages.MessageEvent
import com.example.rafae.booktracker.objects.BookDB
import com.example.rafae.booktracker.objects.ReadingSessionDB
import com.example.rafae.booktracker.presenters.BookDetailPresenter
import com.example.rafae.booktracker.views.BookReadingSession.BookUndergoReadingSessionView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlinx.android.synthetic.main.book_detail.*


class BookDetailView : Fragment(), BooksMVP.BookDetailViewOps, ServiceConnection {

    private lateinit var sessions: List<ReadingSessionDB>

    /**
     * Present Reading Sessions to the User.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReadingSessionForBookRetrieved(sessions: List<ReadingSessionDB>) {
        // update recycler view
        val adapter = BookReadingSessionsAdapter(sessions, context, book)

        this.sessions = sessions

        readingSessionsRecycler.adapter = adapter
        adapter.notifyDataSetChanged()

        // display reading percentage
        if (sessions.size == 0) {
            readingPercentage.text = "not read"
            readingSpeedAverage.text = ""
        } else {
            var percentageRead = Math.round(sessions.last().endPage!!.toFloat() / book.num_pages * 100)
            readingPercentage.text = percentageRead.toString() + " %"
            // get average reading speed
            readingSpeedAverage.text = TimeHelpers.getAverageReadingSpeed(sessions).toString() + " pages/m"
        }
    }

    private lateinit var bookStatus: MutableList<UserStatus>

    /**
     * Display status updates for this book.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    override fun statusForBookretrieved(userStatusUpdates: MutableList<UserStatus>) {
        // update recycler view
//        val adapter = BookReadingSessionsAdapter(userStatusUpdates, context, book)

        this.bookStatus = userStatusUpdates
//        statusRecycler.adapter = adapter
//        adapter.notifyDataSetChanged()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        inflater!!.inflate(R.menu.menu_book_detail, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        return if (id == R.id.continueReading) {
            // open a new fragment

            val f = (view.context as DrawerActivity).fragmentManager.findFragmentById(R.id.current_fragment)

            // pass last reading session's page


            val newFragment: Fragment = BookUndergoReadingSessionView.newInstance(book, lastReadingSessionPage())
            (view.context as DrawerActivity).fragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .add(R.id.current_fragment, newFragment)
                    .addToBackStack(null)
                    .commit()

            true
        } else super.onOptionsItemSelected(item)

    }

    fun lastReadingSessionPage(): Int {
        if (sessions.size == 0) {
            return 0
        } else {
            // get the latest reading session
            return sessions.last().endPage!!
        }
    }


    //    @BindView(R.id.bookStartReading)
//    lateinit var bookStartReading: Button
    @BindView(R.id.counterValue)
    lateinit var counterValue: TextView

    override fun showToast(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun newBookAddeda(books: ArrayList<BookDB>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (container != null) {
//            container.removeAllViews()
        }

        // get book

        val rootView = inflater!!.inflate(R.layout.book_detail, container, false)

//        ButterKnife.bind(this, rootView)
        // get book from arguments
        var args = arguments
        // get data via the key
        book = args.getSerializable(ARG_BOOK) as Book



        startMVPOps()


        // set title
        activity.title = book.title

        // fetch status about this book
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        // set views
        statusPage.text = book.title
        statusDate.text = "by " + book.authors[0].name

        // pass lifecycle
//        DaggerApplication.passLifeCycle(this)

        // set recycler view
//        booksList = rootView.findViewById(R.id.booksList)
        readingSessionsRecycler.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        val dividerItemDecoration = DividerItemDecoration(readingSessionsRecycler.getContext(),
                (readingSessionsRecycler.layoutManager as LinearLayoutManager).getOrientation())
        readingSessionsRecycler.addItemDecoration(dividerItemDecoration)

        // fetch status for this book
//        mPresenter!!.fetchStatusForBook(book)

        // fetch reading updates for this book
        mPresenter!!.fetchReadingSessionsForBook(book)
    }


    /**
     * Tag for logging.
     */
    protected val TAG = "BooksListView"

    // Responsible to maintain the Objects state
    // during changing configuration
//    private val mStateMaintainer = StateMaintainer(fragmentManager, TAG)

    // Presenter operations
    private var mPresenter: BooksMVP.BookDetailPresenterOps? = null


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
    private fun initialize(view: BooksMVP.BookDetailViewOps) {
        mPresenter = BookDetailPresenter(view)
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

    @RequiresApi(Build.VERSION_CODES.O)
    @OnClick(R.id.startCounter)
    fun startReadingService(view: View) {


        val intent = Intent(activity, BookStopwatchService::class.java)


    }

    /**
     * Finish reading session.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun finishReadingSession() {
        // get current time
        val current = Date()

        // new reading session
//        readingSess!!.stop = current

        // prompt how many pages were read
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Hey man, nice reading!")
        alertDialog.setMessage("In which elapsedTime are you?")


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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showInfoOnScreen(currentP: Int) {
        // show info - time (start, stop, elapsed), pages read, reading pagesInfo
//        book.currentPage += currentP

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

// add reading session to book
//        book.readingSessions!!.add(readingSess!!)
        Toast.makeText(context, "Elapsed time: $diffHours:$diffMinutes:$diffSeconds.", Toast.LENGTH_SHORT).show()
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
        val BOOK = "BOOK"

        private val ARG_SECTION_NUMBER = "section_number"
        private val ARG_BOOK = "book"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int, book: Book?): Fragment {
            val fragment = BookDetailView()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            if (book != null) {

                args.putSerializable(ARG_BOOK, book)
            }

            fragment.arguments = args


            return fragment
        }
    }
}
