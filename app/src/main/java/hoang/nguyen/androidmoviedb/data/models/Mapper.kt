package hoang.nguyen.androidmoviedb.data.models

import hoang.nguyen.androidmoviedb.data.remote.ApiConstants
import hoang.nguyen.androidmoviedb.data.remote.response.MovieListResponse

fun MovieListResponse.MovieItemResponse.toMovieItemModel(): MovieItemModel {
    return MovieItemModel(
        this.id,
        this.title,
        this.posterPath.orEmpty().asPosterUrl(),
        this.overview.orEmpty(),
        this.popularity,
        this.voteAverage,
        this.voteCount
    )


}

private fun String.asPosterUrl() =
    if (this.isEmpty()) ApiConstants.DEFAULT_POSTER_URL
    else ApiConstants.IMG_BASE_URL + "w342" + this

private fun String.asBackdropUrl() =
    if (this.isEmpty()) ApiConstants.DEFAULT_BACKDROP_URL
    else ApiConstants.IMG_BASE_URL + "w780" + this