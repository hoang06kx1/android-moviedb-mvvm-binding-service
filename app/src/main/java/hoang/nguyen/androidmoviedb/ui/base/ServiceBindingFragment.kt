package hoang.nguyen.androidmoviedb.ui.base

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import hoang.nguyen.androidmoviedb.data.remote.ApiAndroidService
import java.lang.ref.WeakReference

/***
 * Base fragment which auto bind to ApiAndroidService when it is created and unbind when it is destroyed
 */
abstract class ServiceBindingFragment : Fragment() {
    abstract val onServiceConnection: (service: IBinder) -> Unit

    /** Defines callbacks for service binding */
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            ApiAndroidService.INSTANCE =
                WeakReference((service as ApiAndroidService.LocalBinder).getService())
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

    protected fun toggleLoading(isShow: Boolean) {
        (requireActivity() as LoadingComponent).toggleLoading(isShow)
    }
}