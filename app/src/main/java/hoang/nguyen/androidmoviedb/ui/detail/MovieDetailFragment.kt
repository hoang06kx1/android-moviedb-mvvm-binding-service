package hoang.nguyen.androidmoviedb.ui.detail

import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import hoang.nguyen.androidmoviedb.data.models.toMovieItemModel
import hoang.nguyen.androidmoviedb.data.remote.NetworkResult
import hoang.nguyen.androidmoviedb.databinding.MovieDetailFragmentBinding
import hoang.nguyen.androidmoviedb.ui.AutoBindingFragment
import hoang.nguyen.androidmoviedb.ui.components.viewLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : AutoBindingFragment() {
    private val viewModel: MovieDetailViewModel by viewModel()
    private var binding: MovieDetailFragmentBinding by viewLifecycle()

    override val onServiceConnection: (service: IBinder) -> Unit = {
        viewModel.loadMovieDetail(requireArguments().getInt("MOVIE_ID"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.movie.collect {
                    when (it) {
                        is NetworkResult.Loading -> {}
                        is NetworkResult.Success -> {
                            binding.movie = it.data!!.toMovieItemModel()
                        }
                        is NetworkResult.Error -> {

                        }
                    }
                }
            }
        }
    }
}