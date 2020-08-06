package com.wywrot.ewa.curriculumvitae.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.wywrot.ewa.curriculumvitae.R
import com.wywrot.ewa.curriculumvitae.activity.MainActivity
import com.wywrot.ewa.curriculumvitae.rest.BaseInfo
import com.wywrot.ewa.curriculumvitae.viewmodel.Injector
import com.wywrot.ewa.curriculumvitae.viewmodel.AboutMeViewModel
import kotlinx.android.synthetic.main.fragment_about_me.*

class AboutMeFragment : Fragment() {

    companion object {
        fun newInstance() = AboutMeFragment()
            .apply { retainInstance = true }
    }

    private var receivedResponse: Int = 0

    private val viewModel: AboutMeViewModel by viewModels {
        Injector.provideAboutMeViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setActionBarTitle(R.string.about_me)
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeContainer.setOnRefreshListener {
            viewModel.fetchRecentDataFromRest()
        }

        subscribeUi()
        viewModel.fetchRecentDataFromRest()
    }

    private fun subscribeUi() {
        viewModel.baseInfo?.observe(viewLifecycleOwner, Observer { baseInfo ->
            bindData(baseInfo)
        })
        viewModel.downloadResponseStatus.observe(viewLifecycleOwner, Observer { response ->
            receivedResponse = response
            bindProgressAndEmptyView()
            swipeContainer.isRefreshing = false
        })
    }

    private fun bindData(baseInfo: BaseInfo?) {
        name.text = baseInfo?.name
        jobTitle.text = baseInfo?.jobTitle
        phone.text = context?.getString(R.string.phone_number, baseInfo?.phone)
        email.text = context?.getString(R.string.email_address, baseInfo?.email)
        summaryContent.text = baseInfo?.summaryContent
    }

    private fun bindProgressAndEmptyView() {
        val dataLoaded = viewModel.baseInfo?.value != null

        if (dataLoaded) {
            contentContainer.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
            progressBar.visibility = View.GONE
        } else if (receivedResponse != 0 && receivedResponse != 200) {
            contentContainer.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        } else {
            contentContainer.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }
}