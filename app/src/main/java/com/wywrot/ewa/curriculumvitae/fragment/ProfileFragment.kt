package com.wywrot.ewa.curriculumvitae.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wywrot.ewa.curriculumvitae.R
import com.wywrot.ewa.curriculumvitae.activity.MainActivity
import com.wywrot.ewa.curriculumvitae.viewmodel.Injector
import com.wywrot.ewa.curriculumvitae.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
            .apply { retainInstance = true }
    }

    private val viewModel: ProfileViewModel by viewModels {
        Injector.provideProfileViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setActionBarTitle(R.string.profile)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind()
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchRecentDataFromRest()

        swipeContainer.setOnRefreshListener {
            viewModel.fetchRecentDataFromRest()
        }
    }

    private fun bind() {
        // if there is data to display
        contentContainer.visibility = View.VISIBLE

        //todo get data from rest
        name.text = "Ewa Wywrot"
        jobTitle.text = "Android Developer"
        phone.text = "phone: +48123456789"
        email.text = "e-mail: xxx.xxx@xx.xxx"
        summaryContent.text = getText(R.string.lorem_ipsum)
    }
}