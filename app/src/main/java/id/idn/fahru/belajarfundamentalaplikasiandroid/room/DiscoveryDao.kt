package id.idn.fahru.belajarfundamentalaplikasiandroid.room

import androidx.paging.DataSource
import androidx.room.*
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResultsItemMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.discover.ResultsItemTV

@Dao
interface DiscoveryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllMovie(resultsItemMovies: List<ResultsItemMovie>)

    @Query("SELECT * FROM ResultsItemMovie ORDER BY indexPage ASC")
    fun getDiscoveryMovie(): DataSource.Factory<Int, ResultsItemMovie>

    @Query("DELETE FROM ResultsItemMovie")
    fun deleteAllMovie()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllTv(resultsItemTvs: List<ResultsItemTV>)

    @Query("SELECT * FROM ResultsItemTV ORDER BY indexPage ASC")
    fun getDiscoveryTv(): DataSource.Factory<Int, ResultsItemTV>

    @Query("DELETE FROM ResultsItemTV")
    fun deleteAllTv()

    /**
     * We can remove Unbookmarked Item or something like Delete DB Caches
     */
    @Transaction
    fun flushDB() {
        flushDetailMovie()
        flushDetailTv()
        flushGenres()
        flushReview()
    }

    @Query("DELETE FROM ResponseDetailMovie WHERE isBookmarked = 0")
    fun flushDetailMovie()

    @Query("DELETE FROM ResponseDetailTV WHERE isBookmarked = 0")
    fun flushDetailTv()

    @Query("DELETE FROM GenresItem WHERE isBookmarked = 0")
    fun flushGenres()

    @Query("DELETE FROM ResultsItemReview WHERE isBookmarked = 0")
    fun flushReview()
}