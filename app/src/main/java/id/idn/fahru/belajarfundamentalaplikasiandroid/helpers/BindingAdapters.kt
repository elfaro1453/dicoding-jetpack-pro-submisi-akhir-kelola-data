package id.idn.fahru.belajarfundamentalaplikasiandroid.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar
import id.idn.fahru.belajarfundamentalaplikasiandroid.R
import id.idn.fahru.belajarfundamentalaplikasiandroid.api.ImageURL.getBackdropPath
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.genre.GenresItem
import id.idn.fahru.belajarfundamentalaplikasiandroid.data.source.remote.review.ResultsItemReview
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.adapter.RVReviewAdapter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)

@BindingAdapter("coilLoadImage")
fun coilLoadImage(view: ImageView, imageUrl: String?) {
    imageUrl?.let { view.load(getBackdropPath(it)) }
}

@BindingAdapter("ratingBar")
fun ratingBar(view: RatingBar, rating: Double?) {
    view.rating = (rating ?: 0.0).div(2).toFloat()
}

@BindingAdapter("statusReleased")
fun statusReleased(view: TextView, status: String?) {
    status?.let {
        view.text = view.context.resources.getString(R.string.status, status)
    }
}

@BindingAdapter("budget")
fun budget(view: TextView, digit: Any?) {
    if (digit is Double || digit is Int || digit is Float) {
        view.text =
            view.context.resources.getString(R.string.budget, currencyFormatter.format(digit))
    }
}

@BindingAdapter("income")
fun income(view: TextView, digit: Any?) {
    if (digit is Double || digit is Int || digit is Float) {
        view.text =
            view.context.resources.getString(R.string.revenue, currencyFormatter.format(digit))
    }
}

@BindingAdapter("releaseDate")
fun releaseDate(view: TextView, date: String?) {
    date?.let {
        val formatter =
            DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
        val formattedDate = LocalDate.parse(it).format(formatter)
        view.text = view.context.resources.getString(R.string.tanggal_rilis, formattedDate)
    }
}

private fun openLinkToWeb(url: String, context: Context) =
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

@BindingAdapter("imdb")
fun imdb(view: TextView, imdbId: String?) {
    if (imdbId == null) view.visibility = View.INVISIBLE
    imdbId?.let {
        with(view) {
            text = "IMBD"
            setOnClickListener {
                openLinkToWeb(
                    "https://www.imdb.com/title/$imdbId/",
                    it.context
                )
            }
            visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("homepage")
fun homepage(view: TextView, url: String?) {
    if (url == null) view.visibility = View.INVISIBLE
    url?.let {
        with(view) {
            text = context.resources.getString(R.string.homepage)
            setOnClickListener {
                openLinkToWeb(
                    url,
                    it.context
                )
            }
            visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("adapterReviewList")
fun adapterReviewList(view: RecyclerView, resource: List<ResultsItemReview>?) {
    resource?.let {
        val rvReviewAdapter = RVReviewAdapter()
        view.adapter = rvReviewAdapter
        if (it.isNotEmpty()) view.apply {
            rvReviewAdapter.addReviewList(it)
            setHasFixedSize(true)
            visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("genresAdapter")
fun genresAdapter(view: TextView, listGenres: List<GenresItem>?) {
    if (listGenres == null) view.visibility = View.INVISIBLE
    listGenres?.let { genres ->
        val genreNames = ArrayList<String>()
        genres.forEach {
            genreNames.add(it.name)
        }.run {
            view.text = genreNames.joinToString()
        }
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("seasonsTV")
fun seasonsTV(view: TextView, seasons: Int?) {
    seasons?.let {
        view.text = view.context.resources.getString(R.string.season, it)
    }
}

@BindingAdapter("episodesTV")
fun episodesTV(view: TextView, episodes: Int?) {
    episodes?.let {
        view.text = view.context.resources.getString(R.string.episodes, it)
    }
}

@BindingAdapter("isBookmarked")
fun isBookmarked(view: MaterialCardView, status: Boolean?) {
    val linearLayout = view.getChildAt(0) as LinearLayout
    val imageView = linearLayout.getChildAt(0) as ImageView
    val materialTextView = linearLayout.getChildAt(1) as MaterialTextView
    status?.let {
        if (status) {
            view.setCardBackgroundColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.colorPrimaryDark
                )
            )
            imageView.setImageResource(R.drawable.ic_bookmark_white_24dp)
            materialTextView.setTextColor(ContextCompat.getColor(view.context, R.color.white))
            materialTextView.text = view.resources.getString(R.string.unbookmark)
        } else {
            view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
            imageView.setImageResource(R.drawable.ic_bookmark_black_24dp)
            materialTextView.setTextColor(ContextCompat.getColor(view.context, R.color.dark))
            materialTextView.text = view.resources.getString(R.string.bookmark)
        }
    }
}

@BindingAdapter("reviewSize")
fun reviewSize(view: MaterialCardView, size: Int?) {
    if (size == null || size == 0) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("loadingStatus")
fun loadingStatus(view: SmoothProgressBar, networkState: NetworkState?) {
    networkState?.let {
        if (networkState == NetworkState.LOADING) view.visibility =
            View.VISIBLE else view.visibility = View.GONE
    }
}