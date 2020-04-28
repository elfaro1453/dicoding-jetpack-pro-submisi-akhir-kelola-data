package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.local.DetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.AbsentLiveData
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.DetailMovieRepository
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.DetailItem
import javax.inject.Inject

class DetailMovieViewModel @Inject constructor(detailMovieRepository: DetailMovieRepository) :
    ViewModel() {
    private val _movieID: MutableLiveData<Int> = MutableLiveData()
    private val responseDetailMovie: LiveData<DetailItem<DetailMovie>>

    init {
        responseDetailMovie = _movieID.switchMap {
            _movieID.value?.let {
                detailMovieRepository.getDetailMovie(it)
            } ?: AbsentLiveData.create()
        }
    }

    val detail = responseDetailMovie.switchMap { it.itemDetail }
    val isBookmarked = responseDetailMovie.switchMap { it.bookmarkState }
    val networkState = responseDetailMovie.switchMap { it.networkState }

    fun setMovieID(movieId: Int) {
        _movieID.postValue(movieId)
    }

    fun refresh() {
        responseDetailMovie.value?.goRefresh?.invoke()
    }

    fun bookmark() {
        responseDetailMovie.value?.goBookmark?.invoke()
    }
}