package com.wywrot.ewa.curriculumvitae.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wywrot.ewa.curriculumvitae.repository.ArticlesRepository
import com.wywrot.ewa.curriculumvitae.repository.BaseInfoRepository
import com.wywrot.ewa.curriculumvitae.repository.ExperienceRepository
import com.wywrot.ewa.curriculumvitae.rest.Article
import com.wywrot.ewa.curriculumvitae.rest.BaseInfo
import com.wywrot.ewa.curriculumvitae.rest.Experience

internal class ArticlesViewModel internal constructor(
    private val articlesRepository: ArticlesRepository
) : AbstractViewModel() {

    var downloadResponseStatus: MutableLiveData<Int> = MutableLiveData(0)
    val articlesList: LiveData<List<Article>>? = articlesRepository.getArticles()

    fun fetchRecentDataFromRest() {
        launchDataLoad {
            downloadResponseStatus.value = articlesRepository.getArticlesFromRest()
        }
    }
}
