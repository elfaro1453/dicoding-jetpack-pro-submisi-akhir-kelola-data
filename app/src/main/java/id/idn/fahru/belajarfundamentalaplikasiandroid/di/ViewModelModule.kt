package id.idn.fahru.belajarfundamentalaplikasiandroid.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.idn.fahru.belajarfundamentalaplikasiandroid.di.annotations.ViewModelKey
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.detailmovie.DetailMovieViewModel
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.detailtv.DetailTvViewModel
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.favourite.FavouriteViewModel
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.fragmentmovie.FragmentMovieViewModel
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.fragmenttv.FragmentTVViewModel

@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FragmentMovieViewModel::class)
    internal abstract fun bindFragmentMovieViewModel(fragmentMovieViewModel: FragmentMovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FragmentTVViewModel::class)
    internal abstract fun bindFragmentTVViewModel(fragmentTVViewModel: FragmentTVViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailMovieViewModel::class)
    internal abstract fun bindDetailMovieViewModel(detailMovieViewModel: DetailMovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailTvViewModel::class)
    internal abstract fun bindDetailTVViewModel(detailTvViewModel: DetailTvViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteViewModel::class)
    internal abstract fun bindFavouriteViewModel(favouriteViewModel: FavouriteViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): Factory
}
