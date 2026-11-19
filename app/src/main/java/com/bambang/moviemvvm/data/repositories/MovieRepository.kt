package com.bambang.moviemvvm.data.repositories

import com.bambang.moviemvvm.data.network.MyApi
import com.bambang.moviemvvm.data.network.SafeApiRequest
import com.bambang.moviemvvm.data.model.GenresResponse
import com.bambang.moviemvvm.data.model.ResultsMoviesResponse

class MovieRepository(
    private val api: MyApi
) : SafeApiRequest() {
    suspend fun getGenresMovie(key: String): GenresResponse{
        return apiRequest { api.getGenresMovie(key) }
    }

    suspend fun getResultsMovie(key: String, genre: String, page: String): ResultsMoviesResponse{
        return apiRequest { api.getResultsMovie(key, genre, page) }
    }
}