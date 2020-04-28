package id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.detail

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.genre.GenresItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResponseReview

@Entity
data class ResponseDetailMovie(

    @field:SerializedName("imdb_id")
    val imdbId: String?,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("backdrop_path")
    val backdropPath: String,

    @field:SerializedName("revenue")
    val revenue: Int?,

    @field:SerializedName("popularity")
    val popularity: Double?,

    @field:SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @field:SerializedName("vote_count")
    val voteCount: Int?,

    @field:SerializedName("budget")
    val budget: Int?,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("poster_path")
    val posterPath: String?,

    @field:SerializedName("release_date")
    val releaseDate: String?,

    @field:SerializedName("vote_average")
    val voteAverage: Double?,

    @field:SerializedName("tagline")
    val tagline: String?,

    @field:SerializedName("homepage")
    val homepage: String?,

    @field:SerializedName("status")
    val status: String?,

    val isBookmarked: Boolean = false
) {
    @field:SerializedName("genres")
    @Ignore
    val genres: List<GenresItem>? = ArrayList()

    @Ignore
    val reviews: List<ResponseReview>? = ArrayList()
}