package com.wywrot.ewa.curriculumvitae.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wywrot.ewa.curriculumvitae.R
import com.wywrot.ewa.curriculumvitae.activity.MainActivity
import com.wywrot.ewa.curriculumvitae.adapter.ArticlesAdapter
import com.wywrot.ewa.curriculumvitae.viewmodel.ArticlesViewModel
import com.wywrot.ewa.curriculumvitae.viewmodel.Injector
import kotlinx.android.synthetic.main.fragment_recycler_view_swipe_and_empty_view.*

class MyArticlesFragment : Fragment() {
    private lateinit var adapter: ArticlesAdapter

    companion object {
        fun newInstance() = MyArticlesFragment()
            .apply { retainInstance = true }
    }

    private var receivedResponse: Int = 0

    private val viewModel: ArticlesViewModel by viewModels {
        Injector.provideArticleViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setActionBarTitle(R.string.my_articles)
        return inflater.inflate(
            R.layout.fragment_recycler_view_swipe_and_empty_view,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ArticlesAdapter()
        recyclerView.adapter = adapter

        swipeContainer.setOnRefreshListener {
            viewModel.fetchRecentDataFromRest()
        }

        subscribeUi()
        viewModel.fetchRecentDataFromRest()
    }

    private fun subscribeUi() {
        viewModel.articlesList?.observe(viewLifecycleOwner, Observer { articles ->
            adapter.setArticlesList(articles)
        })
        viewModel.downloadResponseStatus.observe(viewLifecycleOwner, Observer { response ->
            receivedResponse = response
            bindProgressAndEmptyView()
            swipeContainer.isRefreshing = false
        })
    }

    private fun bindProgressAndEmptyView() {
        val dataLoaded = (recyclerView.adapter as ArticlesAdapter).itemCount > 0
        val loadingFromServer = receivedResponse == 0

        when {
            dataLoaded -> {
                emptyView.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
            loadingFromServer -> {
                progressBar.visibility = View.VISIBLE
            }
            else -> {
                emptyView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }
}