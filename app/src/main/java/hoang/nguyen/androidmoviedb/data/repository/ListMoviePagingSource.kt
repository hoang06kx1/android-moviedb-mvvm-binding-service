package hoang.nguyen.androidmoviedb.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import hoang.nguyen.androidmoviedb.data.models.MovieItemModel
import hoang.nguyen.androidmoviedb.data.models.toMovieItemModel
import hoang.nguyen.androidmoviedb.data.remote.MovieApiService

class ListMoviePagingSource(
    private val movieApiService: MovieApiService,
) : PagingSource<Int, MovieItemModel>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MovieItemModel> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = movieApiService.fetchPopularMovies(nextPageNumber)
            return if (response.isSuccessful) {
                val body = response.body()!!
                LoadResult.Page(
                    data = (body.results
                        ?: emptyList()).map { it.toMovieItemModel() },
                    prevKey = null, // Only paging forward.
                    nextKey = nextPageNumber + 1
                )
            } else {
                LoadResult.Error(
                    Throwable(
                        "Rest API Response with failed: ${
                            response.errorBody()?.string() ?: "Unknown"
                        } "
                    )
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieItemModel>): Int? {
        return 1
    }
}
