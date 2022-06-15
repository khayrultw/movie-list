package com.khayrultw.movielist.ui.presentations.movies.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khayrultw.movielist.data.model.Movie
import com.khayrultw.movielist.ui.presentations.base.viewModel.ViewModel
import kotlinx.coroutines.flow.SharedFlow

interface MoviesViewModel: ViewModel {
    val items: LiveData<List<Movie>>
    val swipeRefreshFinished: SharedFlow<Unit>
    val emptyScreen: LiveData<Boolean>

    fun onSearch(text: String)
    fun onRefresh()
}