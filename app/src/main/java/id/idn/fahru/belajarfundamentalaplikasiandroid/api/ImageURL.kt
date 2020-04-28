package id.idn.fahru.belajarfundamentalaplikasiandroid.api

object ImageURL {

    private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"

    fun getPosterPath(posterPath: String): String {
        return BASE_POSTER_PATH + posterPath
    }

    fun getBackdropPath(backdropPath: String): String {
        return BASE_BACKDROP_PATH + backdropPath
    }
}
