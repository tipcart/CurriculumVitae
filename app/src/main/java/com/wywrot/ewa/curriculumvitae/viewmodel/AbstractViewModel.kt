package com.wywrot.ewa.curriculumvitae.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal abstract class AbstractViewModel : ViewModel() {
    internal fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                block()
            } catch (error: Throwable) {
            }
        }
    }
}
