package hoang.nguyen.androidmoviedb.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import hoang.nguyen.androidmoviedb.R
import hoang.nguyen.androidmoviedb.data.remote.BoundApiService
import hoang.nguyen.androidmoviedb.databinding.MainFragmentBinding
import hoang.nguyen.androidmoviedb.ui.components.viewLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val vm: MainViewModel by viewModel()
    private var binding: MainFragmentBinding by viewLifecycle()
    private var adapter = MovieAdapter {
        findNavController().navigate(
            R.id.action_mainFragment_to_movieDetailFragment,
            Bundle().apply {
                putParcelable("MOVIE", it)
            })
    }

    /** Defines callbacks for service binding, passed to bindService().  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.d("H.NH", "Connect service successfully")
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.d("H.NH", "Failed Connect service successfully")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(requireContext(), BoundApiService::class.java).also { intent ->
            requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unbindService(connection)
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