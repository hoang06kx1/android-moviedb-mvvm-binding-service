package hoang.nguyen.androidmoviedb.di

import com.google.gson.Gson
import hoang.nguyen.androidmoviedb.BuildConfig
import hoang.nguyen.androidmoviedb.data.remote.ApiConstants.API_BASE_URL
import hoang.nguyen.androidmoviedb.data.remote.MovieApiServiceImpl
import hoang.nguyen.androidmoviedb.data.remote.MovieApiService
import hoang.nguyen.androidmoviedb.data.repository.MovieRepository
import hoang.nguyen.androidmoviedb.data.repository.MovieRepositoryImpl
import hoang.nguyen.androidmoviedb.ui.detail.MovieDetailViewModel
import hoang.nguyen.androidmoviedb.ui.main.MainViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<Interceptor>(named("LogInterceptor")) {
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
        }
    }

    // OkHttpClients
    single {
        OkHttpClient.Builder()
            .addInterceptor {
                val oldReq = it.request()
                val newUrl = oldReq.url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()
                val newReq = oldReq.newBuilder().url(newUrl).build()
                it.proceed(newReq)
            }
            .addInterceptor(get<Interceptor>(named("LogInterceptor")))
            .build()
    }

    // MovieApiServiceImpl : OkHttp Implementation of MovieApiService
    single<MovieApiService>(named("OkHttp")) {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(MovieApiService::class.java)
    }

    // MovieApiServiceImpl : BoundService implementation
    single<MovieApiService>(named("BoundService")) {
        MovieApiServiceImpl()
    }

    // Passed bound service API wrapper instead of OkHttp implementation
    // So all API requests need go through bound service
    single<MovieRepository> { MovieRepositoryImpl(Gson(), get(named("BoundService"))) }

    viewModel { MainViewModel(get<MovieRepository>()) }
    viewModel { MovieDetailViewModel(get<MovieRepository>()) }
}