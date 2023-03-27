package hoang.nguyen.androidmoviedb.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import hoang.nguyen.androidmoviedb.data.remote.ApiAndroidService

abstract class AutoBindingFragment : Fragment() {
    abstract val onServiceConnection: (service: IBinder) -> Unit

    /** Defines callbacks for service binding */
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            onServiceConnection(service)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            // No-op
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(requireContext(), ApiAndroidService::class.java).also { intent ->
            requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unbindService(connection)
    }
}