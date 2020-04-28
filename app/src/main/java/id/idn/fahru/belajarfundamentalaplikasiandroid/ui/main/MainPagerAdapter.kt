package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.idn.fahru.belajarfundamentalaplikasiandroid.R
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.fragmentmovie.MovieFragment
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.fragmenttv.TVFragment

private val TAB_TITLES = arrayOf(
    R.string.label_movie,
    R.string.label_tv
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class MainPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MovieFragment()
            1 -> fragment = TVFragment()
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}