package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResultsItemReview
import id.idn.fahru.belajarfundamentalaplikasiandroid.databinding.ItemListReviewBinding
import kotlinx.android.synthetic.main.item_list_review.view.*

class RVReviewAdapter : RecyclerView.Adapter<RVReviewAdapter.ViewHolder>() {

    private val listData = ArrayList<ResultsItemReview>()

    fun addReviewList(dataReview: List<ResultsItemReview>) {
        listData.clear()
        dataReview.let {
            listData.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemListReviewBinding = ItemListReviewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(
            itemListReviewBinding
        )
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    class ViewHolder(itemBinding: ItemListReviewBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val rootView = itemBinding.root

        internal fun bind(itemReview: ResultsItemReview) {
            rootView.run {
                author.text = itemReview.author
                itemReview.content?.let {
                    content.text = if (it.length > 300) it.take(297) + "..." else it
                }
                openLink.setOnClickListener {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(itemReview.url)))
                }
            }
        }
    }
}