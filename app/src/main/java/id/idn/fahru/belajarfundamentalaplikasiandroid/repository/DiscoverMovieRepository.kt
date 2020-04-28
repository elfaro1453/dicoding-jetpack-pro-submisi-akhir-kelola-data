package id.idn.fahru.belajarfundamentalaplikasiandroid.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.idn.fahru.belajarfundamentalaplikasiandroid.AppExecutors
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.service.DiscoverService
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResponseMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResultsItemMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.NetworkState
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.boundarycallback.DiscoverBoundaryCallback
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.Listing
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.DiscoveryDao
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.TheMovieDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiscoverMovieRepository @Inject constructor(
    private val discoverService: DiscoverService,
    private val db: TheMovieDB,
    private val discoveryDao: DiscoveryDao
) {

    private fun refreshMovie(): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING
        AppExecutors().diskIO().execute {
            discoveryDao.flushDB()
            discoverService.fetchDiscoverMovie(1).enqueue(
                object : Callback<ResponseMovie> {
                    override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                        // retrofit calls this on main thread so safe to call set value
                        networkState.value = NetworkState.error(t.message)
                        Timber.e("Failed to Fetch ResponseMovie")
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
                                    discoveryDao.deleteAllMovie()
                                    it.forEach {
                                        it.indexPage = page
                                    }
                                    discoveryDao.insertAllMovie(it)
                                }
                                // since we are in bg thread now, post the result.
                                networkState.postValue(NetworkState.LOADED)
                            }
                        }
                    }
                }
            )
        }
        return networkState
    }

    /**
     * Returns a Listing for Movie.
     */
    @MainThread
    fun setListMovies(boolean: Boolean): LiveData<Listing<ResultsItemMovie>> {
        val liveData = MediatorLiveData<Listing<ResultsItemMovie>>()
        val refreshTrigger = MutableLiveData<Boolean>()
        val refreshState = refreshTrigger.switchMap {
            refreshMovie()
        }
        val boundaryCallback = DiscoverBoundaryCallback(
            discoverService,
            db,
            discoveryDao,
            ResultsItemMovie::class.java
        )
        refreshTrigger.postValue(boolean)
        val pageListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(4)
            .setInitialLoadSizeHint(20)
            .setPageSize(10)
            .build()

        val livePagedList = LivePagedListBuilder(
            discoveryDao.getDiscoveryMovie(), pageListConfig
        )
            .setBoundaryCallback(boundaryCallback)
            .build()

        liveData.addSource(refreshTrigger) {
            liveData.value = Listing(
                pagedList = livePagedList,
                networkState = boundaryCallback.networkState,
                refreshState = refreshState,
                retry = { boundaryCallback.helper.retryAllFailed() })
        }

        return liveData
    }
}
