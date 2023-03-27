package hoang.nguyen.androidmoviedb.data.repository

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import hoang.nguyen.androidmoviedb.data.remote.MovieApiService
import hoang.nguyen.androidmoviedb.data.remote.NetworkResult
import hoang.nguyen.androidmoviedb.data.remote.response.ApiErrorResponse
import hoang.nguyen.androidmoviedb.data.remote.response.MovieListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class MovieRepositoryImpl(private val gson: Gson, private val movieApiService: MovieApiService) :
    MovieRepository {
    private fun <T> fetchResult(apiCall: suspend () -> Response<T>): Flow<NetworkResult<T>> {
        return flow {
            emit(NetworkResult.Loading())
            try {
                val response = apiCall.invoke()
                if (response.isSuccessful && response.body() != null) {
                    emit(NetworkResult.Success(response.body()!!))
                } else {
                    try {
                        val apiError =
                            gson.fromJson(
                                response.errorBody()?.string(),
                                ApiErrorResponse::class.java
                            )
                        emit(NetworkResult.Error(apiError.error))
                    } catch (e: JsonSyntaxException) {
                        emit(NetworkResult.Error(response.errorBody()?.string() ?: ""))
                    }
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message))
            }
        }
    }

    override suspend fun fetchMovieDetail(movieId: Int): Flow<NetworkResult<MovieListResponse.MovieItemResponse>> {
        return fetchResult { movieApiService.fetchMovieDetail(movieId) }
    }

    override fun getMoviePagingSource(): ListMoviePagingSource {
        return ListMoviePagingSource(movieApiService)
    }
}