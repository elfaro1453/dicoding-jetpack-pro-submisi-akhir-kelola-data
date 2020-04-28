package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.fragmentmovie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import dagger.android.support.AndroidSupportInjection
import id.idn.fahru.belajarfundamentalaplikasiandroid.databinding.MovieFragmentBinding
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.NetworkState
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter.NetworkStateAdapter
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter.RVFragmentMovieAdapter
import javax.inject.Inject

class MovieFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var binding: MovieFragmentBinding
    private val movieViewModel: FragmentMovieViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutInflater = LayoutInflater.from(this.context)
        val movieFragmentBinding = MovieFragmentBinding.inflate(layoutInflater, container, false)
        binding = movieFragmentBinding
        return movieFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapterRV = RVFragmentMovieAdapter()
        val adapterNetwork = NetworkStateAdapter {
            movieViewModel.retry()
        }
        with(binding) {
            swipeContainer.apply {
                setOnRefreshListener {
                    movieViewModel.refresh()
                }
            }
            rvMovieFragment.apply {
                adapter = MergeAdapter(adapterRV, adapterNetwork)
                layoutManager = GridLayoutManager(context, 2)
            }
            movieViewModel.apply {
                refresh()
                refreshState.observe(viewLifecycleOwner, Observer {
                    swipeContainer.isRefreshing = it == NetworkState.LOADING
                })
                dataDiscovery.observe(
                    viewLifecycleOwner, Observer {
                        adapterRV.apply {
                            submitList(it)
                        }
                    }
                )
                networkState.observe(
                    viewLifecycleOwner, Observer {
                        adapterNetwork.networkState = it
                        swipeContainer.isRefreshing = it == NetworkState.LOADING
                    }
                )
            }
        }
    }
}
