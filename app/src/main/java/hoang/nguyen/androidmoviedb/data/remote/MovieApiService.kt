package hoang.nguyen.androidmoviedb.data.remote

import hoang.nguyen.androidmoviedb.data.remote.response.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular?region=US")
    suspend fun fetchPopularMovies(@Query("page") page: Int): Response<MovieListResponse>

    @GET("movie/{movieId}")
    suspend fun fetchMovieDetail(@Path("movieId") movieId: Int): Response<MovieListResponse.MovieItemResponse>
}