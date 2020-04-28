package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.fragmenttv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.discover.ResultsItemTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.AbsentLiveData
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.DiscoverTvRepository
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.Listing
import javax.inject.Inject

class FragmentTVViewModel @Inject constructor(discoverTvRepository: DiscoverTvRepository) :
    ViewModel() {
    private val _pageListMovies: MutableLiveData<Boolean> = MutableLiveData()
    private val repoResult: LiveData<Listing<ResultsItemTV>>

    init {
        _pageListMovies.postValue(true)
        repoResult = _pageListMovies.switchMap { _ ->
            _pageListMovies.value?.let { discoverTvRepository.setListTvs(it) }
                ?: AbsentLiveData.create()
        }
    }

    val dataDiscovery = repoResult.switchMap { it.pagedList }
    val networkState = repoResult.switchMap { it.networkState }
    val refreshState = repoResult.switchMap { it.networkState }

    fun refresh() {
        _pageListMovies.value?.let {
            _pageListMovies.postValue(!it)
        }
    }

    fun retry() = repoResult.value?.retry?.invoke()
}