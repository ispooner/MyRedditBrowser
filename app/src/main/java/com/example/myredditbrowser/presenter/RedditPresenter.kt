package com.example.myredditbrowser.presenter

import com.example.myredditbrowser.model.Example
import com.example.myredditbrowser.network.RedditInstance

class RedditPresenter(var view : Contract.View) : Contract.Presenter, RedditInstance.retrofitCallbacks {


    // Retrofit callbacks
    override fun onCallFailure(message: String) {
        view.presentError(message)
    }

    override fun onFirstCallSuccess(example: Example) {
        view.presentFirstPage(example)
    }

    override fun onNextCallSuccess(example: Example) {
        view.presentNextPage(example)
    }

    val redditInstance : RedditInstance = RedditInstance(this)

    //Presenter functions
    override fun retrieveFirstPage(subreddit: String) {
        redditInstance.getFirstPage(subreddit)
    }

    override fun retrieveNextPage(subreddit: String, count: Int, after: String) {
        redditInstance.getNextPage(subreddit, count, after)
    }
}