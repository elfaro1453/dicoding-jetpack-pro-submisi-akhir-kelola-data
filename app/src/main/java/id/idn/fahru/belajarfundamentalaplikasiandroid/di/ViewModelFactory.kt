/**
 * Kegunaan ViewModelFactory :
 * https://codelabs.developers.google.com/codelabs/kotlin-android-training-view-model/#7
 * https://github.com/android/architecture-components-samples/blob/fc20e0a99a5ba1624a7c28ac21317726859e8d04/GithubBrowserSample/app/src/main/java/com/android/example/github/di/ViewModelModule.kt
 */
package id.idn.fahru.belajarfundamentalaplikasiandroid.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = viewModels[modelClass] ?: viewModels.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
