package com.bambang.moviemvvm.data.network

import com.bambang.moviemvvm.data.model.GenresResponse
import com.bambang.moviemvvm.data.model.DetailsMovieResponse
import com.bambang.moviemvvm.data.model.ResultsMoviesResponse
import com.bambang.moviemvvm.data.model.ReviewMovieResponse
import com.bambang.moviemvvm.data.model.TrailerMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyApi {
    @GET("genre/movie/list")
    suspend fun getGenresMovie(
        @Query("api_key") key: String
    ) : Response<GenresResponse>

    @GET("discover/movie")
    suspend fun getResultsMovie(
        @Query("api_key") key: String,
        @Query("with_genres") genre: String,
        @Query("page") page: String
    ) : Response<ResultsMoviesResponse>

    @GET("movie/{id_movie}")
    suspend fun getDetailsMovie(
        @Path("id_movie") id: String,
        @Query("api_key") key: String
    ) : Response<DetailsMovieResponse>

    @GET("movie/{id_movie}/reviews")
    suspend fun getReviewMovie(
        @Path("id_movie") id: String,
        @Query("api_key") key: String
    ) : Response<ReviewMovieResponse>

    @GET("movie/{id_movie}/videos")
    suspend fun getTrailerMovie(
        @Path("id_movie") id: String,
        @Query("api_key") key: String
    ) : Response<TrailerMovieResponse>

}

