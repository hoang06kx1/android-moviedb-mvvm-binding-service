package hoang.nguyen.androidmoviedb.data.remote

import android.util.Log
import hoang.nguyen.androidmoviedb.data.remote.response.MovieListResponse
import kotlinx.coroutines.delay
import retrofit2.Response

class BoundApiService : MovieApiService {
    val assertIsServiceAvailable = {
        Log.d("Dev", "Service component:" + ServiceComponent.INSTANCE.get().toString())
        ServiceComponent.INSTANCE.get() ?: throw Exception("Api Service is not BOUND!")
    }

    /** Method for clients.  */
    override suspend fun fetchPopularMovies(page: Int): Response<MovieListResponse> {
        Log.d("Dev", "Before fetch Service component:" + ServiceComponent.INSTANCE.get().toString())
        delay(10000)
        return assertIsServiceAvailable().fetchPopularMovies(page)
    }
}