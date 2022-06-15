package com.khayrultw.movielist.ui.adapters

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khayrultw.movielist.R
import com.khayrultw.movielist.data.api.MovieListApi
import com.khayrultw.movielist.data.model.Movie
import com.khayrultw.movielist.databinding.ItemLayoutMovieBinding

class MovieAdapter(private var items: List<Movie>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createMovieItemViewHolder(parent)
    }

    private fun createMovieItemViewHolder(parent: ViewGroup): MovieItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemLayoutMovieBinding>(
            LayoutInflater.from(parent.context),
            movieItemLayoutId,
            parent,
            false
        )
        return MovieItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = items[position]
        if(holder is MovieItemViewHolder) {
            holder.bind(model)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Movie>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class MovieItemViewHolder(
        private val binding: ItemLayoutMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Movie) {
            binding.tvTitle.text = model.title
            binding.tvDate.text = model.releaseDate
            binding.tvRating.text = model.voteAverage.toString()
            binding.rbMovieRating.rating = model.voteAverage / 2
            binding.listener = View.OnClickListener {
                listener?.onItemClicked(model)
            }

            Glide.with(binding.root)
                .load(MovieListApi.POSTER_BASE_URL + model.posterPath)
                .into(binding.ivPoster)
        }
    }

    interface Listener {
        fun onItemClicked(model: Movie)
    }

    companion object {
        const val movieItemLayoutId: Int = R.layout.item_layout_movie
    }

}