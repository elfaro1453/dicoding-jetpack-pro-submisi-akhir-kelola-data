package id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.local

import androidx.room.Embedded
import androidx.room.Relation
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.genre.GenresItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.detail.ResponseDetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResultsItemReview

data class DetailMovie(
    @Embedded val responseDetailMovie: ResponseDetailMovie,
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