/**
 * Referensi : https://medium.com/better-programming/dependency-injection-in-android-with-dagger2-d260b8a72bb0
 */
package id.idn.fahru.belajarfundamentalaplikasiandroid.di

import androidx.annotation.NonNull
import androidx.test.espresso.IdlingRegistry
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.RequestInterceptor
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.service.DiscoverService
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.service.MovieService
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.service.TVService
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.retrofitcalladapter.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * TestNetworkModule hanya digunakan pada debug build
 * Sehingga bisa dihapus saat build release
 */
@Suppress("unused")
@Module
class TestNetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
        IdlingRegistry.getInstance()
            .register(OkHttp3IdlingResource.create("retrofit", okHttpClient))
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideDiscoverService(@NonNull retrofit: Retrofit): DiscoverService {
        return retrofit.create(DiscoverService::class.java)
    }


    @Provides
    @Singleton
    fun provideMovieService(@NonNull retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideTVService(@NonNull retrofit: Retrofit): TVService {
        return retrofit.create(TVService::class.java)
    }
}