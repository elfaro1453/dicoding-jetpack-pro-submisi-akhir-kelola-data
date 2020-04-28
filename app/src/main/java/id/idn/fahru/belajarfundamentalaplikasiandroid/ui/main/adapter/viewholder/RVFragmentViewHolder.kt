package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter.viewholder

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import id.idn.fahru.belajarfundamentalaplikasiandroid.R
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.ImageURL
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResultsItemMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.discover.ResultsItemTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.databinding.ItemListRecyclerViewFragmentBinding
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.detailmovie.DetailMovieActivity
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.detailtv.DetailTvActivity

class RVFragmentViewHolder(itemBinding: ItemListRecyclerViewFragmentBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    companion object {
        fun create(parent: ViewGroup): RVFragmentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemListBinding =
                ItemListRecyclerViewFragmentBinding.inflate(layoutInflater, parent, false)
            return RVFragmentViewHolder(
                itemListBinding
            )
        }
    }

    private val title = itemBinding.rvTitle
    private val image = itemBinding.imgItemPhoto
    private val rating = itemBinding.ratingBar
    private val rootView = itemBinding.root

    fun <T> bind(item: T) {
        val res = rootView.resources
        when (item) {
            is ResultsItemMovie -> {
                title.text = item.title.toString()
                image.contentDescription = res.getString(R.string.poster_of, title.text.toString())
                image.load(ImageURL.getPosterPath(item.posterPath.toString())) {
                    transformations(RoundedCornersTransformation())
                }
                rating.rating = item.voteAverage?.div(2)?.toFloat() ?: 0f
                rootView.setOnClickListener {
                    val intent = Intent(it.context, DetailMovieActivity::class.java).apply {
                        putExtra(DetailMovieActivity.sendMovieID, item)
                    }
                    it.context.startActivity(intent)
                }
            }
            is ResultsItemTV -> {
                title.text = item.name.toString()
                image.contentDescription = res.getString(R.string.poster_of, title.text.toString())
                image.load(ImageURL.getPosterPath(item.posterPath.toString())) {
                    transformations(RoundedCornersTransformation())
                }
                rating.rating = item.voteAverage?.div(2)?.toFloat() ?: 0f
                rootView.setOnClickListener {
                    val intent = Intent(it.context, DetailTvActivity::class.java).apply {
                        putExtra(DetailTvActivity.sendTvID, item)
                    }
                    it.context.startActivity(intent)
                }
            }
        }
    }
}