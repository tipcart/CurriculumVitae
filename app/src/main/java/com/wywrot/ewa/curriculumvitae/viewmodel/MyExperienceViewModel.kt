package com.wywrot.ewa.curriculumvitae.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wywrot.ewa.curriculumvitae.repository.BaseInfoRepository
import com.wywrot.ewa.curriculumvitae.repository.ExperienceRepository
import com.wywrot.ewa.curriculumvitae.rest.BaseInfo
import com.wywrot.ewa.curriculumvitae.rest.Experience

internal class MyExperienceViewModel internal constructor(
    private val experienceRepository: ExperienceRepository
) : AbstractViewModel() {

    var downloadResponseStatus: MutableLiveData<Int> = MutableLiveData(0)
    val experiencesList: LiveData<List<Experience>>? = experienceRepository.getExperience()

    fun fetchRecentDataFromRest() {
        launchDataLoad {
            downloadResponseStatus.value = experienceRepository.getExperienceFromRest()
        }
    }
}
