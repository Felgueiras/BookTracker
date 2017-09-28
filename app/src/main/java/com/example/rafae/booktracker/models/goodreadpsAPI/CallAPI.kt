package com.example.rafae.booktracker.models.goodreadpsAPI

import android.util.Log
import com.example.rafae.booktracker.BooksMVP
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Review
import com.example.rafae.booktracker.objects.BookDB
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

/**
 * Created by felguiras on 26/09/2017.
 */
// TODO make singleton
class CallAPI {

    enum class CALL_RETURN {
        XML, JSON
    }

    val API_BASE_URL = "https://www.goodreads.com"

    fun call(mPresenter: BooksMVP.RequiredPresenterOps?) {
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


        // call login
        makeTheCall(retrofit, mPresenter)
    }


    /**
     * Call Goodreads API.
     */
    private fun makeTheCall(retrofit: Retrofit, mPresenter: BooksMVP.RequiredPresenterOps?) {
        // Create a very simple REST adapter which points the GitHub API endpoint.
        val client = retrofit.create(GoodReadsRESTClient::class.java)

        // Fetch a list of the Github repositories.
        val call = client.currentlyReading()

        Log.d("Call",
                call.request().url().toString()
        )

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(object : Callback<GoodReadResponse> {
            override fun onResponse(call: Call<GoodReadResponse>, response: Response<GoodReadResponse>) {
                // The network call was a success and we got a response

                Log.d("REST", "fetched response")
                val resp = response.body()

                // get reviews/books on this shelf
                val reviews: List<Review> = resp!!.reviews

                val books:MutableList<Book> = mutableListOf<Book>()

                // iterate over reviews
                for (review: Review in reviews) {
                    // get book
                    val book: Book = review.book
                    Log.d("Book",book.title)
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