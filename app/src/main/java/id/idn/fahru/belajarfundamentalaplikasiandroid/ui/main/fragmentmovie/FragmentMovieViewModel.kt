package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.fragmentmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResultsItemMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.AbsentLiveData
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.DiscoverMovieRepository
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.Listing
import javax.inject.Inject

class FragmentMovieViewModel @Inject constructor(discoverMovieRepository: DiscoverMovieRepository) :
    ViewModel() {
    private val _pageListMovies: MutableLiveData<Boolean> = MutableLiveData()
    private val repoResult: LiveData<Listing<ResultsItemMovie>>

    init {
        _pageListMovies.postValue(true)
        repoResult = _pageListMovies.switchMap { _ ->
            _pageListMovies.value?.let {
                discoverMovieRepository.setListMovies(it)
            } ?: AbsentLiveData.create()
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