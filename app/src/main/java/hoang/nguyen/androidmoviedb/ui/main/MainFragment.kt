package hoang.nguyen.androidmoviedb.ui.main

import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import hoang.nguyen.androidmoviedb.R
import hoang.nguyen.androidmoviedb.databinding.MainFragmentBinding
import hoang.nguyen.androidmoviedb.ui.base.AutoBindingFragment
import hoang.nguyen.androidmoviedb.ui.components.viewLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : AutoBindingFragment() {
    private val vm: MainViewModel by viewModel()
    private var binding: MainFragmentBinding by viewLifecycle()
    var isViewCreated = MutableStateFlow(false)
    var isServiceConnected = MutableStateFlow(false)

    private var adapter = MovieAdapter {
        findNavController().navigate(
            R.id.action_mainFragment_to_movieDetailFragment,
            Bundle().apply {
                putInt("MOVIE_ID", it.id)
            })
    }

    override val onServiceConnection: (service: IBinder) -> Unit = {
        isServiceConnected.value = true
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
        isViewCreated.value = true
        binding.recyclerView.adapter = adapter.apply {
            withLoadStateFooter(MovieLoadStateAdapter(adapter::retry))
        }
        viewLifecycleOwner.lifecycleScope.launch {
            // Pager will fetch data automatically when there is a collector
            // So only collect data when View is ready && SERVICE BOUND
            isViewCreated.combine(isServiceConnected) { viewCreated, serviceConnected ->
                viewCreated && serviceConnected
            }.collect { bothCreated ->
                if (bothCreated) {
                    vm.pagedMovies.collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
        }
    }
}