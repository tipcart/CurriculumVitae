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
import com.wywrot.ewa.curriculumvitae.adapter.ExperienceAdapter
import com.wywrot.ewa.curriculumvitae.viewmodel.Injector
import com.wywrot.ewa.curriculumvitae.viewmodel.MyExperienceViewModel
import kotlinx.android.synthetic.main.fragment_recycler_view_swipe_and_empty_view.*

class MyExperienceFragment : Fragment() {
    private lateinit var adapter: ExperienceAdapter

    companion object {
        fun newInstance() = MyExperienceFragment()
            .apply { retainInstance = true }
    }

    private var receivedResponse: Int = 0

    private val viewModel: MyExperienceViewModel by viewModels {
        Injector.provideExperienceViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setActionBarTitle(R.string.experience)
        return inflater.inflate(
            R.layout.fragment_recycler_view_swipe_and_empty_view,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ExperienceAdapter()
        recyclerView.adapter = adapter

        swipeContainer.setOnRefreshListener {
            viewModel.fetchRecentDataFromRest()
        }

        subscribeUi()
        viewModel.fetchRecentDataFromRest()
    }

    private fun subscribeUi() {
        viewModel.experiencesList?.observe(viewLifecycleOwner, Observer { experiences ->
            adapter.setExperienceList(experiences)
        })
        viewModel.downloadResponseStatus.observe(viewLifecycleOwner, Observer { response ->
            receivedResponse = response
            bindProgressAndEmptyView()
            swipeContainer.isRefreshing = false
        })
    }

    private fun bindProgressAndEmptyView() {
        val dataLoaded = viewModel.experiencesList?.value != null

        if (dataLoaded) {

            emptyView.visibility = View.GONE
            progressBar.visibility = View.GONE
        } else if (receivedResponse != 0 && receivedResponse != 200) {

            emptyView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        } else {

            emptyView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }
}