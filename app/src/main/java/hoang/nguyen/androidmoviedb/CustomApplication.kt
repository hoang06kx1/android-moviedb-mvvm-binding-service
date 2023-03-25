package hoang.nguyen.androidmoviedb

import android.app.Application
import hoang.nguyen.androidmoviedb.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@CustomApplication)
            // Load modules
            modules(appModule)
        }
    }
}