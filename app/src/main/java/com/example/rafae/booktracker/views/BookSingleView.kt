package com.example.rafae.booktracker.views

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.example.rafae.booktracker.R
import com.example.rafae.booktracker.StateMaintainer
import com.example.rafae.booktracker.objects.BookDB
import com.example.rafae.booktracker.objects.ReadingSessionDB
import java.util.*
import android.widget.LinearLayout
import android.widget.EditText
import com.example.rafae.booktracker.BooksMVP
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.presenters.ShelfPresenter
import kotlin.collections.ArrayList


class BookSingleView : AppCompatActivity(), BooksMVP.BooksListViewOps {


    override fun displayBooks(books: ArrayList<Book>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun newBookAddeda(books: ArrayList<BookDB>) {
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
    @BindView(R.id.statusPage)
    lateinit var bookTitle: TextView
    @BindView(R.id.statusDate)
    lateinit var bookAuthor: TextView
    @BindView(R.id.bookDateAdded)
    lateinit var bookDateAdded: TextView
//    @BindView(R.id.bookStartReading)
//    lateinit var bookStartReading: Button


    lateinit var book: BookDB

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
        book = extras.getSerializable(BOOK) as BookDB

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

    fun sendNotification(view: View) {

        // BEGIN_INCLUDE(build_action)
        /** Create an intent that will be fired when the user clicks the notification.
         * The intent needs to be packaged into a [android.app.PendingIntent] so that the
         * notification service can fire it on our behalf.
         */
        val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("http://developer.android.com/reference/android/app/Notification.html"))
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        // END_INCLUDE(build_action)

        // BEGIN_INCLUDE (build_notification)
        /**
         * Use NotificationCompat.Builder to set up our notification.
         */
        val builder = NotificationCompat.Builder(this)

        /** Set the icon that will appear in the notification bar. This icon also appears
         * in the lower right hand corner of the notification itself.
         *
         * Important note: although you can use any drawable as the small icon, Android
         * design guidelines state that the icon should be simple and monochrome. Full-color
         * bitmaps or busy images don't render well on smaller screens and can end up
         * confusing the user.
         */
        builder.setSmallIcon(R.drawable.ic_stat_notification)

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent)

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true)


        /**
         * Build the notification's appearance.
         * Set the large icon, which appears on the left of the notification. In this
         * sample we'll set the large icon to be the same as our app icon. The app icon is a
         * reasonable default if you don't have anything more compelling to use as an icon.
         */
        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.notification_tile_bg))

        /**
         * Set the text of the notification. This sample sets the three most commononly used
         * text areas:
         * 1. The content elapsedTime, which appears in large type at the top of the notification
         * 2. The content text, which appears in smaller text below the elapsedTime
         * 3. The subtext, which appears under the text on newer devices. Devices running
         * versions of Android prior to 4.2 will ignore this field, so don't use it for
         * anything vital!
         */
        builder.setContentTitle("BasicNotifications Sample")
        builder.setContentText("Time to learn about notifications!")
        builder.setSubText("Tap to view documentation about notifications.")


        /**
         * Add actions.
         */
        builder.addAction(R.drawable.ic_stat_notification, "CLICK", pendingIntent)

        // END_INCLUDE (build_notification)

        // BEGIN_INCLUDE(send_notification)
        /**
         * Send the notification. This will immediately display the notification icon in the
         * notification bar.
         */
        val notificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
        // END_INCLUDE(send_notification)
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