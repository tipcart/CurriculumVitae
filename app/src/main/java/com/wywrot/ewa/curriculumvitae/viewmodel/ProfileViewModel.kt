package com.wywrot.ewa.curriculumvitae.viewmodel

import com.wywrot.ewa.curriculumvitae.repository.ProfileRepository

internal class ProfileViewModel internal constructor(
    private val profileRepository: ProfileRepository
) : AbstractViewModel() {


    fun fetchRecentDataFromRest() {
        launchDataLoad {
            val x = profileRepository.getBaseInfo()
        }
    }
}
