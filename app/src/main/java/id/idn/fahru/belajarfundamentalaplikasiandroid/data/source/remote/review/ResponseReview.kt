package id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ResponseReview(

    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("page")
    val page: Int?,

    @field:SerializedName("total_pages")
    val totalPages: Int?,

    @field:SerializedName("results")
    val results: List<ResultsItemReview>? = ArrayList(),

    @field:SerializedName("total_results")
    val totalResults: Int?
)

@Entity
data class ResultsItemReview(

    @field:SerializedName("author")
    val author: String?,

    @field:SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: String,

    @field:SerializedName("content")
    val content: String?,

    @field:SerializedName("url")
    val url: String?,

    var reviewOwnerId: Int?,

    val isBookmarked: Boolean = false
)