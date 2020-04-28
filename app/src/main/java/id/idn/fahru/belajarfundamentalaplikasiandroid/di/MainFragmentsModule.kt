package id.idn.fahru.belajarfundamentalaplikasiandroid.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.idn.fahru.belajarfundamentalaplikasiandroid.di.annotations.FragmentScope
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.fragmentmovie.MovieFragment
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.fragmenttv.TVFragment

/**
 * https://github.com/android/architecture-components-samples/blob/fc20e0a99a5ba1624a7c28ac21317726859e8d04/GithubBrowserSample/app/src/main/java/com/android/example/github/di/MainFragmentsModule
 */

@Suppress("unused")
@Module
abstract class MainFragmentsModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMovieFragment(): MovieFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeTVFragment(): TVFragment
}