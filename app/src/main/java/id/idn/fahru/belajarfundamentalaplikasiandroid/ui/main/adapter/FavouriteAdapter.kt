package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.belajarfundamentalaplikasiandroid.databinding.ItemListFavouriteBinding
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter.viewholder.FavViewHolder


class FavouriteAdapter :
    RecyclerView.Adapter<FavViewHolder>() {

    private val listData = ArrayList<Any>()

    fun addList(dataMovie: List<Any>) {
        listData.clear()
        dataMovie.let {
            listData.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemListMovieBinding =
            ItemListFavouriteBinding.inflate(layoutInflater, parent, false)
        return FavViewHolder(
            itemListMovieBinding
        )
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(
        holder: FavViewHolder,
        position: Int
    ) {
        holder.bind(listData[position])
    }
}