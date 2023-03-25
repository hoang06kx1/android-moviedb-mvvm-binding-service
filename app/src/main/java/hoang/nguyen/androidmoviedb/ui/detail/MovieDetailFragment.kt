package hoang.nguyen.androidmoviedb.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hoang.nguyen.androidmoviedb.databinding.MovieDetailFragmentBinding
import hoang.nguyen.androidmoviedb.ui.components.viewLifecycle

class MovieDetailFragment : Fragment() {
    private var binding: MovieDetailFragmentBinding by viewLifecycle()

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
        binding.movie = requireArguments().getParcelable("MOVIE")!!
    }
}