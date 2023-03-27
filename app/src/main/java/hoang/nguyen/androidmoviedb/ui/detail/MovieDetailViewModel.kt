package hoang.nguyen.androidmoviedb.ui.detail

import androidx.lifecycle.ViewModel
import hoang.nguyen.androidmoviedb.data.repository.MovieRepository
import kotlinx.coroutines.flow.*

class MovieDetailViewModel(movieRepository: MovieRepository) : ViewModel() {
    private val loadMovie = MutableSharedFlow<Int>(extraBufferCapacity = 1).apply { buffer(1) }
    val movie = loadMovie.flatMapMerge { id ->
        movieRepository.fetchMovieDetail(id)
    }

    fun loadMovieDetail(movieId: Int) {
        loadMovie.tryEmit(movieId)
    }
}