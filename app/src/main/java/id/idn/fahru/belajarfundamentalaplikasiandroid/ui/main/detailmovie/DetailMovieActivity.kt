package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.detailmovie

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import id.idn.fahru.belajarfundamentalaplikasiandroid.R
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResultsItemMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.databinding.DetailMovieActivityBinding
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.Status
import javax.inject.Inject

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val sendMovieID = "movieID"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val detailMovieViewModel: DetailMovieViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding: DetailMovieActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.detail_movie_activity)
        val itemMovie = intent.getParcelableExtra(sendMovieID) as ResultsItemMovie
        binding.run {
            setSupportActionBar(movieDetailToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            title = itemMovie.title ?: ""
            lifecycleOwner = this@DetailMovieActivity
            detailMovie = detailMovieViewModel
        }
        detailMovieViewModel.run {
            setMovieID(itemMovie.id)
            networkState.observe(this@DetailMovieActivity, Observer {
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
