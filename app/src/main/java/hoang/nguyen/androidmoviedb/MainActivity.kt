package hoang.nguyen.androidmoviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hoang.nguyen.androidmoviedb.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}