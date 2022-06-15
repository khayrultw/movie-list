package com.khayrultw.movielist.ui.presentations.movies.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.khayrultw.movielist.data.model.Movie
import com.khayrultw.movielist.data.repository.MovieRepositoryImpl
import com.khayrultw.movielist.ui.presentations.base.viewModel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MoviesViewModelImpl : BaseViewModel(), MoviesViewModel {
    private val lang = "en-US"
    private val query = "a"
    private val region = "US"
    private val year = "2020"
    private val releaseYear = "2020"
    private var page = 1

    private val movieRepository = MovieRepositoryImpl.instance
    private var movies: List<Movie> = emptyList()

    private val _items: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>().apply { value = emptyList() }
    override val items: LiveData<List<Movie>> = _items

    private val _swipeRefreshFinished: MutableSharedFlow<Unit> = MutableSharedFlow()
    override val swipeRefreshFinished: SharedFlow<Unit> = _swipeRefreshFinished

    private val _emptyScreen = MutableLiveData<Boolean>().apply { value = true }
    override val emptyScreen: LiveData<Boolean> = _emptyScreen

    override fun onViewCreated() {
        super.onViewCreated()
        viewModelScope.launch {
            showLoader()
            getMovies()
            hideLoader()
        }
    }

    private suspend fun getMovies() {
        val res = safeApiCall {
            movieRepository.getMovies(lang, releaseYear, page, region, year, query)
        }
        res?.let {
            _items.value = it
            movies = it
        }
        _emptyScreen.value = items.value.isNullOrEmpty()
    }

    override fun onSearch(text: String) {
        _items.value = movies.filter { movie -> movie.title.contains(text, ignoreCase = true) }
        _emptyScreen.value = _items.value.isNullOrEmpty()
    }

    override fun onRefresh() {
        viewModelScope.launch {
            getMovies()
            _swipeRefreshFinished.emit(Unit)
        }

    }
}