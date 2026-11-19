package com.bambang.moviemvvm.data.repositories

import com.bambang.moviemvvm.data.network.MyApi
import com.bambang.moviemvvm.data.network.SafeApiRequest
import com.bambang.moviemvvm.data.model.TrailerMovieResponse

class TrailerMovieRepository(
    private val api: MyApi
) : SafeApiRequest()    {
    suspend fun getTrailerMovie(key: String, id: String): TrailerMovieResponse {
        return apiRequest { api.getTrailerMovie(id, key) }
    }
}