package com.example.rafae.booktracker.views.BookDetail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.ButterKnife
import com.example.rafae.booktracker.R
import com.example.rafae.booktracker.helpers.TimeHelpers
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.objects.ReadingSessionDB

/**
 * Created by felguiras on 15/09/2017.
 */
internal class BookReadingSessionsAdapter(private val readingSessions: List<ReadingSessionDB>, private val context: Context, val book: Book) : RecyclerView.Adapter<BookReadingSessionsAdapter.BookReadingSessionHolder>() {

    var myBooks: List<ReadingSessionDB> = readingSessions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookReadingSessionHolder {
        val inflatedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_reading_status, parent, false)
        return BookReadingSessionHolder(inflatedView, context)
    }


    override fun onBindViewHolder(holder: BookReadingSessionHolder, position: Int) {
        // get current book
        val update = readingSessions[position]
        holder.bindUpdate(update, book)
    }

    override fun getItemCount(): Int {
        return myBooks!!.size
    }

    class BookReadingSessionHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        var elapsedTime: TextView
        var pagesInfo: TextView
        var readingSpeed: TextView
        var percentage: TextView

        init {
            ButterKnife.bind(this, view)

            elapsedTime = view.findViewById(R.id.elapsedTime)
            pagesInfo = view.findViewById(R.id.startPage)
            readingSpeed = view.findViewById(R.id.readingSpeed)
            percentage = view.findViewById(R.id.readingPercentage)


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

        fun bindUpdate(session: ReadingSessionDB, book: Book) {

            val pagesRead = session.endPage!! - session.startPage!!
//            book = session
            elapsedTime.text = "Read for " + session.readingTime.toString() + " seconds"
//            stopPage.text = session.endPage.toString()
            pagesInfo.text = "Read from " + session.startPage.toString() + " - " + session.endPage.toString() +
                    " (" + pagesRead + ")"
            // speed
            readingSpeed.text = TimeHelpers.getReadingSpeed(session).toString() + " pages/m"
            // percentage
            var percentageRead = Math.round(session.endPage!!.toFloat() / book.num_pages * 100)
            percentage.text = percentageRead.toString() + " %"

            this.book = book

        }


    }
}