package com.bambang.moviemvvm.data.repositories

import com.bambang.moviemvvm.data.network.MyApi
import com.bambang.moviemvvm.data.network.SafeApiRequest
import com.bambang.moviemvvm.data.model.DetailsMovieResponse

class DetailsMovieRepository(
    private val api: MyApi
) : SafeApiRequest()  {
    suspend fun getDetailsMovie(key: String, id: String): DetailsMovieResponse {
        return apiRequest { api.getDetailsMovie(id, key) }
    }
}