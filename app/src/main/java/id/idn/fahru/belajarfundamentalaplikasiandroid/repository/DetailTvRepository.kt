package id.idn.fahru.belajarfundamentalaplikasiandroid.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import id.idn.fahru.belajarfundamentalaplikasiandroid.AppExecutors
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.service.TVService
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.local.DetailTv
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResponseReview
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.detail.ResponseDetailTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.NetworkState
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.DetailItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.DetailDao
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.TheMovieDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailTvRepository @Inject constructor(
    private val tvService: TVService,
    private val db: TheMovieDB,
    private val detailDao: DetailDao
) {

    @MainThread
    private fun bookmarkTv(
        tv: ResponseDetailTV
    ): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        AppExecutors().diskIO().execute {
            db.runInTransaction {
                val stat = detailDao.syncBookmarkTv(tv.id)
                if (stat) {
                    detailDao.bookmarkTv(false, tv.id)
                    status.postValue(false)
                } else {
                    detailDao.bookmarkTv(true, tv.id)
                    status.postValue(true)
                }
            }
        }
        return status
    }

    private fun tvState(tvId: Int, refresh: Boolean): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        AppExecutors().diskIO().execute {
            var isAnyData = detailDao.checkTVById(tvId)
            if (refresh) isAnyData = 0
            if (isAnyData < 1) {
                tvService.fetchTvById(tvId).enqueue(
                    object : Callback<ResponseDetailTV> {
                        override fun onFailure(call: Call<ResponseDetailTV>, t: Throwable) {
                            networkState.value = NetworkState.error(t.message)
                        }

                        override fun onResponse(
                            call: Call<ResponseDetailTV>,
                            response: Response<ResponseDetailTV>
                        ) {
                            response.body()?.let {
                                it.genres?.onEach { genreItem ->
                                    genreItem.genreOwnerId = it.id
                                }?.let { dataGenres ->
                                    AppExecutors().diskIO().execute {
                                        db.runInTransaction {
                                            detailDao.insertTv(it)
                                            detailDao.insertGenres(dataGenres)
                                        }
                                    }
                                }
                                networkState.postValue(NetworkState.LOADED)
                            }
                        }
                    }
                )
                tvService.fetchReviews(tvId).enqueue(
                    object : Callback<ResponseReview> {
                        override fun onFailure(call: Call<ResponseReview>, t: Throwable) {
                            // it's ok reviews being null
                            // networkState.value = NetworkState.error(t.message)
                        }

                        override fun onResponse(
                            call: Call<ResponseReview>,
                            response: Response<ResponseReview>
                        ) {
                            val reviewsTv = response.body()?.results
                            reviewsTv?.onEach {
                                it.reviewOwnerId = response.body()?.id
                            }?.let {
                                AppExecutors().diskIO().execute {
                                    db.runInTransaction {
                                        detailDao.insertReviews(it)
                                    }
                                }
                            }
                        }
                    }
                )
            } else {
                networkState.postValue(NetworkState.LOADED)
            }
        }
        return networkState
    }

    @MainThread
    fun getDetailTv(tvId: Int): LiveData<DetailItem<DetailTv>> {
        val liveData = MediatorLiveData<DetailItem<DetailTv>>()

        val itemDetail = detailDao.getTvById(tvId)
        val bookmarkState = detailDao.checkBookmarkTv(tvId)
        val networkState = tvState(tvId, false)

        liveData.addSource(itemDetail) {
            liveData.value = DetailItem(
                itemDetail,
                bookmarkState,
                networkState,
                { bookmarkTv(it.responseDetailTV) },
                { tvState(tvId, true) })
        }
        return liveData
    }
}