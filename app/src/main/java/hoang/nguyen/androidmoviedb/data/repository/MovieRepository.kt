package hoang.nguyen.androidmoviedb.data.repository

import dev.hyuwah.moviedbexplorer.data.remote.model.MovieListResponse
import hoang.nguyen.androidmoviedb.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): Flow<NetworkResult<MovieListResponse>>
}