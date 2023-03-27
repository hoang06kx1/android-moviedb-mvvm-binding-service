package hoang.nguyen.androidmoviedb.data.remote

import hoang.nguyen.androidmoviedb.data.remote.response.MovieListResponse
import retrofit2.Response

/***
 * A wrapper class around Android service component which is responsible for making Network Requests
 * The service should be bound before call any API request, otherwise a exception will be thrown
 */
class BoundMovieApiServiceImpl : MovieApiService {
    private val assertIsServiceAvailable =
        ApiAndroidService.INSTANCE.get() ?: throw Exception("Bound API Service is not bound yet!")


    /** Method for clients.  */
    override suspend fun fetchPopularMovies(page: Int): Response<MovieListResponse> {
        return assertIsServiceAvailable.fetchPopularMovies(page)
    }

    override suspend fun fetchMovieDetail(movieId: Int): Response<MovieListResponse.MovieItemResponse> {
        return assertIsServiceAvailable.fetchMovieDetail(movieId)
    }
}