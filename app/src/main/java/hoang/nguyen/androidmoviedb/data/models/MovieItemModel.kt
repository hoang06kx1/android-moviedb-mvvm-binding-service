package hoang.nguyen.androidmoviedb.data.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieItemModel(
    var id: Int,
    var title: String,
    var posterPath: String,
    var backdropPath: String,
    var overview: String,
    var popularity: Double,
    var voteAverage: Double,
    var voteCount: Int
) : Parcelable {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MovieItemModel>() {
            override fun areItemsTheSame(
                oldItem: MovieItemModel,
                newItem: MovieItemModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MovieItemModel,
                newItem: MovieItemModel
            ): Boolean = oldItem == newItem
        }
    }
}