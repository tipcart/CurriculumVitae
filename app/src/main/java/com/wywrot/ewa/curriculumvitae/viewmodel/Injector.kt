package com.wywrot.ewa.curriculumvitae.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.wywrot.ewa.curriculumvitae.repository.BaseInfoRepository

interface ViewModelFactoryProvider {
    fun provideAboutMeViewModelFactory(context: Context): NewInstanceFactory
}

internal val Injector: ViewModelFactoryProvider
    get() = DefaultViewModelProvider

@Suppress("UNCHECKED_CAST")
private object DefaultViewModelProvider : ViewModelFactoryProvider {

    private fun getBaseInfoRepository(context: Context) =
        BaseInfoRepository.getInstance(context)


    override fun provideAboutMeViewModelFactory(context: Context) =
        object : NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>) =
                AboutMeViewModel(
                    getBaseInfoRepository(context)
                ) as T
        }
}