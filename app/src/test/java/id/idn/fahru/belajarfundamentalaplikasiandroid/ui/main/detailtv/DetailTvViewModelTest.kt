package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.detailtv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.local.DetailTv
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.genre.GenresItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResultsItemReview
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.ConvertToLiveData
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.NetworkState
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.DetailTvRepository
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.DetailItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.util.MockTestUtil.Companion.responseDetailTv
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.random.Random

@RunWith(MockitoJUnitRunner::class)
class DetailTvViewModelTest {
    private lateinit var detailTvViewModel: DetailTvViewModel

    private val genres = emptyList<GenresItem>()
    private val reviews = emptyList<ResultsItemReview>()
    private val bookmark = ConvertToLiveData(true)
    private val networkState = ConvertToLiveData(NetworkState.LOADED)


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    private val tvRepository = mock<DetailTvRepository>()
    private val mockDetailObserver = mock<Observer<in DetailTv>>()

    @Before
    fun init() {
        detailTvViewModel = DetailTvViewModel(tvRepository)
    }

    @Test
    fun getMovieDetailById() {
        val randomInt = Random.nextInt()
        whenever(tvRepository.getDetailTv(randomInt)).thenReturn(mockDetailTv(randomInt))
        detailTvViewModel.apply {
            detail.observeForever(mockDetailObserver)
            setTvID(randomInt)
        }
        val dataDetail = detailTvViewModel.detail.value?.responseDetailTV
        assertNotNull(dataDetail)
        assertEquals(randomInt, dataDetail?.id)
        assertEquals("firstAirDate $randomInt", dataDetail?.firstAirDate)
        assertEquals("homepage $randomInt", dataDetail?.homepage)
        assertEquals("backdropPath $randomInt", dataDetail?.backdropPath)
    }

    private fun mockDetailTv(id: Int): LiveData<DetailItem<DetailTv>> {
        val detailMovie = ConvertToLiveData(DetailTv(responseDetailTv(id), genres, reviews))
        return ConvertToLiveData(DetailItem(detailMovie, bookmark, networkState, {}, {}))
    }
}