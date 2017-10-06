package com.example.rafae.booktracker.models

/**
 * Created by rafae on 24/09/2017.
 */

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.rafae.booktracker.BooksMVP
import com.example.rafae.booktracker.daggerExample.DaggerApplication
import com.example.rafae.booktracker.models.goodreadpsAPI.CallAPI
import com.example.rafae.booktracker.models.goodreadpsAPI.UserStatus
import com.example.rafae.booktracker.models.goodreadpsAPI.responseObjects.Book
import com.example.rafae.booktracker.objects.ReadingSessionDB
import com.example.rafae.booktracker.presenters.BookDetailPresenter
import javax.inject.Singleton


/**
 * Implements operations that Presenter executes on it.
 * Presenter -> Model
 */
@Singleton
class BooksModel private constructor() : BooksMVP.ModelOps {

    /**
     * Use LiveData to get ReadingSessions for a book.
     */
    override fun fetchReadingSessionsForBook(book: Book) {

        // LiveData
//         obeserve() first argument needs to extend LifeCycle (LifeCycleActivity)
        val vehicleComponent = DaggerApplication.getComponent()
        val vehicle = vehicleComponent.provideVehicle()
        sampleDatabase = Room.databaseBuilder(vehicle.context,
                SampleDatabase::class.java, "sample-db").build()
        val booksList = sampleDatabase.daoAccess().fetchReadingSessionsForBook(book.title)
        booksList.observe(vehicle.lifecycle, object : Observer<List<ReadingSessionDB>> {
            override fun onChanged(sessionsArray: List<ReadingSessionDB>?) {

                //Update your UI here.
                Log.d("Room", "Fetched Reading Sessions")
                for (sessions in sessionsArray!!) {
                    Log.d("Status(Room)", sessions.endPage.toString())
                }
                (mPresenter as BookDetailPresenter).onReadingSessionForBookRetrieved(sessionsArray)
            }
        })
    }

    /**
     * Persiste ReadingSessionDB using Room.
     */
    override fun addReadingSession(readingSession: ReadingSessionDB) {
        RoomOps().execute(readingSession)
    }

    /**
     * Fetch Status for a Book.
     */
    override fun fetchStatusForBook(book: Book) {
//       // use Room
//        val vehicleComponent = DaggerApplication.getComponent()
//        val vehicle = vehicleComponent.provideVehicle()
//        sampleDatabase = Room.databaseBuilder(vehicle.context,
//                SampleDatabase::class.java, "sample-db").build()
//        val booksList = sampleDatabase.daoAccess().fetchAllData()
//        booksList.observe(vehicle.lifecycle, object : Observer<List<UserStatus>> {
//            override fun onChanged(statusArray: List<UserStatus>?) {
//                //Update your UI here.
//                Log.d("Room", "Fetched all data")
//                for (status in statusArray!!) {
//                    Log.d("Status(Room)", status.user_status_id.toString())
//                }
////                mPresenter!!.onBookInserted(statusArray as ArrayList<BookDB>)
//            }
//        })

        CallAPI().call(mPresenter = mPresenter, act = CallAPI.API_ACTION.GET_STATUS)

    }

    /**
     * - private constructor is used to ensure this class can’t be initialized anywhere except inside of this class.
     * - init will be called when this class is initialized the first time i.e. when Singleton.instance called the very first.
     * -  Holder object & lazy instance is used to ensure only one instance of Singleton is created.
     */

    init {
        println("This ($this) is a singleton")
    }

    private object Holder {
        val INSTANCE = BooksModel()
    }

    companion object {
        val instance: BooksModel by lazy { Holder.INSTANCE }
    }

    // Presenter reference
    var mPresenter: Object? = null


//    private val sampleDatabase: SampleDatabase

    private var context: Context

    init {

        // fetch context
        val vehicleComponent = DaggerApplication.getComponent()
        val vehicle = vehicleComponent.provideVehicle()
        context = vehicle.context

        Toast.makeText(context, "SPEED", Toast.LENGTH_SHORT).show()

//        doSomth(vehicle)

    }


    private inner class RoomOps : AsyncTask<ReadingSessionDB, Void, Void>() {

        override fun onPreExecute() {
            super.onPreExecute()

            //Perform pre-adding operation here.
        }

        override fun doInBackground(vararg voids: ReadingSessionDB): Void? {
            // get list
            val sess = voids[0]

            //Let's add some dummy data to the database.
            val vehicleComponent = DaggerApplication.getComponent()
            val vehicle = vehicleComponent.provideVehicle()
            sampleDatabase = Room.databaseBuilder(vehicle.context,
                    SampleDatabase::class.java, "sample-db").build()
            // check if in DB
            sampleDatabase.daoAccess().insertReadingSession(sess)

            return null
        }

    }


    lateinit var sampleDatabase: SampleDatabase

    lateinit var lifecycle: LifecycleOwner

    /**
     * Fetch books from GoodReads.
     */
    override fun fetchBooks(shelf: String) {
        // LiveData code
        // fetch context
        val vehicleComponent = DaggerApplication.getComponent()
        val vehicle = vehicleComponent.provideVehicle()
        context = vehicle.context
        lifecycle = vehicle.lifecycle

        // get books from goodreads
        CallAPI().call(mPresenter, shelf, CallAPI.API_ACTION.GET_BOOKS)
    }


    fun fetchBooksRoom() {
        // LiveData code
        // fetch context
        val vehicleComponent = DaggerApplication.getComponent()
        val vehicle = vehicleComponent.provideVehicle()
        context = vehicle.context
        lifecycle = vehicle.lifecycle

        // use livedata with room
        sampleDatabase = Room.databaseBuilder(context,
                SampleDatabase::class.java, "sample-db").build()

        // LiveData
        // obeserve() first argument needs to extend LifeCycle (LifeCycleActivity)
//        val booksList = sampleDatabase.daoAccess().fetchAllData()
//        booksList.observe(vehicle.lifecycle, object : Observer<List<BookDB>> {
//            override fun onChanged(books: List<BookDB>?) {
//                //Update your UI here.
//                Log.d("Room", "Fetched all data")
//                for (univ in books!!) {
//                    Log.d("Univ", univ.elapsedTime + "")
//                }
//                mPresenter!!.onBookInserted(books as ArrayList<BookDB>)
//            }
//        })

        /**
         * CallAPI().call()
         */
    }

    /**
     * Sent from [MainPresenter.onDestroy]
     * Should stop/kill operations that could be running
     * and aren't needed anymore
     */
    override fun onDestroy() {
        // destroying actions
    }

    /**
     * Add a new BookDB.
     */
    override fun insertBook(book: Book) {
        // Live Data
//        Single.fromCallable {
//            sampleDatabase.daoAccess().insertOnlySingleRecord(book)
//        }.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    // Removes Note from DB
    override fun removeNote(note: Note) {
        // data business logic
        // ...
//        mPresenter!!.onNoteRemoved(note)
    }
}

private class DatabaseAsync : AsyncTask<List<UserStatus>, Void, Void>() {


    lateinit var sampleDatabase: SampleDatabase

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