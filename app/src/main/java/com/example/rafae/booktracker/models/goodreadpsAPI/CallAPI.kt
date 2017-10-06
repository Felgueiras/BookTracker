package com.example.rafae.booktracker.models.goodreadpsAPI

import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.os.AsyncTask
import android.util.Log
import com.example.rafae.booktracker.BooksMVP
import com.example.rafae.booktracker.daggerExample.DaggerApplication
import com.example.rafae.booktracker.models.SampleDatabase
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Review
import com.example.rafae.booktracker.oauth.DoBackgroundNetTask
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.*

/**
 * Created by felguiras on 26/09/2017.
 */
// TODO make singleton
class CallAPI {

    enum class CALL_RETURN {
        XML, JSON
    }

    val API_BASE_URL = "https://www.goodreads.com"

    enum class API_ACTION {
        GET_BOOKS, GET_STATUS
    }

    /**
     * Call GoodReadsAPI to retrieve books from a shelf.
     */
    fun call(mPresenter: Object?, shelf: String = "", act: API_ACTION) {
        val callType: CALL_RETURN = CALL_RETURN.XML
        val retrofit: Retrofit

        when (callType) {
            CALL_RETURN.XML -> {
                retrofit = Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .client(OkHttpClient())
                        .addConverterFactory(SimpleXmlConverterFactory.create())
                        .build()
            }
            CALL_RETURN.JSON -> {
                val httpClient = OkHttpClient.Builder()

                val builder = Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        )

                retrofit = builder
                        .client(
                                httpClient.build()
                        )
                        .build()
            }
        }

        when (act) {
            API_ACTION.GET_BOOKS -> {
                getBooksFromShelf(retrofit, mPresenter as BooksMVP.RequiredPresenterOps, shelf)
            }
            API_ACTION.GET_STATUS -> {
                getStatusInfo(retrofit, mPresenter as Object, shelf)
            }
        }


    }

    private fun getUserStatus(retrofit: Retrofit, mPresenter: BooksMVP.RequiredPresenterOps?, shelf: String) {
        // Create a very simple REST adapter which points the GitHub API endpoint.
        val client = retrofit.create(GoodReadsRESTClient::class.java)

        DoBackgroundNetTask(null).execute()

        // Fetch a list of the Github repositories.
        val call = client.getMemberInfo()

        Log.d("Call", call.request().url().toString())

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(object : Callback<GoodReadResponse> {
            override fun onResponse(call: Call<GoodReadResponse>, response: Response<GoodReadResponse>) {
                // The network call was a success and we got a response

                Log.d("REST", "fetched response")
                val resp = response.body()

                // get user
                val user = resp!!.user
                // get updates
                val updates = user.updates
                // get only those of type usertatus
                val userStatusUpdates = mutableListOf<Update>()
                for (update in updates) {
                    if (update.type.equals("userstatus")) {
                        userStatusUpdates.add(update)
                    }
                }

                // get one of them
                val update = userStatusUpdates[0]
                // get user status
                val userStatus = update.`object`.user_status
                val currentPage = userStatus.page
                val percent = userStatus.percent
                val book = userStatus.book
                Log.d("Process", currentPage.toString() + "(" + book.num_pages + ")")

                // get user status ID for another request
                val id = userStatus.user_status_id

                // TODO make another request

                // get number of pages for books

                // get reviews/books on this shelf
//                val reviews: List<Review> = resp!!.reviews
//
//                val books: MutableList<Book> = mutableListOf<Book>()
//
//                // iterate over reviews
//                for (review: Review in reviews) {
//                    // get book
//                    val book: Book = review.book
//                    Log.d("Book", book.elapsedTime)
//                    books.add(book)
//                }
//
//                mPresenter!!.onBookInserted(books as ArrayList<Book>)


//                // handle response and display it in recycler view
//                val body = response.body()
//
//                for (task in body!!.tasks!!) {
//                    Log.d("REST", task.toString())
//                }
//
//                // crete adapter
//                val adapter = TasksListAdapter(body.tasks!!, activity)
//                recycler.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
            }

            override fun onFailure(call: Call<GoodReadResponse>, t: Throwable) {
                // the network call was a failure
                // TODO: handle error
                Log.d("RESTerr", t.toString())
            }
        })
    }


    /**
     * Get user status for a book (i.e. elapsedTime for a day)
     */
    private fun getStatusInfo(retrofit: Retrofit, mPresenter: Object?, shelf: String) {
        // Create a very simple REST adapter which points the GitHub API endpoint.
        val client = retrofit.create(GoodReadsRESTClient::class.java)

        DoBackgroundNetTask(null).execute()

        // Fetch a list of the Github repositories.
        val call = client.getStatusInfo()

        Log.d("Call", call.request().url().toString())

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(object : Callback<GoodReadResponse> {
            override fun onResponse(call: Call<GoodReadResponse>, response: Response<GoodReadResponse>) {
                // The network call was a success and we got a response

                Log.d("REST", "fetched response")
                val resp = response.body()

                // get user status
                val userStatusMain = resp!!.user_status
                // get status inside that status
                val userStatusArray = userStatusMain.user_status
//                Log.d("Process", currentPage.toString() + "(" + book.num_pages + ")")
                var userStatusUpdates = mutableListOf<UserStatus>()
                if (userStatusArray == null) {
                    // just one update
                    Log.d("Status", userStatusMain.toString())


                    userStatusUpdates.add(userStatusMain)

                    // TODO store using Room
//                    DatabaseAsync().execute(userStatusUpdates)

                } else {
                    userStatusUpdates = userStatusArray
//                    for (status in userStatusArray) {
//                        Log.d("Status", status.toString())
//                    }
//                    DatabaseAsync().execute(userStatusArray)

                }


                // cast presenter
                val pres = mPresenter as BooksMVP.RequiredBookDetailPresenterOps

                pres.onStatusForBookRetrieved(userStatusUpdates)

            }

            override fun onFailure(call: Call<GoodReadResponse>, t: Throwable) {
                // the network call was a failure
                // TODO: handle error
                Log.d("RESTerr", t.toString())
            }
        })

        // LiveData
//         obeserve() first argument needs to extend LifeCycle (LifeCycleActivity)
        val vehicleComponent = DaggerApplication.getComponent()
        val vehicle = vehicleComponent.provideVehicle()
        sampleDatabase = Room.databaseBuilder(vehicle.context,
                SampleDatabase::class.java, "sample-db").build()
        val booksList = sampleDatabase.daoAccess().fetchAllData()
        booksList.observe(vehicle.lifecycle, object : Observer<List<UserStatus>> {
            override fun onChanged(statusArray: List<UserStatus>?) {
                //Update your UI here.
                Log.d("Room", "Fetched all data")
                for (status in statusArray!!) {
                    Log.d("Status(Room)", status.user_status_id.toString())
                }
//                mPresenter!!.onBookInserted(statusArray as ArrayList<BookDB>)
            }
        })
    }

    private inner class DatabaseAsync : AsyncTask<List<UserStatus>, Void, Void>() {


        override fun doInBackground(vararg voids: List<UserStatus>): Void? {
            // get list
            val status = voids[0]


            //Let's add some dummy data to the database.
            val vehicleComponent = DaggerApplication.getComponent()
            val vehicle = vehicleComponent.provideVehicle()
            sampleDatabase = Room.databaseBuilder(vehicle.context,
                    SampleDatabase::class.java, "sample-db").build()
            // check if in DB
            sampleDatabase.daoAccess().insertMultipleListRecord(status)

            return null
        }

        override fun onPostExecute(aVoid: Void) {
            super.onPostExecute(aVoid)

            //To after addition operation here.
        }
    }


    lateinit var sampleDatabase: SampleDatabase


    /**
     * Call Goodreads API.
     */
    private fun getBooksFromShelf(retrofit: Retrofit, mPresenter: BooksMVP.RequiredPresenterOps?, shelf: String) {
        // Create a very simple REST adapter which points the GitHub API endpoint.
        val client = retrofit.create(GoodReadsRESTClient::class.java)

        DoBackgroundNetTask(null).execute()

        // Fetch a list of the Github repositories.
        val call = client.booksFromShelf(shelf = shelf)

        Log.d("Call", call.request().url().toString())

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(object : Callback<GoodReadResponse> {
            override fun onResponse(call: Call<GoodReadResponse>, response: Response<GoodReadResponse>) {
                // The network call was a success and we got a response

                Log.d("REST", "fetched response")
                val resp = response.body()

                // get reviews/books on this shelf
                val reviews: List<Review> = resp!!.reviews

                val books: MutableList<Book> = mutableListOf<Book>()

                // iterate over reviews
                for (review: Review in reviews) {
                    // get book
                    val book: Book = review.book
                    Log.d("Book", book.title)
                    books.add(book)
                }

                mPresenter!!.onBookInserted(books as ArrayList<Book>)


//                // handle response and display it in recycler view
//                val body = response.body()
//
//                for (task in body!!.tasks!!) {
//                    Log.d("REST", task.toString())
//                }
//
//                // crete adapter
//                val adapter = TasksListAdapter(body.tasks!!, activity)
//                recycler.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
            }

            override fun onFailure(call: Call<GoodReadResponse>, t: Throwable) {
                // the network call was a failure
                // TODO: handle error
                Log.d("RESTerr", t.toString())
            }
        })
    }


}