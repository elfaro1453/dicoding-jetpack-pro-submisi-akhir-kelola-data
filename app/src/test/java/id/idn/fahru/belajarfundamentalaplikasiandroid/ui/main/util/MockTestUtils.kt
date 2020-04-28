package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.util

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.detail.ResponseDetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResultsItemMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.detail.ResponseDetailTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.discover.ResultsItemTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.util.ApiUtil.createLiveData
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MockTestUtil {
    companion object {
        fun mockMoviePagedList(): LiveData<PagedList<ResultsItemMovie>> {
            val movies = ArrayList<ResultsItemMovie>()
            for (i in 0 until 20) {
                movies.add(
                    ResultsItemMovie(
                        1.1,
                        "Title $i",
                        "Poster $i",
                        1.1,
                        i
                    )
                )
            }
            val pagedList = mockPagedList(movies)
            return createLiveData(pagedList)
        }

        fun mockTvPagedList(): LiveData<PagedList<ResultsItemTV>> {
            val tvs = ArrayList<ResultsItemTV>()
            for (i in 0 until 20) {
                tvs.add(
                    ResultsItemTV(
                        1.1,
                        "Poster $i",
                        1.1,
                        "Title $i",
                        i
                    )
                )
            }
            val pagedList = mockPagedList(tvs)
            return createLiveData(pagedList)
        }

        fun responseDetailMovie(id: Int) = ResponseDetailMovie(
            "imdbID $id",
            "title $id",
            "backdropPath $id",
            id,
            1.0,
            id,
            id,
            id,
            "Overview $id",
            "posterpath $id",
            "releaseDate $id",
            1.0,
            "Tagline $id",
            "homepage $id",
            "status $id"
        )

        fun responseDetailTv(id: Int) = ResponseDetailTV(
            id,
            "backdropPath $id",
            1.0,
            id,
            id,
            id,
            "firstAirDate $id",
            "overview $id",
            "posterPath $id",
            1.0,
            "name $id",
            "lastAirDate $id",
            "homepage $id",
            "status $id"
        )

        fun <T> mockPagedList(list: List<T>): PagedList<T> {
            val pagedList = mock(PagedList::class.java) as PagedList<T>
            `when`(pagedList.size).thenReturn(list.size)
            return pagedList
        }
    }
}
