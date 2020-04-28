package id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.genre

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GenresItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    var genreOwnerId: Int?,

    val isBookmarked: Boolean = false
)