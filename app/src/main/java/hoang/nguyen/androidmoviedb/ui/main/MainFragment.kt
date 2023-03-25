package hoang.nguyen.androidmoviedb.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import hoang.nguyen.androidmoviedb.databinding.MainFragmentBinding
import hoang.nguyen.androidmoviedb.ui.components.viewLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val vm: MainViewModel by viewModel()
    private var binding: MainFragmentBinding by viewLifecycle()
    var adapter = MovieAdapter {
        // TODO
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter.apply {
            withLoadStateFooter(MovieLoadStateAdapter(adapter::retry))
        }
        viewLifecycleOwner.lifecycleScope.launch {
            vm.pagedMovies.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}