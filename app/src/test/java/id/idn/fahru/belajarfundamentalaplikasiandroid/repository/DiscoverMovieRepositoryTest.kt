package id.idn.fahru.belajarfundamentalaplikasiandroid.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.service.DiscoverService
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResultsItemMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.Listing
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.DiscoveryDao
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.TheMovieDB
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DiscoverMovieRepositoryTest {
    private lateinit var repository: DiscoverMovieRepository
    private val service = mock<DiscoverService>()
    private val db = mock<TheMovieDB>()
    private val dao = mock<DiscoveryDao>()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = DiscoverMovieRepository(service, db, dao)
    }

    @Test
    fun getDetailMovie() {
        val mockDao = mock<DataSource.Factory<Int, ResultsItemMovie>>()
        whenever(dao.getDiscoveryMovie()).thenReturn(mockDao)
        val observer = mock<Observer<Listing<ResultsItemMovie>>>()
        val data = repository.setListMovies(true)
        data.observeForever(observer)
        verify(dao).getDiscoveryMovie()
    }
}