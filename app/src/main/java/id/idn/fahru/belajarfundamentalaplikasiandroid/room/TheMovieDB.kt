package id.idn.fahru.belajarfundamentalaplikasiandroid.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.genre.GenresItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.detail.ResponseDetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResultsItemMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResultsItemReview
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.detail.ResponseDetailTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.discover.ResultsItemTV

@Database(
    entities = [
        ResultsItemMovie::class,
        ResultsItemTV::class,
        ResultsItemReview::class,
        GenresItem::class,
        ResponseDetailMovie::class,
        ResponseDetailTV::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TheMovieDB : RoomDatabase() {
    abstract fun discoveryDao(): DiscoveryDao
    abstract fun detailDao(): DetailDao
    abstract fun favouriteDao(): FavouriteDao
}