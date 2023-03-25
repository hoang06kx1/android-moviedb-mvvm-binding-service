package hoang.nguyen.androidmoviedb.data.repository

import hoang.nguyen.androidmoviedb.data.remote.response.MovieListResponse
import hoang.nguyen.androidmoviedb.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchPopularMovies(page: Int): Flow<NetworkResult<MovieListResponse>>
    fun getMoviePagingSource(): ListMoviePagingSource
}