package com.wywrot.ewa.curriculumvitae.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.wywrot.ewa.curriculumvitae.repository.ProfileRepository

interface ViewModelFactoryProvider {
    fun provideProfileViewModelFactory(context: Context): NewInstanceFactory
}

internal val Injector: ViewModelFactoryProvider
    get() = DefaultViewModelProvider

@Suppress("UNCHECKED_CAST")
private object DefaultViewModelProvider : ViewModelFactoryProvider {

    private fun getProfileRepository(context: Context) =
        ProfileRepository.getInstance(context)


    override fun provideProfileViewModelFactory(context: Context) =
        object : NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>) =
                ProfileViewModel(
                    getProfileRepository(context)
                ) as T
        }
}