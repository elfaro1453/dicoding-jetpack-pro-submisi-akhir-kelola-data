package id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.detail

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.genre.GenresItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResponseReview

@Entity
data class ResponseDetailTV(

    @field:SerializedName("number_of_episodes")
    val numberOfEpisodes: Int?,

    @field:SerializedName("backdrop_path")
    val backdropPath: String?,

    @field:SerializedName("popularity")
    val popularity: Double?,

    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("number_of_seasons")
    val numberOfSeasons: Int?,

    @field:SerializedName("vote_count")
    val voteCount: Int?,

    @field:SerializedName("first_air_date")
    val firstAirDate: String?,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("poster_path")
    val posterPath: String?,

    @field:SerializedName("vote_average")
    val voteAverage: Double?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("last_air_date")
    val lastAirDate: String?,

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