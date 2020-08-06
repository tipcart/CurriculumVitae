package com.wywrot.ewa.curriculumvitae.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.wywrot.ewa.curriculumvitae.repository.BaseInfoRepository
import com.wywrot.ewa.curriculumvitae.repository.ExperienceRepository

interface ViewModelFactoryProvider {
    fun provideAboutMeViewModelFactory(context: Context): NewInstanceFactory
    fun provideExperienceViewModelFactory(context: Context): NewInstanceFactory
}

internal val Injector: ViewModelFactoryProvider
    get() = DefaultViewModelProvider

@Suppress("UNCHECKED_CAST")
private object DefaultViewModelProvider : ViewModelFactoryProvider {

    private fun getBaseInfoRepository(context: Context) =
        BaseInfoRepository.getInstance(context)

    private fun getExperienceRepository(context: Context) =
        ExperienceRepository.getInstance(context)

    override fun provideAboutMeViewModelFactory(context: Context) =
            object : NewInstanceFactory() {
                override fun <T : ViewModel> create(modelClass: Class<T>) =
                    AboutMeViewModel(
                        getBaseInfoRepository(context)
                    ) as T
            }

    override fun provideExperienceViewModelFactory(context: Context) =
        object : NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>) =
                MyExperienceViewModel(
                    getExperienceRepository(context)
                ) as T
        }
}