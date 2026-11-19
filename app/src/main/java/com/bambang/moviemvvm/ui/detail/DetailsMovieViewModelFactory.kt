package com.bambang.moviemvvm.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bambang.moviemvvm.data.repositories.DetailsMovieRepository
import com.bambang.moviemvvm.data.repositories.ReviewMovieRepository
import com.bambang.moviemvvm.data.repositories.TrailerMovieRepository

class DetailsMovieViewModelFactory(
    private val repository: DetailsMovieRepository,
    private val reviewRepository: ReviewMovieRepository,
    private val trailerRepository: TrailerMovieRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsMovieViewModel(repository, reviewRepository, trailerRepository) as T
    }
}
