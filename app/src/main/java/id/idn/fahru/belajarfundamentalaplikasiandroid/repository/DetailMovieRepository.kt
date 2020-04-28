package id.idn.fahru.belajarfundamentalaplikasiandroid.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import id.idn.fahru.belajarfundamentalaplikasiandroid.AppExecutors
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.service.MovieService
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.local.DetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.detail.ResponseDetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResponseReview
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
class DetailMovieRepository @Inject constructor(
    private val movieService: MovieService,
    private val db: TheMovieDB,
    private val detailDao: DetailDao
) {

    @MainThread
    private fun bookmarkMovie(
        movie: ResponseDetailMovie
    ): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        AppExecutors().diskIO().execute {
            db.runInTransaction {
                val stat = detailDao.syncBookmarkMovie(movie.id)
                if (stat) {
                    detailDao.bookmarkMovie(false, movie.id)
                    status.postValue(false)
                } else {
                    detailDao.bookmarkMovie(true, movie.id)
                    status.postValue(true)
                }
            }
        }
        return status
    }

    private fun movieState(movieId: Int, refresh: Boolean): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        AppExecutors().diskIO().execute {
            var isAnyData = detailDao.checkMovieById(movieId)
            if (refresh) isAnyData = 0
            if (isAnyData < 1) {
                movieService.fetchMovieById(movieId).enqueue(
                    object : Callback<ResponseDetailMovie> {
                        override fun onFailure(call: Call<ResponseDetailMovie>, t: Throwable) {
                            networkState.value = NetworkState.error(t.message)
                        }

                        override fun onResponse(
                            call: Call<ResponseDetailMovie>,
                            response: Response<ResponseDetailMovie>
                        ) {
                            response.body()?.let {
                                it.genres?.onEach { genreItem ->
                                    genreItem.genreOwnerId = it.id
                                }?.let { dataGenres ->
                                    AppExecutors().diskIO().execute {
                                        db.runInTransaction {
                                            detailDao.insertMovie(it)
                                            detailDao.insertGenres(dataGenres)
                                        }
                                    }
                                }
                                networkState.postValue(NetworkState.LOADED)
                            }
                        }
                    }
                )
                movieService.fetchReviews(movieId).enqueue(
                    object : Callback<ResponseReview> {
                        override fun onFailure(call: Call<ResponseReview>, t: Throwable) {
                            // it's ok reviews being null
                            // networkState.value = NetworkState.error(t.message)
                        }

                        override fun onResponse(
                            call: Call<ResponseReview>,
                            response: Response<ResponseReview>
                        ) {
                            val reviewsMovie = response.body()?.results
                            reviewsMovie?.onEach {
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
    fun getDetailMovie(movieId: Int): LiveData<DetailItem<DetailMovie>> {
        val liveData = MediatorLiveData<DetailItem<DetailMovie>>()

        val itemDetail = detailDao.getMovieById(movieId)
        val bookmarkState = detailDao.checkBookmarkMovie(movieId)
        val networkState = movieState(movieId, false)

        liveData.addSource(itemDetail) {
            liveData.value = DetailItem(
                itemDetail,
                bookmarkState,
                networkState,
                { bookmarkMovie(it.responseDetailMovie) },
                { movieState(movieId, true) })
        }
        return liveData
    }
}