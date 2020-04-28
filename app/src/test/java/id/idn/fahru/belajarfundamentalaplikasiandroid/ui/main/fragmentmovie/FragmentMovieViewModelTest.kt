package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.fragmentmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResultsItemMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.ConvertToLiveData
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.NetworkState
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.DiscoverMovieRepository
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.Listing
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.util.MockTestUtil.Companion.mockMoviePagedList
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FragmentMovieViewModelTest {
    private lateinit var fragmentMovieViewModel: FragmentMovieViewModel

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val discoverRepository = mock<DiscoverMovieRepository>()
    private val networkState = ConvertToLiveData(NetworkState.LOADED)
    private val mockObserver = mock<Observer<PagedList<ResultsItemMovie>>>()

    @Before
    fun before() {
        fragmentMovieViewModel = FragmentMovieViewModel(discoverRepository)
    }

    @Test
    fun getDataDiscoveryMovie() {
        val mockpagedList = mockMoviePagedList()
        val listing = Listing(mockpagedList, networkState, networkState, {})
        whenever(discoverRepository.setListMovies(true)).thenReturn(ConvertToLiveData(listing))
        fragmentMovieViewModel.apply {
            dataDiscovery.observeForever(mockObserver)
            verify(discoverRepository).setListMovies(true)
            val data = dataDiscovery.value
            assertNotNull(data)
            assertEquals(20, data?.size)
        }
    }
}