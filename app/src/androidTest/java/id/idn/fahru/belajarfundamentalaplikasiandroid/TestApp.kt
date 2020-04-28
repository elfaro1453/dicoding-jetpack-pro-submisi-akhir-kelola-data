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

package id.idn.fahru.belajarfundamentalaplikasiandroid

import androidx.test.espresso.IdlingRegistry
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.facebook.stetho.Stetho
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.DaggerApplication
import id.idn.fahru.belajarfundamentalaplikasiandroid.di.DaggerTestAppComponent
import okhttp3.OkHttpClient
import timber.log.Timber

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 *  https://medium.com/freenet-engineering/running-android-espresso-tests-with-data-binding-and-koin-a57a8d38daa5
 */
class TestApp : DaggerApplication() {

    private val appComponent = DaggerTestAppComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)

        val httpClient = OkHttpClient.Builder()
            .cache(CoilUtils.createDefaultCache(this))
            .build()
        IdlingRegistry.getInstance()
            .register(OkHttp3IdlingResource.create("coil", httpClient))
        Coil.setDefaultImageLoader {
            ImageLoader(this) {
                crossfade(true)
                okHttpClient {
                    httpClient
                }
            }
        }
    }

    override fun applicationInjector() = appComponent
}