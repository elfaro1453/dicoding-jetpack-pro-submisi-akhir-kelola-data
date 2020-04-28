package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.detailtv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.local.DetailTv
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.AbsentLiveData
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.DetailTvRepository
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.DetailItem
import javax.inject.Inject

class DetailTvViewModel @Inject constructor(detailTvRepository: DetailTvRepository) : ViewModel() {
    private val _tvID: MutableLiveData<Int> = MutableLiveData()
    private val responseDetailTv: LiveData<DetailItem<DetailTv>>

    init {
        responseDetailTv = _tvID.switchMap {
            _tvID.value?.let {
                detailTvRepository.getDetailTv(it)
            } ?: AbsentLiveData.create()
        }
    }

    val detail = responseDetailTv.switchMap { it.itemDetail }
    val isBookmarked = responseDetailTv.switchMap { it.bookmarkState }
    val networkState = responseDetailTv.switchMap { it.networkState }

    fun setTvID(tvId: Int) {
        _tvID.postValue(tvId)
    }

    fun refresh() {
        responseDetailTv.value?.goRefresh?.invoke()
    }

    fun bookmark() {
        responseDetailTv.value?.goBookmark?.invoke()
    }
}