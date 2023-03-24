package hoang.nguyen.androidmoviedb.data.remote

import dev.hyuwah.moviedbexplorer.data.remote.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular?region=US")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MovieListResponse>
}