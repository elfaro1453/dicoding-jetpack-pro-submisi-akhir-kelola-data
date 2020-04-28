package id.idn.fahru.belajarfundamentalaplikasiandroid.di.annotations

import javax.inject.Scope

/**
 * Referensi annotation Scope for dagger
 * https://medium.com/@elye.project/dagger-2-for-dummies-in-kotlin-scope-d51a6b6e077f
 * https://medium.com/@khreniak/dagger-scopes-simple-explanation-184684707227
 */
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope
