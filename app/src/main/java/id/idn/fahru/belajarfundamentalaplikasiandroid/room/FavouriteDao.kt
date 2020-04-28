package id.idn.fahru.belajarfundamentalaplikasiandroid.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.detail.ResponseDetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.detail.ResponseDetailTV

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM ResponseDetailMovie WHERE isBookmarked = 1")
    fun getFavMovie(): LiveData<List<ResponseDetailMovie>>

    @Query("SELECT * FROM ResponseDetailTV WHERE isBookmarked = 1")
    fun getFavTv(): LiveData<List<ResponseDetailTV>>
}