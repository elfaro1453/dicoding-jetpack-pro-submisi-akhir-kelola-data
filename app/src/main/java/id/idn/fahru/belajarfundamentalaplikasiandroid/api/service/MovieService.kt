package id.idn.fahru.belajarfundamentalaplikasiandroid.api.service

import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.detail.ResponseDetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResponseReview
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {
    @GET("/3/movie/{movie_id}")
    fun fetchMovieById(@Path("movie_id") id: Int): Call<ResponseDetailMovie>

    @GET("/3/movie/{movie_id}/reviews")
    fun fetchReviews(@Path("movie_id") id: Int): Call<ResponseReview>
}
