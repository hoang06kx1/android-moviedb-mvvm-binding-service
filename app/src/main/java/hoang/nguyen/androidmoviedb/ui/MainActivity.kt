package hoang.nguyen.androidmoviedb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import hoang.nguyen.androidmoviedb.R
import hoang.nguyen.androidmoviedb.databinding.MainActivityBinding
import hoang.nguyen.androidmoviedb.ui.base.LoadingComponent

class MainActivity : AppCompatActivity(), LoadingComponent {

    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
    }

    override fun toggleLoading(isShow: Boolean) {
        binding.progressBar.isVisible = isShow
    }
}