package id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.local

import androidx.room.Embedded
import androidx.room.Relation
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.genre.GenresItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResultsItemReview
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.detail.ResponseDetailTV

data class DetailTv(
    @Embedded val responseDetailTV: ResponseDetailTV,
    @Relation(
        parentColumn = "id",
        entityColumn = "genreOwnerId"
    )
    val genres: List<GenresItem>?,
    @Relation(
        parentColumn = "id",
        entityColumn = "reviewOwnerId"
    )
    val reviews: List<ResultsItemReview>?
)