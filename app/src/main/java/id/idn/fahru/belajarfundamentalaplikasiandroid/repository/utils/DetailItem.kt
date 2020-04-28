package id.idn.fahru.belajarfundamentalaplikasiandroid.repository.utils

import androidx.lifecycle.LiveData
import id.idn.fahru.belajarfundamentalaplikasiandroid.helpers.NetworkState

data class DetailItem<T>(
    val itemDetail: LiveData<T>,
    val bookmarkState: LiveData<Boolean>,
    val networkState: LiveData<NetworkState>,
    val goBookmark: () -> Unit,
    val goRefresh: () -> Unit
)