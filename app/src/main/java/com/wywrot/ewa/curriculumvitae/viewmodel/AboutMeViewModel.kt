package com.wywrot.ewa.curriculumvitae.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wywrot.ewa.curriculumvitae.repository.BaseInfoRepository
import com.wywrot.ewa.curriculumvitae.rest.BaseInfo

internal class AboutMeViewModel internal constructor(
    private val baseInfoRepository: BaseInfoRepository
) : AbstractViewModel() {

    var downloadResponseStatus: MutableLiveData<Int> = MutableLiveData(0)
    val baseInfo: LiveData<BaseInfo>? = baseInfoRepository.getBaseInfo()

    fun fetchRecentDataFromRest() {
        launchDataLoad {
            downloadResponseStatus.value = baseInfoRepository.getBaseInfoFromRest()
        }
    }
}
