package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter.viewholder

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import id.idn.fahru.belajarfundamentalaplikasiandroid.R
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.ImageURL
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.detail.ResponseDetailMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResultsItemMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.detail.ResponseDetailTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.discover.ResultsItemTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.databinding.ItemListFavouriteBinding
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.detailmovie.DetailMovieActivity
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.detailtv.DetailTvActivity

class FavViewHolder(itemBinding: ItemListFavouriteBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private val title = itemBinding.title
    private val image = itemBinding.imgItemPhoto
    private val rating = itemBinding.ratingBar
    private val description = itemBinding.description
    private val rootView = itemBinding.root

    fun <T> bind(item: T) {
        val res = rootView.resources
        when (item) {
            is ResponseDetailMovie -> {
                (item as ResponseDetailMovie).let {
                    val titleText = it.title.toString()
                    if (titleText.length > 35) {
                        title.text = titleText.take(32) + "..."
                    } else {
                        title.text = titleText
                    }
                    image.contentDescription =
                        res.getString(R.string.poster_of, it.title.toString())
                    image.load(ImageURL.getPosterPath(it.posterPath.toString()))
                    rating.rating = it.voteAverage?.div(2)?.toFloat() ?: 0f
                    it.overview?.let { overview ->
                        if (overview.length > 60) {
                            description.text = it.overview.take(57) + "..."
                        } else {
                            description.text = it.overview
                        }
                    }
                }
                rootView.setOnClickListener {
                    val data = ResultsItemMovie(
                        item.popularity,
                        item.title,
                        item.posterPath,
                        item.voteAverage,
                        item.id
                    )
                    val intent = Intent(it.context, DetailMovieActivity::class.java).apply {
                        putExtra(DetailMovieActivity.sendMovieID, data)
                    }
                    it.context.startActivity(intent)
                }
            }
            is ResponseDetailTV -> {
                (item as ResponseDetailTV).let {
                    val titleText = it.name.toString()
                    if (titleText.length > 35) {
                        title.text = titleText.take(32) + "..."
                    } else {
                        title.text = titleText
                    }
                    image.contentDescription =
                        res.getString(R.string.poster_of, it.name.toString())
                    image.load(ImageURL.getPosterPath(it.posterPath.toString()))
                    rating.rating = it.voteAverage?.div(2)?.toFloat() ?: 0f
                    it.overview?.let { overview ->
                        if (overview.length > 60) {
                            description.text = it.overview.take(57) + "..."
                        } else {
                            description.text = it.overview
                        }
                    }
                }
                rootView.setOnClickListener {
                    val data = ResultsItemTV(
                        item.popularity,
                        item.posterPath,
                        item.voteAverage,
                        item.name,
                        item.id
                    )
                    val intent = Intent(it.context, DetailTvActivity::class.java).apply {
                        putExtra(DetailTvActivity.sendTvID, data)
                    }
                    it.context.startActivity(intent)
                }
            }
        }
    }
}