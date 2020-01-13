package com.example.myredditbrowser.presenter

import com.example.myredditbrowser.model.Example

interface Contract {

    interface Presenter {
        fun retrieveFirstPage(subreddit : String)
        fun retrieveNextPage(subreddit: String, count: Int, after: String)
    }

    interface View {
        fun presentFirstPage(example: Example)
        fun presentNextPage(example: Example)
        fun presentError(message: String)
    }
}