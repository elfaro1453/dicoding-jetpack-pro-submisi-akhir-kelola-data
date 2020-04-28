package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.favourite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import id.idn.fahru.belajarfundamentalaplikasiandroid.databinding.TVFragmentBinding
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter.FavouriteAdapter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class FavTvFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var binding: TVFragmentBinding
    private val favouriteViewModel: FavouriteViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutInflater = LayoutInflater.from(this.context)
        val tVFragmentBinding = TVFragmentBinding.inflate(layoutInflater, container, false)
        binding = tVFragmentBinding
        return tVFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapterRV = FavouriteAdapter()
        with(binding) {
            rvTvFragment.apply {
                adapter = adapterRV
                layoutManager = LinearLayoutManager(context)
            }
            favouriteViewModel.apply {
                getTvs().observe(
                    viewLifecycleOwner, Observer {
                        adapterRV.addList(it)
                    }
                )
            }
        }
    }
}
