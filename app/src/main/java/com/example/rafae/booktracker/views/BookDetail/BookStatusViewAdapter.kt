package com.example.rafae.booktracker.views.BookDetail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.ButterKnife
import com.example.rafae.booktracker.R
import com.example.rafae.booktracker.models.goodreadpsAPI.UserStatus
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book


/**
 * Created by felguiras on 15/09/2017.
 */
internal class BookStatusViewAdapter(private val updates: MutableList<UserStatus>, private val context: Context, val book: Book) : RecyclerView.Adapter<BookStatusViewAdapter.BookInListHolder>() {

    var myBooks: MutableList<UserStatus> = updates

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookInListHolder {
        val inflatedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_status_update, parent, false)
        return BookInListHolder(inflatedView, context)
    }


    override fun onBindViewHolder(holder: BookInListHolder, position: Int) {
        // get current book
        val update = updates[position]
        holder.bindUpdate(update, book)
    }

    override fun getItemCount(): Int {
        return myBooks!!.size
    }

    class BookInListHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        lateinit var page: TextView
        lateinit var date: TextView
        lateinit var percentage: TextView

        init {
            ButterKnife.bind(this, view)

            page = view.findViewById(R.id.statusPage)
            percentage = view.findViewById(R.id.statusPercentage)
            date = view.findViewById(R.id.statusDate)


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

        fun bindUpdate(status: UserStatus, book: Book) {
//            book = status
            page.text = status.user_status_id.toString()
            date.text = status.page.toString()
            percentage.text = status.percent.toString()
            this.book = book

        }




    }
}