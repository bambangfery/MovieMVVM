package com.bambang.moviemvvm.ui.detail

import androidx.lifecycle.ViewModel
import com.bambang.moviemvvm.data.repositories.DetailsMovieRepository
import com.bambang.moviemvvm.data.repositories.ReviewMovieRepository
import com.bambang.moviemvvm.data.repositories.TrailerMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailsMovieViewModel(
    private val repository: DetailsMovieRepository,
    private val reviewRepository: ReviewMovieRepository,
    private val trailerRepository: TrailerMovieRepository
) : ViewModel() {
    suspend fun getDetailsMovie(
        key: String,
        id: String
    ) = withContext(Dispatchers.IO) {  repository.getDetailsMovie(key, id) }

    suspend fun getReviewMovie(
        key: String,
        id: String
    ) = withContext(Dispatchers.IO) {  reviewRepository.getReviewMovie(key, id) }

    suspend fun getTrailerMovie(
        key: String,
        id: String
    ) = withContext(Dispatchers.IO) {  trailerRepository.getTrailerMovie(key, id) }
}
