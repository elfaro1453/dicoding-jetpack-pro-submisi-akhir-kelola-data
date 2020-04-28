package id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.favourite

import android.os.Bundle
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import id.idn.fahru.belajarfundamentalaplikasiandroid.R
import id.idn.fahru.belajarfundamentalaplikasiandroid.databinding.ActivityFavouriteBinding
import javax.inject.Inject

class FavouriteActivity : DaggerAppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityFavouriteBinding = ActivityFavouriteBinding.inflate(layoutInflater)
        val sectionsPagerAdapter = FavPagerAdapter(this, supportFragmentManager)
        activityFavouriteBinding.apply {
            setContentView(root)
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            title = root.context.getString(R.string.bookmark)
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
