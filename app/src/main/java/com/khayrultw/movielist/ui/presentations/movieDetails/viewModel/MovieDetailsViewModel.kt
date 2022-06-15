package com.khayrultw.movielist.ui.presentations.movieDetails.viewModel

import androidx.lifecycle.LiveData
import com.khayrultw.movielist.data.model.Movie
import com.khayrultw.movielist.ui.presentations.base.viewModel.ViewModel

interface MovieDetailsViewModel: ViewModel {
    val movie: LiveData<Movie>
}