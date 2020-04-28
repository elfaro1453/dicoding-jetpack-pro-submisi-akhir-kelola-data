package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.detailtv

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import id.idn.fahru.belajarfundamentalaplikasiandroid.R
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.discover.ResultsItemTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.databinding.DetailTvActivityBinding
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.Status
import javax.inject.Inject

class DetailTvActivity : AppCompatActivity() {

    companion object {
        const val sendTvID = "TV_ID"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val detailTvViewModel: DetailTvViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding: DetailTvActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.detail_tv_activity)
        val itemTv = intent.getParcelableExtra(sendTvID) as ResultsItemTV
        detailTvViewModel.setTvID(itemTv.id)
        binding.run {
            setSupportActionBar(movieDetailToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            title = itemTv.name ?: ""
            lifecycleOwner = this@DetailTvActivity
            detailTv = detailTvViewModel
        }
        detailTvViewModel.run {
            setTvID(itemTv.id)
            networkState.observe(this@DetailTvActivity, Observer {
                if (it.status == Status.FAILED) {
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.connection_error),
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(resources.getString(R.string.retry)) {
                        refresh()
                    }.show()
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
