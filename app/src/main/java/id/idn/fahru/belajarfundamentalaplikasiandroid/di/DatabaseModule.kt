package id.idn.fahru.belajarfundamentalaplikasiandroid.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.DetailDao
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.DiscoveryDao
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.FavouriteDao
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.TheMovieDB
import javax.inject.Singleton

@Suppress("unused")
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): TheMovieDB {
        return Room
            .databaseBuilder(app, TheMovieDB::class.java, "themovie.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDiscoveryDao(db: TheMovieDB): DiscoveryDao {
        return db.discoveryDao()
    }

    @Singleton
    @Provides
    fun provideDetailDao(db: TheMovieDB): DetailDao {
        return db.detailDao()
    }

    @Singleton
    @Provides
    fun provideFavouriteDao(db: TheMovieDB): FavouriteDao {
        return db.favouriteDao()
    }
}