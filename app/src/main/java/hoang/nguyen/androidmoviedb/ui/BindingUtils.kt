package hoang.nguyen.androidmoviedb.ui

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation

@BindingAdapter("imageUrl")
fun loadImage(view: AppCompatImageView, url: String?) {
    url?.let {
        view.load(it) {
            crossfade(true)
            transformations(RoundedCornersTransformation(16f, 16f, 0f, 0f))
        }
    }
}
