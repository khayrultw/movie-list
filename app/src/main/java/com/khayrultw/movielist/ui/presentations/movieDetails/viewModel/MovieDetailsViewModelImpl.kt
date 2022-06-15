package com.khayrultw.movielist.ui.presentations.movieDetails.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.khayrultw.movielist.data.model.Movie
import com.khayrultw.movielist.data.repository.MovieRepositoryImpl
import com.khayrultw.movielist.ui.constants.ExtraCodes
import com.khayrultw.movielist.ui.presentations.base.viewModel.BaseViewModel

class MovieDetailsViewModelImpl(stateHandle: SavedStateHandle): BaseViewModel(), MovieDetailsViewModel {
    private val movieId: Movie? = stateHandle.get<Movie>(ExtraCodes.EXTRA_DATA)

    private val _movie: MutableLiveData<Movie> = MutableLiveData()
    override val movie: LiveData<Movie> = _movie

    override fun onViewCreated() {
        super.onViewCreated()
       movieId?.let {
           _movie.value = it
       }
    }
}