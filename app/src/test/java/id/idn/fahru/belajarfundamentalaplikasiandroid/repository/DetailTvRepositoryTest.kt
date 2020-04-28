package id.idn.fahru.belajarfundamentalaplikasiandroid.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.service.TVService
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.local.DetailTv
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.DetailItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.DetailDao
import id.idn.fahru.belajarfundamentalaplikasiandroid.room.TheMovieDB
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailTvRepositoryTest {
    private lateinit var repository: DetailTvRepository
    private val service = mock<TVService>()
    private val db = mock<TheMovieDB>()
    private val dao = mock<DetailDao>()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = DetailTvRepository(service, db, dao)
    }

    @Test
    fun getDetailMovie() {
        val mockResponse = mock<LiveData<DetailTv>>()
        val mockDao = mock<LiveData<Boolean>>()
        whenever(dao.getTvById(1)).thenReturn(mockResponse)
        whenever(dao.checkBookmarkTv(1)).thenReturn(mockDao)
        val observer = mock<Observer<DetailItem<DetailTv>>>()
        val data = repository.getDetailTv(1)
        data.observeForever(observer)
        verify(dao).getTvById(1)
        verify(dao).checkBookmarkTv(1)
    }
}