package id.idn.fahru.belajarfundamentalaplikasiandroid.repository.boundarycallback

import androidx.annotation.MainThread
import androidx.paging.PagedList
import id.idn.fahru.belajarfundamentalaplikasiandroid.AppExecutors
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.service.DiscoverService
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResponseMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResultsItemMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.discover.ResponseTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.discover.ResultsItemTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.PagingRequestHelper
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.createStatusLiveData
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.DiscoveryDao
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.TheMovieDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DiscoverBoundaryCallback<T>(
    private val discoverService: DiscoverService,
    private val db: TheMovieDB,
    private val discoveryDao: DiscoveryDao,
    private val item: Class<T>
) : PagedList.BoundaryCallback<T>() {

    val helper =
        PagingRequestHelper(
            AppExecutors().diskIO()
        )
    val networkState = helper.createStatusLiveData()

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            when (item.simpleName) {
                "ResultsItemMovie" -> {
                    discoverService.fetchDiscoverMovie(1).enqueue(createWebserviceCallbackMovie(it))
                }
                "ResultsItemTV" -> {
                    discoverService.fetchDiscoverTv(1).enqueue(createWebserviceCallbackTv(it))
                }
            }
        }
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: T) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            when (itemAtEnd) {
                is ResultsItemMovie -> {
                    val page = itemAtEnd.indexPage + 1
                    discoverService.fetchDiscoverMovie(page)
                        .enqueue(createWebserviceCallbackMovie(it))
                }
                is ResultsItemTV -> {
                    val page = itemAtEnd.indexPage + 1
                    discoverService.fetchDiscoverTv(page).enqueue(createWebserviceCallbackTv(it))
                }
            }
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: T) {
        // ignored, since we only ever append to what's in the DB
    }

    private fun createWebserviceCallbackMovie(requestCallback: PagingRequestHelper.Request.Callback)
            : Callback<ResponseMovie> {
        return object : Callback<ResponseMovie> {
            override fun onFailure(
                call: Call<ResponseMovie>,
                t: Throwable
            ) {
                requestCallback.recordFailure(t)
            }

            override fun onResponse(
                call: Call<ResponseMovie>,
                response: Response<ResponseMovie>
            ) {
                val page = response.body()?.page ?: 0
                val items = response.body()?.results
                items?.let {
                    AppExecutors().diskIO().execute {
                        db.runInTransaction {
                            it.forEach {
                                it.indexPage = page
                            }
                            discoveryDao.insertAllMovie(it)
                        }
                        requestCallback.recordSuccess()
                    }
                }
            }
        }
    }

    private fun createWebserviceCallbackTv(requestCallback: PagingRequestHelper.Request.Callback)
            : Callback<ResponseTV> {
        return object : Callback<ResponseTV> {
            override fun onFailure(
                call: Call<ResponseTV>,
                t: Throwable
            ) {
                requestCallback.recordFailure(t)
            }

            override fun onResponse(
                call: Call<ResponseTV>,
                response: Response<ResponseTV>
            ) {
                val page = response.body()?.page ?: 0
                val items = response.body()?.results
                items?.let {
                    AppExecutors().diskIO().execute {
                        db.runInTransaction {
                            it.forEach {
                                it.indexPage = page
                            }
                            discoveryDao.insertAllTv(it)
                        }
                        requestCallback.recordSuccess()
                    }
                }
            }
        }
    }
}