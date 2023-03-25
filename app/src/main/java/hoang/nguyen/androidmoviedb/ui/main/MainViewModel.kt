package hoang.nguyen.androidmoviedb.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import hoang.nguyen.androidmoviedb.data.repository.ListMoviePagingSource
import hoang.nguyen.androidmoviedb.data.repository.MovieRepository

class MainViewModel(movieRepository: MovieRepository) : ViewModel() {
    val pagedMovies = Pager(
        config = PagingConfig(40)
    ) {
        movieRepository.getMoviePagingSource()
    }.flow
        .cachedIn(viewModelScope)
}