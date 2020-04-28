package id.idn.fahru.belajarfundamentalaplikasiandroid.di.annotations

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Referensi jikalau lupa ini buat apa
 * https://www.youtube.com/watch?v=jCYj_MYCgEQ
 * Dagger 2 MultiBinding - Inject into ViewModels using MVVM, Dagger and Kotlin
 */
@MapKey
@Target(AnnotationTarget.FUNCTION)
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
