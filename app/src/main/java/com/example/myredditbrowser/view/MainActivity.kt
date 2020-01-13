package com.example.myredditbrowser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myredditbrowser.R
import com.example.myredditbrowser.adapter.PostAdapter
import com.example.myredditbrowser.model.Child
import com.example.myredditbrowser.model.Data_
import com.example.myredditbrowser.model.Example
import com.example.myredditbrowser.presenter.Contract
import com.example.myredditbrowser.presenter.RedditPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Contract.View, PostAdapter.RedditAdapterDelegate{

    //postAdapter delegate
    override fun redditCallback(data_: Data_) {
        //Not really using this...
    }

    val presenter : Contract.Presenter = RedditPresenter(this)

    lateinit var example: Example
    lateinit var postAdapter: PostAdapter

    override fun presentFirstPage(example: Example) {
        this.example = example

        postAdapter = PostAdapter(example.data.children, this)
        main_recyclerView.adapter = postAdapter
        main_recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun presentNextPage(example: Example) {
        //Add to the thing
        this.example = example
        val size = postAdapter.results.size
        postAdapter.results.addAll(example.data.children)
        main_recyclerView.adapter = postAdapter
        main_recyclerView.scrollToPosition(size)
    }

    override fun presentError(message: String) {
        Toast.makeText(this, "message", Toast.LENGTH_SHORT).show();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_searchView.setQuery("funny", false)

        main_searchButton.setOnClickListener {
            presenter.retrieveFirstPage(main_searchView.query.toString().trim())
        }

        main_recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // scroll up: -1, down: 1
                if(!recyclerView.canScrollVertically(-1)) {
                    //reretrieve the first page from the query
                    //presenter.retrieveFirstPage(main_searchView.query.toString().trim())
                }
                if(!recyclerView.canScrollVertically(1)) {
                    //retrieve the next page.
                    presenter.retrieveNextPage(
                        main_searchView.query.toString().trim(),
                        0,
                        example.data.after
                        )
                }
            }
        })

        presenter.retrieveFirstPage(main_searchView.query.toString().trim())
    }


}
