package com.khayrultw.movielist.ui.presentations.base.viewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel: androidx.lifecycle.ViewModel(), ViewModel {
    private val _snackbar: MutableSharedFlow<Pair<String, Boolean>> = MutableSharedFlow()
    override val snackbar: SharedFlow<Pair<String, Boolean>> = _snackbar

    private val _loading: MutableSharedFlow<Boolean> = MutableSharedFlow()
    override val loading: SharedFlow<Boolean> = _loading

    override fun onViewCreated() {}

    suspend fun <T> safeApiCall(apiCall: suspend () -> T?): T? {
        return try {
            apiCall.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
            showSnackbar(message = "Something went wrong", error = true)
            null
        }
    }

    override fun showSnackbar(message: String, error: Boolean) {
        viewModelScope.launch {
            _snackbar.emit(Pair(message, error))
        }
    }

    override fun showLoader() {
        viewModelScope.launch {
            _loading.emit(true)
        }
    }

    override fun hideLoader() {
        viewModelScope.launch {
            _loading.emit(false)
        }
    }
}
