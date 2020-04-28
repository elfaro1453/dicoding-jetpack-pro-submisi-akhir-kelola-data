package id.idn.fahru.belajarfundamentalaplikasiandroid.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.service.MovieService
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.local.DetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.DetailItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.DetailDao
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.TheMovieDB
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailMovieRepositoryTest {
    private lateinit var repository: DetailMovieRepository
    private val service = mock<MovieService>()
    private val db = mock<TheMovieDB>()
    private val dao = mock<DetailDao>()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = DetailMovieRepository(service, db, dao)
    }

    @Test
    fun getDetailMovie() {
        val mockResponse = mock<LiveData<DetailMovie>>()
        val mockDao = mock<LiveData<Boolean>>()
        whenever(dao.getMovieById(1)).thenReturn(mockResponse)
        whenever(dao.checkBookmarkMovie(1)).thenReturn(mockDao)
        val observer = mock<Observer<DetailItem<DetailMovie>>>()
        val data = repository.getDetailMovie(1)
        data.observeForever(observer)
        verify(dao).getMovieById(1)
        verify(dao).checkBookmarkMovie(1)
    }
}