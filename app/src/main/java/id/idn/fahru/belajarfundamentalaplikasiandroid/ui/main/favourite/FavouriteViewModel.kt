package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.favourite


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.detail.ResponseDetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.detail.ResponseDetailTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.FavouriteRepository
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(favouriteRepository: FavouriteRepository) :
    ViewModel() {
    private val movies = favouriteRepository.movies
    private val tvs = favouriteRepository.tvs
    fun getMovies(): LiveData<List<ResponseDetailMovie>> {
        return movies
    }

    fun getTvs(): LiveData<List<ResponseDetailTV>> {
        return tvs
    }
}