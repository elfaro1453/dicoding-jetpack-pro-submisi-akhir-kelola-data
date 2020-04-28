package id.idn.fahru.belajarfundamentalaplikasiandroid

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import id.idn.fahru.belajarfundamentalaplikasiandroid.databinding.ActivityMainBinding
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.MainPagerAdapter
import id.idn.fahru.belajarfundamentalaplikasiandroid.ui.main.favourite.FavouriteActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val sectionsPagerAdapter = MainPagerAdapter(this, supportFragmentManager)
        activityMainBinding.apply {
            setContentView(root)
            setSupportActionBar(toolbar)
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(viewPager)
            bookmarkButton.setOnClickListener {
                startActivity(Intent(root.context, FavouriteActivity::class.java))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}