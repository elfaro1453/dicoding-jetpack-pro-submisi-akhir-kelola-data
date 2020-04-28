package id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.discover

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

data class ResponseTV(

    @field:SerializedName("page")
    val page: Int?,

    @field:SerializedName("total_pages")
    val totalPages: Int?,

    @field:SerializedName("results")
    val results: List<ResultsItemTV>?,

    @field:SerializedName("total_results")
    val totalResults: Int?
)

@Entity
@Parcelize
data class ResultsItemTV(

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0
) : Parcelable {
    // to be consistent w/ changing backend order, we need to keep a data like this
    @IgnoredOnParcel
    var indexPage: Int = 0
}