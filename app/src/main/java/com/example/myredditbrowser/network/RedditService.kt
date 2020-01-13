package com.example.myredditbrowser.network

import com.example.myredditbrowser.model.Example
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditService {

    @GET("/r/{subreddit}/.json")
    fun getFirstPage(@Path("subreddit") subreddit :String) : Call<Example>

    @GET("/r/{subreddit}/.json")
    fun getNextSlice(
        @Path("subreddit") subreddit: String,
        @Query("count") count: Int,
        @Query("after") after: String) : Call<Example>
}