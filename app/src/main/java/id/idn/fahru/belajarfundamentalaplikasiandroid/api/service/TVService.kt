package id.idn.fahru.belajarfundamentalaplikasiandroid.api.service

import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResponseReview
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.detail.ResponseDetailTV
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TVService {
    // Get TV Detail
    @GET("/3/tv/{tv_id}")
    fun fetchTvById(@Path("tv_id") id: Int): Call<ResponseDetailTV>

    // Get TV Reviews
    @GET("/3/tv/{tv_id}/reviews")
    fun fetchReviews(@Path("tv_id") id: Int): Call<ResponseReview>
}