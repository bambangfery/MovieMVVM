package com.bambang.moviemvvm.data.repositories

import com.bambang.moviemvvm.data.network.MyApi
import com.bambang.moviemvvm.data.network.SafeApiRequest
import com.bambang.moviemvvm.data.model.ReviewMovieResponse

class ReviewMovieRepository(
    private val api: MyApi
) : SafeApiRequest()   {
    suspend fun getReviewMovie(key: String, id: String): ReviewMovieResponse {
        return apiRequest { api.getReviewMovie(id, key) }
    }
}