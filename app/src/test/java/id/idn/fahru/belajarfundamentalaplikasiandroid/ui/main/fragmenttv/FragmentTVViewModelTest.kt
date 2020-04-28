package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.fragmenttv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.discover.ResultsItemTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.ConvertToLiveData
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.NetworkState
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.DiscoverTvRepository
import id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils.Listing
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.util.MockTestUtil.Companion.mockTvPagedList
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FragmentTVViewModelTest {
    private lateinit var fragmentTVViewModel: FragmentTVViewModel

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val discoverRepository = mock<DiscoverTvRepository>()
    private val networkState = ConvertToLiveData(NetworkState.LOADED)
    private val mockObserver = mock<Observer<PagedList<ResultsItemTV>>>()

    @Before
    fun before() {
        fragmentTVViewModel = FragmentTVViewModel(discoverRepository)
    }

    @Test
    fun getDataDiscoveryMovie() {
        val mockpagedList = mockTvPagedList()
        val listing = Listing(mockpagedList, networkState, networkState, {})
        whenever(discoverRepository.setListTvs(true)).thenReturn(ConvertToLiveData(listing))
        fragmentTVViewModel.apply {
            dataDiscovery.observeForever(mockObserver)
            verify(discoverRepository).setListTvs(true)
            val data = dataDiscovery.value
            assertNotNull(data)
            assertEquals(20, data?.size)
        }
    }
}