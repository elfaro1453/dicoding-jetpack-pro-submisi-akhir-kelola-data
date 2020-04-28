package id.idn.fahru.belajarfundamentalaplikasiandroid.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

/**
 * TestAppComponent hanya digunakan pada debug build
 * Sehingga bisa dihapus saat build release
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
        TestNetworkModule::class]
)
interface TestAppComponent : AndroidInjector<DaggerApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): TestAppComponent
    }
}