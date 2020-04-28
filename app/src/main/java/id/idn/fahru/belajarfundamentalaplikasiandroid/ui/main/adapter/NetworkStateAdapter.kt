package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.NetworkState
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter.viewholder.NetworkStateItemViewHolder

class NetworkStateAdapter(private val retry: () -> Unit) :
    RecyclerView.Adapter<NetworkStateItemViewHolder>() {
    /**
     * LoadState to present in the adapter.
     *
     * Changing this property will immediately notify the Adapter to change the item it's
     * presenting.
     */
    var networkState = NetworkState.LOADED
        set(networkState) {
            if (field != networkState) {
                val displayOldItem = displayLoadStateAsItem(field)
                val displayNewItem = displayLoadStateAsItem(networkState)

                if (displayOldItem && !displayNewItem) {
                    notifyItemRemoved(0)
                } else if (displayNewItem && !displayOldItem) {
                    notifyItemInserted(0)
                } else if (displayOldItem && displayNewItem) {
                    notifyItemChanged(0)
                }
                field = networkState
            }
        }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, position: Int) {
        holder.bindTo(networkState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder.create(parent, retry)
    }

    override fun getItemViewType(position: Int): Int = 0

    override fun getItemCount(): Int = if (displayLoadStateAsItem(networkState)) 1 else 0

    /**
     * Returns true if the LoadState should be displayed as a list item when active.
     *
     *  [LoadState.Loading] and [LoadState.Error] present as list items,
     * [LoadState.Done] is not.
     */
    private fun displayLoadStateAsItem(networkState: NetworkState) =
        networkState != NetworkState.LOADED
}