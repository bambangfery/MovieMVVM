package com.bambang.moviemvvm.ui.movie

import androidx.lifecycle.ViewModel
import com.bambang.moviemvvm.data.repositories.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel(){

    suspend fun getGenresMovie(
        key: String
    ) = withContext(Dispatchers.IO) {  repository.getGenresMovie(key) }

    suspend fun getResultsMovie(
        key: String,
        genre: String,
        page: String
    ) = withContext(Dispatchers.IO) {  repository.getResultsMovie(key,genre,page) }

}