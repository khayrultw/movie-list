package com.khayrultw.movielist.ui.presentations.movies

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.khayrultw.movielist.R
import com.khayrultw.movielist.data.model.Movie
import com.khayrultw.movielist.databinding.FragmentMoviesBinding
import com.khayrultw.movielist.ui.adapters.MovieAdapter
import com.khayrultw.movielist.ui.presentations.base.BaseFragment
import com.khayrultw.movielist.ui.presentations.movies.viewModel.MoviesViewModelImpl

class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {
    override val viewModel: MoviesViewModelImpl by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun getLayoutResource(): Int = R.layout.fragment_movies

    override fun onBindView(view: View) {
        super.onBindView(view)

        binding?.viewModel = viewModel

        setupAdapter()
        setupSearchListener()

        binding?.srlMovies?.setOnRefreshListener {
            viewModel.onRefresh()
            binding?.etSearch?.setText("")
        }

        viewModel.items.observe(viewLifecycleOwner) {
            movieAdapter.setItems(it)
        }

        viewModel.swipeRefreshFinished.asLiveData().observe(viewLifecycleOwner) {
            binding?.srlMovies?.isRefreshing = false
        }
    }

    private fun setupAdapter() {
        movieAdapter = MovieAdapter(emptyList())
        val layoutManager = LinearLayoutManager(requireContext())
        binding?.rvMovies?.adapter = movieAdapter
        binding?.rvMovies?.layoutManager = layoutManager

        movieAdapter.listener = object : MovieAdapter.Listener {
            override fun onItemClicked(model: Movie) {
                navigateToMovieDetails(model)
            }
        }
    }

    private fun navigateToMovieDetails(model: Movie) {
        val action = MoviesFragmentDirections.actionMoviesToMovieDetails(model)
        navController.navigate(action)
    }

    private fun setupSearchListener() {
        binding?.etSearch?.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.onSearch(v.text.toString())
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun hideKeyboard() {
        val view = requireActivity().findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}