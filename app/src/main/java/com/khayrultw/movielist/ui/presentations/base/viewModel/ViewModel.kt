package com.khayrultw.movielist.ui.presentations.base.viewModel

import kotlinx.coroutines.flow.SharedFlow

interface ViewModel {
    val snackbar: SharedFlow<Pair<String, Boolean>>
    val loading: SharedFlow<Boolean>

    fun onViewCreated()
    fun showSnackbar(message: String, error: Boolean)
    fun showLoader()
    fun hideLoader()
}