package com.example.rafae.booktracker.views.Shelf

import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import com.example.rafae.booktracker.R
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.DrawerActivity
import com.example.rafae.booktracker.views.BookDetail.BookDetailView


/**
 * Created by felguiras on 15/09/2017.
 */
internal class ShelfViewAdapter(private val books: ArrayList<Book>, private val context: Context) : RecyclerView.Adapter<ShelfViewAdapter.BookInListHolder>() {

    var myBooks: ArrayList<Book>? = books

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookInListHolder {
        val inflatedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_in_list, parent, false)
        return BookInListHolder(inflatedView, context)
    }


    override fun onBindViewHolder(holder: BookInListHolder, position: Int) {
        // get current book
        val bk = books[position]
        holder.bindBook(bk)
    }

    override fun getItemCount(): Int {
        return myBooks!!.size
    }

    class BookInListHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        // ButterKnife
        @BindView(R.id.statusPage)
        lateinit var title: TextView
        @BindView(R.id.statusDate)
        lateinit var author: TextView
        lateinit var completion: TextView
        lateinit var avgRating: TextView
        lateinit var pubYear: TextView

        init {
            ButterKnife.bind(this, view)

            title = view.findViewById(R.id.statusPage)
            author = view.findViewById(R.id.statusDate)
            completion = view.findViewById(R.id.statusPercentage)
            avgRating = view.findViewById(R.id.avgRating)
            pubYear = view.findViewById(R.id.pubYear)

            view.setOnClickListener {
//                val intent = Intent(context, BookDetailView::class.java)
//                intent.putExtra(BookSingleView.BOOK, book)
//                context.startActivity(intent)
                val f = (view.context as DrawerActivity).fragmentManager.findFragmentById(R.id.current_fragment)
                val newFragment:Fragment = BookDetailView.Companion.newInstance(1, book)
                (view.context as DrawerActivity).fragmentManager.beginTransaction()
//                        .hide(f)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .add(R.id.current_fragment,newFragment )
                        .addToBackStack(null)
                        .commit()
            }
        }

        lateinit var book: Book

        fun bindBook(bk: Book) {
            book = bk
            title.text = bk.title
            author.text = "by "+bk.authors[0].name
            // TODO get last reading session for Book
            val completionVal:Int = 50
            completion.text = """${completionVal.toString()}%"""
            // rating - number ratings
            avgRating.text = bk.avgRating.toString()
            // pub year
            pubYear.text = bk.pubYear


        }




    }
}
