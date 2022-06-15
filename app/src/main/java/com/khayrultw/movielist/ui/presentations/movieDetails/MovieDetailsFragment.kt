package com.khayrultw.movielist.ui.presentations.movieDetails

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.khayrultw.movielist.R
import com.khayrultw.movielist.data.api.MovieListApi
import com.khayrultw.movielist.data.model.Movie
import com.khayrultw.movielist.databinding.FragmentMovieDetailsBinding
import com.khayrultw.movielist.databinding.ItemLayoutGenreBinding
import com.khayrultw.movielist.ui.constants.Genre
import com.khayrultw.movielist.ui.presentations.base.BaseFragment
import com.khayrultw.movielist.ui.presentations.movieDetails.viewModel.MovieDetailsViewModelImpl

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    override val viewModel: MovieDetailsViewModelImpl by viewModels()

    override fun getLayoutResource(): Int = R.layout.fragment_movie_details

    override fun onBindView(view: View) {
        super.onBindView(view)

        binding?.viewModel = viewModel

        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            displayMovieDetails(movie)
        }
    }

    private fun displayMovieDetails(movie: Movie) {
        binding?.let {
            it.tvTitle.text = movie.title
            it.tvDate.text = movie.releaseDate
            it.tvOverView.text = movie.overview
            it.rbMovieRating.rating = movie.voteAverage / 2
            it.tvRating.text = movie.voteAverage.toString()
            Glide.with(this)
                .load(MovieListApi.POSTER_BASE_URL + movie.posterPath)
                .into(it.ivPoster)
        }
        displayGenres(movie.genreIds)
    }

    private fun displayGenres(genreIds: List<Int>) {
        binding?.flGenreContainer?.let {
            it.removeAllViews()
            for (genreId in genreIds) {
                val genre = Genre.getById(genreId)
                val genreBinding = DataBindingUtil.inflate<ItemLayoutGenreBinding>(
                    layoutInflater,
                    R.layout.item_layout_genre,
                    null,
                    false
                )
                genreBinding.tvGenre.text = genre
                it.addView(genreBinding.root)
            }
        }
    }
}