package com.example.rafae.booktracker.views.BookDetail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.ButterKnife
import com.example.rafae.booktracker.R
import com.example.rafae.booktracker.helpers.ReadingSessionHelpers
import com.example.rafae.booktracker.helpers.TimeHelpers
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.objects.ReadingSessionDB
import kotlinx.android.synthetic.main.book_reading_status.*

/**
 * Created by felguiras on 15/09/2017.
 */
internal class BookReadingSessionsAdapter(private val readingSessions: List<ReadingSessionDB>, private val context: Context, val book: Book) : RecyclerView.Adapter<BookReadingSessionsAdapter.BookReadingSessionHolder>() {

    private var fastest: ReadingSessionDB
    private var slowest: ReadingSessionDB

    val longest: ReadingSessionDB

    private var shortest: ReadingSessionDB

    private var morePagesRead: ReadingSessionDB

    private var lessPagesRead: ReadingSessionDB

    init {
        // store references to the sessions to be highlighted
        fastest = ReadingSessionHelpers.getFastestReadingSession(readingSessions)
        slowest = ReadingSessionHelpers.getSlowestReadingSession(readingSessions)
        longest = ReadingSessionHelpers.getLongestReadingSession(readingSessions)
        shortest = ReadingSessionHelpers.getShortestReadingSession(readingSessions)
        morePagesRead = ReadingSessionHelpers.getMorePagesReadReadingSession(readingSessions)
        lessPagesRead = ReadingSessionHelpers.getLessPagesReadReadingSession(readingSessions)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookReadingSessionHolder {
        val inflatedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_reading_status, parent, false)
        return BookReadingSessionHolder(inflatedView, context, this)
    }


    override fun onBindViewHolder(holder: BookReadingSessionHolder, position: Int) {
        // get current book
        val readingSession = readingSessions[readingSessions.size - 1 - position]
        holder.bindReadingSession(readingSession, book)
    }

    override fun getItemCount(): Int {
        return readingSessions.size
    }

    /**
     * Holder for a Reading Session.
     */
    class BookReadingSessionHolder(view: View, private val context: Context, val bookReadingSessionsAdapter: BookReadingSessionsAdapter) : RecyclerView.ViewHolder(view) {
        var elapsedTime: TextView
        var pagesInfo: TextView
        var readingSpeed: TextView
        var percentage: TextView
        var progress: ProgressBar
        // highlights
        var longest: ImageView
        var shortest: ImageView
        var fastest: ImageView
        var slowest: ImageView
        var morePages: ImageView
        var lessPages: ImageView

        init {
            ButterKnife.bind(this, view)

            elapsedTime = view.findViewById(R.id.elapsedTime)
            pagesInfo = view.findViewById(R.id.startPage)
            readingSpeed = view.findViewById(R.id.readingSpeed)
            percentage = view.findViewById(R.id.readingPercentage)
            progress = view.findViewById(R.id.progressVisual)
            // highlights
            longest = view.findViewById(R.id.longest)
            shortest = view.findViewById(R.id.shortest)
            fastest = view.findViewById(R.id.fastest)
            slowest = view.findViewById(R.id.slowest)
            morePages = view.findViewById(R.id.morePages)
            lessPages = view.findViewById(R.id.lessPages)


            view.setOnClickListener {
                //                val intent = Intent(context, BookDetailView::class.java)
//                intent.putExtra(BookSingleView.BOOK, book)
//                context.startActivity(intent)
//                val f = (view.context as DrawerActivity).fragmentManager.findFragmentById(R.id.current_fragment)
//                val newFragment: Fragment = BookDetailView.newInstance(1, book)
//                (view.context as DrawerActivity).fragmentManager.beginTransaction()
////                        .hide(f)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        .add(R.id.current_fragment,newFragment )
//                        .addToBackStack(null)
//                        .commit()
            }
        }

        lateinit var book: Book

        fun bindReadingSession(session: ReadingSessionDB, book: Book) {

            val pagesRead = session.endPage!! - session.startPage!!
//            book = session
            elapsedTime.text = "Read for " + TimeHelpers.convertSecondsToTimeString(session.readingTime) + " seconds"
//            stopPage.text = session.endPage.toString()
            pagesInfo.text = "Read from " + session.startPage.toString() + " - " + session.endPage.toString() +
                    " (" + pagesRead + ")"
            // speed
            readingSpeed.text = TimeHelpers.getReadingSpeed(session).toString() + " pages/m"
            // percentage
            val percentageRead: Int = Math.round(session.endPage!!.toFloat() / book.num_pages * 100)
            percentage.text = percentageRead.toString() + " %"

            progress.progress = percentageRead

            this.book = book

            // check if to be highlighted (longest)
            if (session.equals(bookReadingSessionsAdapter.longest)) {
                longest.visibility = View.VISIBLE
            }
            if (session.equals(bookReadingSessionsAdapter.shortest)) {
                shortest.visibility = View.VISIBLE
            }
            if (session.equals(bookReadingSessionsAdapter.fastest)) {
                fastest.visibility = View.VISIBLE
            }
            if (session.equals(bookReadingSessionsAdapter.slowest)) {
                slowest.visibility = View.VISIBLE
            }
            if (session.equals(bookReadingSessionsAdapter.morePagesRead)) {
                morePages.visibility = View.VISIBLE
            }
            if (session.equals(bookReadingSessionsAdapter.lessPagesRead)) {
                lessPages.visibility = View.VISIBLE
            }

        }


    }
}