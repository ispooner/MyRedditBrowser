package com.example.myredditbrowser.network

import android.widget.Toast
import com.example.myredditbrowser.model.Example
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RedditInstance(var callback : retrofitCallbacks) {

    interface retrofitCallbacks {
        fun onCallFailure(message: String)
        fun onFirstCallSuccess(page: Example)
        fun onNextCallSuccess(example: Example)
    }



    private val BASE_URL = "https://www.reddit.com"

    private var redditService : RedditService = createRedditService(getInstance())

    private fun getInstance() : Retrofit {
        val client = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun createRedditService(retrofit: Retrofit) : RedditService {
        return retrofit.create(RedditService::class.java)
    }

    fun getFirstPage(subreddit: String) {
        return redditService.getFirstPage(subreddit).enqueue(object : Callback<Example> {
            override fun onFailure(call: Call<Example>, t: Throwable) {
                callback.onCallFailure("Failed to retrieve page")
            }

            override fun onResponse(call: Call<Example>, response: Response<Example>) {
                if(response.body() == null) {
                    callback.onCallFailure("Retrieved null")
                }
                else {
                    callback.onFirstCallSuccess(response.body()!!)
                }
            }

        } )
    }

    fun getNextPage(subreddit: String, count: Int, after: String) {
        return redditService.getNextSlice(subreddit, count, after).enqueue(object : Callback<Example> {
            override fun onFailure(call: Call<Example>, t: Throwable) {
                callback.onCallFailure("Failed to retrieve page")
            }

            override fun onResponse(call: Call<Example>, response: Response<Example>) {
                if(response.body() == null) {
                    callback.onCallFailure("Retrieved null")
                }
                else {
                    callback.onNextCallSuccess(response.body()!!)
                }
            }

        } )
    }

}