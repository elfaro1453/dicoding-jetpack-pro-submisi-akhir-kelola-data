package id.idn.fahru.belajarfundamentalaplikasiandroid

import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.DaggerApplication
import id.idn.fahru.belajarfundamentalaplikasiandroid.di.DaggerAppComponent
import timber.log.Timber

@Suppress("unused")
class JetPackPro : DaggerApplication() {

    private val appComponent = DaggerAppComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector() = appComponent
}