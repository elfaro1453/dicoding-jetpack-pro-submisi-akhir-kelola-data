package id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

data class ResponseMovie(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<ResultsItemMovie>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)

@Parcelize
@Entity
data class ResultsItemMovie(

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0
) : Parcelable {
    // to be consistent w/ changing backend order, we need to keep a data like this
    @IgnoredOnParcel
    var indexPage: Int = 0
}