# 🎬 MovieMVVM

Aplikasi katalog film Android native berbasis **Kotlin** yang menerapkan arsitektur **MVVM (Model-View-ViewModel)** dan Repository Pattern untuk menampilkan data film secara dinamis dari **The Movie Database (TMDB) API**.

---

## ✨ Fitur Utama

- **Filter Film Berdasarkan Genre**: Navigasi chip kategori genre horizontal (*Action, Adventure, Animation, Comedy, Crime,* dll.) untuk memfilter daftar film secara dinamis[cite: 2, 3].
- **Daftar Film & Infinite Scroll**: Menampilkan poster film, judul, tanggal rilis, jumlah rating, dan ringkasan (*overview*) lengkap dengan dukungan pagination otomatis saat di-scroll (`RecyclerViewLoadMoreScroll`)[cite: 2, 3].
- **Halaman Detail Film Lengkap**: Menyajikan gambar backdrop, judul, tanggal rilis, jumlah rating, label genre, dan sinopsis lengkap (*overview*)[cite: 4].
- **Ulasan Pengguna (*User Reviews*)**: Menampilkan ulasan dan tanggapan penonton terkait film yang dipilih langsung dari endpoint review TMDB[cite: 1, 4].
- **Pemutar Trailer YouTube Terintegrasi**: Memutar trailer resmi film secara langsung di dalam aplikasi menggunakan `android-youtube-player`[cite: 4, 5].
- **Penanganan Koneksi & Error Terpusat**: Dilengkapi `NetworkConnectionInterceptor` dan wrapper `SafeApiRequest` untuk menangani status jaringan dan respons API secara aman[cite: 1].

---

## 🛠️ Tech Stack & Arsitektur

Project ini dibangun menggunakan pola arsitektur **MVVM** dengan pemisahan layer data, domain/repository, dan UI yang jelas:

- **Bahasa**: Kotlin
- **Arsitektur**: MVVM (Model - View - ViewModel) + Repository Pattern (`ViewModelFactory`)[cite: 1, 2]
- **UI & Layout**: XML Layout (`RecyclerView`, Custom Chip Drawables, ConstraintLayout/LinearLayout)[cite: 2, 3]
- **Networking**: Retrofit & OkHttp (`ApiClient`, `NetworkConnectionInterceptor`, `SafeApiRequest`)[cite: 1]
- **Asynchronous**: Kotlin Coroutines[cite: 2]
- **Video Player**: [Android YouTube Player](https://github.com/PierfrancescoSoffritti/android-youtube-player)[cite: 5]
- **API Source**: [The Movie Database (TMDB) API v3](https://developer.themoviedb.org/docs)

---

## 📂 Struktur Project

```text
com.bambang.moviemvvm
├── data
│   ├── model                            # Data class & response model dari TMDB API
│   │   ├── DetailsMovieResponse.kt
│   │   ├── Genres.kt
│   │   ├── GenresItem.kt
│   │   ├── GenresResponse.kt
│   │   ├── ProductionCompaniesItem.kt
│   │   ├── ProductionCountriesItem.kt
│   │   ├── ResultsMovies.kt
│   │   ├── ResultsMoviesResponse.kt
│   │   ├── ReviewMovie.kt
│   │   ├── ReviewMovieResponse.kt
│   │   ├── SpokenLanguagesItem.kt
│   │   ├── TrailerMovie.kt
│   │   └── TrailerMovieResponse.kt
│   ├── network                          # Konfigurasi Retrofit, Interceptor & SafeApiRequest
│   │   ├── ApiClient.kt
│   │   ├── MyApi.kt
│   │   ├── NetworkConnectionInterceptor.kt
│   │   ├── SafeApiRequest.kt
│   │   └── ServerManager.kt
│   └── repositories                     # Layer abstraksi sumber data API
│       ├── DetailsMovieRepository.kt
│       ├── MovieRepository.kt
│       ├── ReviewMovieRepository.kt
│       └── TrailerMovieRepository.kt
├── ui
│   ├── detail                           # Halaman detail film, review & pemutar trailer
│   │   ├── DetailsMovieActivity.kt
│   │   ├── DetailsMovieViewModel.kt
│   │   ├── DetailsMovieViewModelFactory.kt
│   │   └── GenreListAdapter.kt
│   └── movie                            # Halaman utama daftar film & filter genre
│       ├── adapter
│       │   ├── GenresItemsAdapter.kt
│       │   └── ResultsMoviesAdapter.kt
│       ├── MovieActivity.kt
│       ├── MovieViewModel.kt
│       └── MovieViewModelFactory.kt
├── utils                                # Konstanta, Coroutines helper, Exceptions & Pagination
│   ├── Constant.kt
│   ├── Coroutines.kt
│   ├── Exceptions.kt
│   ├── OnLoadMoreListener.kt
│   └── RecyclerViewLoadMoreScroll.kt
└── MVVMApplication.kt                   # Application class
