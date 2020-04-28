package id.idn.fahru.belajarfundamentalaplikasiandroid.repository

import id.idn.fahru.belajarfundamentalaplikasiandroid.room.FavouriteDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteRepository @Inject constructor(
    favouriteDao: FavouriteDao
) {
    // The Dao Room query returns livedata so we don't need appExecutor since it runs at background thread
    val movies = favouriteDao.getFavMovie()
    val tvs = favouriteDao.getFavTv()
}
