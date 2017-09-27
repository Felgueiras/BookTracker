package com.example.rafae.booktracker.models.spotifyAPI

import com.example.rafae.booktracker.models.goodreadpsAPI.GoodReadResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by felguiras on 26/09/2017.
 */
interface SpotifyRESTClient {

//    @GET("/todo/api/v1.0/tasks")
//    fun tasksAll(): Call<TaskListREST>

    //    @FormUrlEncoded
//    @POST("/todo/api/v1.0/tasks")
//    fun addTask(
//            @Body title: TaskREST
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


    @GET("/oauth/authorize")
    fun requestLogin(
            @Query("mobile") authorName: Int = 1

            ): Call<GoodReadResponse>

    @GET("/todo/api/v1.0/tasks/{taskID}")
    fun taskSingle(
            @Path("taskID") taskID: Int
    ): Call<GoodReadResponse>

    @DELETE("/todo/api/v1.0/tasks/{taskID}")
    fun deleteTask(
            @Path("taskID") taskID: Int
    ): Call<GoodReadResponse>

    @PUT("/todo/api/v1.0/tasks/{taskID}")
    fun updateTask(
            @Body task: GoodReadResponse,
            @Path("taskID") taskID: Int
    ): Call<GoodReadResponse>

}