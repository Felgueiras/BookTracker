package com.example.rafae.booktracker.models.goodreadpsAPI

import retrofit2.Call
import retrofit2.http.*

/**
 * Created by felguiras on 26/09/2017.
 */
interface GoodReadsRESTClient {

//    @GET("/todo/api/v1.0/tasks")
//    fun tasksAll(): Call<TaskListREST>

    //    @FormUrlEncoded
//    @POST("/todo/api/v1.0/tasks")
//    fun addTask(
//            @Body elapsedTime: TaskREST
//    ): Call<TaskREST>

    @GET("/search.xml")
    fun searchByTopic(
            @Query("key") apiKey: String = "jqBqcyAQtVgqohUmiHyA",
            @Query("q") searchTopic: String = "EndersGame"
    ): Call<GoodReadResponse>

    @GET("/api/author_url/{authorName}")
    fun findAuthorByName(
            @Path("authorName") authorName: String = "Orson%20Scott%20Card",
            @Query("key") apiKey: String
    ): Call<GoodReadResponse>

    @GET("/review/list/46472494.xml")
    fun booksFromShelf(
            @Query("key") apiKey: String = "jqBqcyAQtVgqohUmiHyA",
            @Query("v") v: Int = 2,
            @Query("shelf") shelf: String = "currently-reading"
    ): Call<GoodReadResponse>

    @GET("/user/show/46472494.xml")
    fun getMemberInfo(
            @Query("key") apiKey: String = "jqBqcyAQtVgqohUmiHyA"
    ): Call<GoodReadResponse>

    @GET("/user_status/show/145948935?format=xml")
    fun getStatusInfo(
            @Query("key") apiKey: String = "jqBqcyAQtVgqohUmiHyA"
    ): Call<GoodReadResponse>

    @POST("/user_status.xml")
    fun updateUserStatus(
            @Query("book_id") bookId: String = "565058",
            @Query("elapsedTime") page: Int = 100
    ): Call<GoodReadResponse>

    @GET("/oauth/authorize")
    fun requestLogin(
            @Query("mobile") authorName: Int = 1

    ): Call<GoodReadResponse>


}
