package id.idn.fahru.belajarfundamentalaplikasiandroid.api.service

import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResponseMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.discover.ResponseTV
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverService {
    @GET("/3/discover/movie?language=en-US&sort_by=popularity.desc")
    fun fetchDiscoverMovie(@Query("page") page: Int): Call<ResponseMovie>

    @GET("/3/discover/tv?language=en-US&sort_by=popularity.desc")
    fun fetchDiscoverTv(@Query("page") page: Int): Call<ResponseTV>
}