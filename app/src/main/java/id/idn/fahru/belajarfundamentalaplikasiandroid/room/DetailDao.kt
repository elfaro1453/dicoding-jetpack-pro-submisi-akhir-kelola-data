package id.idn.fahru.belajarfundamentalaplikasiandroid.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.local.DetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.local.DetailTv
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.genre.GenresItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.detail.ResponseDetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResultsItemReview
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.detail.ResponseDetailTV

@Dao
abstract class DetailDao {
    /**
     * https://medium.com/swlh/android-room-persistence-library-relations-in-a-nested-one-to-many-relationship-f2fe21c9e1ad
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertGenres(genresItem: List<GenresItem>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertReviews(reviews: List<ResultsItemReview>): List<Long>

    @Query("UPDATE GenresItem SET isBookmarked = :status WHERE genreOwnerId = :Id")
    abstract fun setBookmarkGenre(status: Boolean, Id: Int)

    @Query("UPDATE ResultsItemReview SET isBookmarked = :status WHERE reviewOwnerId = :Id")
    abstract fun setBookmarkReview(status: Boolean, Id: Int)

    /**
     * Movie
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovie(responseDetailMovie: ResponseDetailMovie): Long

    @Transaction
    @Query("SELECT * FROM ResponseDetailMovie")
    abstract fun getMovies(): DataSource.Factory<Int, DetailMovie>

    @Transaction
    @Query("SELECT * FROM ResponseDetailMovie WHERE id = :movieId")
    abstract fun getMovieById(movieId: Int): LiveData<DetailMovie>

    @Query("UPDATE ResponseDetailMovie SET isBookmarked = :status WHERE id = :movieId")
    abstract fun setBookmarkMovie(status: Boolean, movieId: Int)

    @Query("SELECT COUNT(*) FROM ResponseDetailMovie WHERE id = :movieId")
    abstract fun checkMovieById(movieId: Int): Long

    @Query("SELECT isBookmarked FROM ResponseDetailMovie WHERE id = :movieId")
    abstract fun checkBookmarkMovie(movieId: Int): LiveData<Boolean>

    @Query("SELECT isBookmarked FROM ResponseDetailMovie WHERE id = :movieId")
    abstract fun syncBookmarkMovie(movieId: Int): Boolean

    @Transaction
    open fun bookmarkMovie(status: Boolean, movieId: Int) {
        setBookmarkMovie(status, movieId)
        setBookmarkGenre(status, movieId)
        setBookmarkReview(status, movieId)
    }

    /**
     * Tv
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTv(responseDetailTV: ResponseDetailTV): Long

    @Transaction
    @Query("SELECT * FROM ResponseDetailTV")
    abstract fun getTvs(): DataSource.Factory<Int, DetailTv>

    @Transaction
    @Query("SELECT * FROM ResponseDetailTV WHERE id = :tvId")
    abstract fun getTvById(tvId: Int): LiveData<DetailTv>

    @Query("UPDATE ResponseDetailTV SET isBookmarked = :status WHERE id = :tvId")
    abstract fun setBookmarkTv(status: Boolean, tvId: Int)

    @Query("SELECT COUNT(*) FROM ResponseDetailTV WHERE id = :tvID")
    abstract fun checkTVById(tvID: Int): Long

    @Query("SELECT isBookmarked FROM ResponseDetailTV WHERE id = :tvID")
    abstract fun checkBookmarkTv(tvID: Int): LiveData<Boolean>

    @Query("SELECT isBookmarked FROM ResponseDetailTV WHERE id = :tvID")
    abstract fun syncBookmarkTv(tvID: Int): Boolean

    @Transaction
    open fun bookmarkTv(status: Boolean, tvId: Int) {
        setBookmarkTv(status, tvId)
        setBookmarkGenre(status, tvId)
        setBookmarkReview(status, tvId)
    }
}