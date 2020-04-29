# Dicoding Jetpack Pro Submisi Akhir Kelola Data
Ini adalah project Dicoding kelas Belajar Android Jetpack Pro - Submission Akhir Kelola Data, tampilannya sederhana banget karena dikejar waktu langganan yang mau habis. Ntar bakalan diupdate lagi kalau kelas Material Design dan Menjadi Android Developer Expert telah dibuka. :)

## Show Up
<img src="/images/main-activity.jpg" width="32%"/> <img src="/images/favourite-activity.jpg" width="32%" /> <img src="/images/detail-activity.jpg" width="32%"/>

## Komponen
Saya lebih fokus pada kode program daripada kode UI/UX, so bahas kode programnya aja yap :
  * Ditulis dengan [Kotlin](https://kotlinlang.org "Kotlin Lang")
  * Localization Support
  * [View Binding](https://developer.android.com/topic/libraries/view-binding) sebagai pengganti findViewbyId dan Kotlin Synthetic
  * [Data Binding](https://developer.android.com/topic/libraries/data-binding) agar lebih mudah observe livedata
  * [Retrofit](https://square.github.io/retrofit) sebagai API client
  * [Google GSON](https://github.com/google/gson) sebagai JSON converter ke Kotlin data class
  * [Paging Library](https://developer.android.com/topic/libraries/architecture/paging) untuk membuat efek Endless RecyclerView.
  * [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room) sebagai penyimpanan Data
  * [Dagger 2](https://github.com/google/dagger) untuk Dependency Injection

## Cara Menjalankan Aplikasi
Aplikasi akan error saat pertama kali dijalankan karena anda perlu memasukkan API_Key dari themoviedb, cara mendapatkan api_key bisa dilihat di postingan [blog dicoding](https://www.dicoding.com/blog/registrasi-testing-themoviedb-api/)

Buka build.gradle di module.app, ganti bagian YOUR_API_KEY di `buildConfigField("String", "TMDB_API_KEY", "Your_API_Key")` dengan api_key yang anda dapatkan sebelumnya.

Semoga bermanfaat.
