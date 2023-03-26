package hoang.nguyen.androidmoviedb.data.remote

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import hoang.nguyen.androidmoviedb.data.remote.response.MovieListResponse
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import retrofit2.Response
import java.lang.ref.WeakReference

class ServiceComponent : Service() {
    init {
        INSTANCE = WeakReference(this)
    }

    private val okHttpServiceImpl: MovieApiService by inject(named("OkHttp"))

    // Binder given to clients.
    private val binder = LocalBinder()

    /** Method for clients.  */
    suspend fun fetchPopularMovies(page: Int): Response<MovieListResponse> {
        Log.d("DEV", "Begin to fetch poupar movies $page")
        return okHttpServiceImpl.fetchPopularMovies(page)
    }

    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods.
        // fun getService(): BoundApiService = this@BoundApiService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    companion object {
        var INSTANCE: WeakReference<ServiceComponent> = WeakReference(null)
    }
}