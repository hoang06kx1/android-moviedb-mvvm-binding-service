package hoang.nguyen.androidmoviedb.ui.main

import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import hoang.nguyen.androidmoviedb.R
import hoang.nguyen.androidmoviedb.databinding.MainFragmentBinding
import hoang.nguyen.androidmoviedb.ui.base.ServiceBindingFragment
import hoang.nguyen.androidmoviedb.ui.base.viewLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : ServiceBindingFragment() {
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
        binding.recyclerView.adapter = adapter.withLoadStateAdapters(
            header = MovieLoadStateAdapter(adapter::retry),
            footer = MovieLoadStateAdapter(adapter::retry)
        )
        (binding.recyclerView.layoutManager as GridLayoutManager).spanSizeLookup =
            object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    // If progress will be shown then span size will be 1 otherwise it will be 2
                    return if (adapter.getItemViewType(position) == MovieAdapter.LOADING_ITEM) 1 else 2
                }
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

    private fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
        header: LoadStateAdapter<*>,
        footer: LoadStateAdapter<*>
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            header.loadState = loadStates.refresh
            footer.loadState = loadStates.append
        }
        return ConcatAdapter(header, this, footer)
    }
}