package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.tv.discover.ResultsItemTV
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter.viewholder.RVFragmentViewHolder


class RVFragmentTVAdapter :
    PagedListAdapter<ResultsItemTV, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ResultsItemTV>() {
            override fun areContentsTheSame(
                oldItem: ResultsItemTV,
                newItem: ResultsItemTV
            ): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: ResultsItemTV, newItem: ResultsItemTV): Boolean =
                oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RVFragmentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RVFragmentViewHolder).bind(getItem(position))
    }
}