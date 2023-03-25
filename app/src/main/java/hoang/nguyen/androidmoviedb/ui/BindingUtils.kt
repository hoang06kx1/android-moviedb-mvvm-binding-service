package hoang.nguyen.androidmoviedb.ui

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import hoang.nguyen.androidmoviedb.data.models.MovieItemModel
import hoang.nguyen.androidmoviedb.ui.main.MovieAdapter

@BindingAdapter("imageUrl")
fun loadImage(view: AppCompatImageView, url: String) {
    view.load(url) {
        crossfade(true)
        transformations(RoundedCornersTransformation(16f, 16f, 0f, 0f))
    }
}
