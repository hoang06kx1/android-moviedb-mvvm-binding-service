package hoang.nguyen.androidmoviedb.data.remote

import hoang.nguyen.androidmoviedb.data.remote.response.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular?region=US")
    suspend fun fetchPopularMovies(@Query("page") page: Int): Response<MovieListResponse>
}