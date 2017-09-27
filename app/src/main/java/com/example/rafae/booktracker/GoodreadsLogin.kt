package com.example.rafae.booktracker

import android.arch.lifecycle.LifecycleFragment
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.rafae.booktracker.daggerExample.DaggerApplication
import com.example.rafae.booktracker.models.goodreadpsAPI.CallAPI
import com.example.rafae.booktracker.objects.Book
import com.example.rafae.booktracker.presenters.BooksListPresenter
import com.example.rafae.booktracker.views.BookAddView
import com.example.rafae.booktracker.views.BooksListAdapter
import java.util.*

class GoodreadsLogin : LifecycleFragment(), BooksMVP.BooksListViewOps {


//    var applicationContext: Context? = null

    override fun newBookAdded(books: ArrayList<Book>) {
        // update recycler view
        val adapter = BooksListAdapter(books, context)

        booksList.adapter = adapter
//        booksList.invalidate()
        adapter.notifyDataSetChanged()
    }

    /**
     * Tag for logging.
     */
    protected val TAG = "BooksListView"

    // Responsible to maintain the Objects state
    // during changing configuration
//    private val mStateMaintainer = StateMaintainer(activity.fragmentManager, TAG)

    // Presenter operations
    private var mPresenter: BooksMVP.PresenterOps? = null

    // views
    @BindView(R.id.booksList)
    lateinit var booksList: RecyclerView
    @BindView(R.id.fabAddBook)
    lateinit var addBook: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.books_list, container, false)


        ButterKnife.bind(this, rootView)
        startMVPOps()

        // pass lifecycle
        DaggerApplication.passLifeCycle(this)

        // TODO fetch books list
        mPresenter!!.fetchBooks()
        val books = ArrayList<Book>()
        var book: Book = Book("Myself", "Hello world!", Date(), 123)
//        books.add(book)

        val adapter = BooksListAdapter(books, activity)

        booksList = rootView.findViewById(R.id.booksList)
        booksList.layoutManager = LinearLayoutManager(activity)
        val dividerItemDecoration = DividerItemDecoration(booksList.getContext(),
                (booksList.layoutManager as LinearLayoutManager).getOrientation())
        booksList.addItemDecoration(dividerItemDecoration)
        booksList.adapter = adapter


        // TODO just for testing - this goes in the model area
        CallAPI().call()

        return rootView
    }


    /**
     * Handle clicking on button to submit new note.
     *
     * @param v
     */
    @OnClick(R.id.fabAddBook)
    fun addBookClicked(v: View) {

        // go to page wehre book is added
        val intent = Intent(activity, BookAddView::class.java)
        this.startActivity(intent)
    }


    /**
     * Initialize and restart the Presenter.
     * This method should be called after { Activity#onCreate(Bundle)}
     */
    fun startMVPOps() {
        try {

            initialize(this)

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
//        mStateMaintainer.put("TODO", mPresenter)
    }

    /**
     * Recovers Presenter and informs Presenter that occurred a config change.
     * If Presenter has been lost, recreates a instance
     */
    private fun reinitialize(view: BooksMVP.BooksListViewOps) {
//        mPresenter = mStateMaintainer.get<BooksMVP.PresenterOps>("TODO")

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
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

        // fetch books
        mPresenter!!.fetchBooks()
    }

    companion object {

        private val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int, supportFragmentManager: FragmentManager): GoodreadsLogin {
            val fragment = GoodreadsLogin()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args


            return fragment
        }
    }

}