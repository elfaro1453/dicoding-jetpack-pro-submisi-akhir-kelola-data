/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.belajarfundamentalaplikasiandroid.R
import id.idn.fahru.belajarfundamentalaplikasiandroid.databinding.NetworkStateItemBinding
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.NetworkState
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.Status

/**
 * A View Holder that can display a loading or have click action.
 * It is used to show the network state of paging.
 */
class NetworkStateItemViewHolder(
    itemBinding: NetworkStateItemBinding,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(itemBinding.root) {
    private val progressBar = itemBinding.progressBar
    private val retry = itemBinding.retryButton
    private val errorMsg = itemBinding.errorMsg
    private val root = itemBinding.root

    init {
        retry.setOnClickListener {
            retryCallback()
        }
    }

    fun bindTo(networkState: NetworkState?) {
        progressBar.visibility = toVisibility(networkState?.status == Status.RUNNING)
        retry.visibility = toVisibility(networkState?.status == Status.FAILED)
        errorMsg.visibility = toVisibility(networkState?.msg != null)
        errorMsg.text = root.resources.getString(R.string.connection_error)
    }

    private fun toVisibility(constraint: Boolean): Int = if (constraint) {
        View.VISIBLE
    } else {
        View.GONE
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val networkStateItemBinding =
                NetworkStateItemBinding.inflate(layoutInflater, parent, false)
            return NetworkStateItemViewHolder(
                networkStateItemBinding, retryCallback
            )
        }
    }
}
