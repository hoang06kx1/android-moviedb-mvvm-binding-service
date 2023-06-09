package hoang.nguyen.androidmoviedb.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import hoang.nguyen.androidmoviedb.data.models.MovieItemModel
import hoang.nguyen.androidmoviedb.databinding.ItemMovieBinding

class MovieAdapter(
    val onItemClick: (item: MovieItemModel) -> Unit
) : PagingDataAdapter<MovieItemModel, MovieAdapter.ViewHolder>(MovieItemModel.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item)
    }

    inner class ViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieItemModel) = with(binding) {
            this.adapter = this@MovieAdapter
            this.movie = item
        }
    }


    companion object {
        // Define Loading ViewType
        const val LOADING_ITEM = 0

        // Define Movie ViewType
        const val MOVIE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        // set ViewType
        return if (position == itemCount) MOVIE_ITEM else LOADING_ITEM
    }

}