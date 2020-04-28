package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.movie.discover.ResultsItemMovie
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter.viewholder.RVFragmentViewHolder


class RVFragmentMovieAdapter :
    PagedListAdapter<ResultsItemMovie, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ResultsItemMovie>() {
            override fun areContentsTheSame(
                oldItem: ResultsItemMovie,
                newItem: ResultsItemMovie
            ): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(
                oldItem: ResultsItemMovie,
                newItem: ResultsItemMovie
            ): Boolean =
                oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RVFragmentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RVFragmentViewHolder).bind(getItem(position))
    }
}