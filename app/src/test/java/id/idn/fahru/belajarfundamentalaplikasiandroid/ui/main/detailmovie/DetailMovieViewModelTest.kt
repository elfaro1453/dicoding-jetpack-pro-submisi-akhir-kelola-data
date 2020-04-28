package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.detailmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.local.DetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.genre.GenresItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResultsItemReview
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.ConvertToLiveData
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.NetworkState
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.DetailMovieRepository
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.DetailItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.util.MockTestUtil.Companion.responseDetailMovie
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.random.Random

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {
    private lateinit var detailMovieViewModel: DetailMovieViewModel

    private val genres = emptyList<GenresItem>()
    private val reviews = emptyList<ResultsItemReview>()
    private val bookmark = ConvertToLiveData(true)
    private val networkState = ConvertToLiveData(NetworkState.LOADED)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    private val movieRepository = mock<DetailMovieRepository>()
    private val mockDetailObserver = mock<Observer<in DetailMovie>>()

    @Before
    fun init() {
        detailMovieViewModel = DetailMovieViewModel(movieRepository)
    }

    @Test
    fun getMovieDetailById() {
        val randomInt = Random.nextInt()
        whenever(movieRepository.getDetailMovie(randomInt)).thenReturn(mockDetailMovie(randomInt))
        detailMovieViewModel.apply {
            detail.observeForever(mockDetailObserver)
            setMovieID(randomInt)
        }
        val dataDetail = detailMovieViewModel.detail.value?.responseDetailMovie
        assertNotNull(dataDetail)
        assertEquals(randomInt, dataDetail?.id)
        assertEquals("imdbID $randomInt", dataDetail?.imdbId)
        assertEquals("title $randomInt", dataDetail?.title)
        assertEquals("backdropPath $randomInt", dataDetail?.backdropPath)
        assertEquals(randomInt, dataDetail?.budget)
    }

    private fun mockDetailMovie(id: Int): LiveData<DetailItem<DetailMovie>> {
        val detailMovie = ConvertToLiveData(DetailMovie(responseDetailMovie(id), genres, reviews))
        return ConvertToLiveData(DetailItem(detailMovie, bookmark, networkState, {}, {}))
    }
}