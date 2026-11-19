package com.bambang.moviemvvm.data.model

import com.google.gson.annotations.SerializedName

data class GenresResponse (
    @SerializedName("genres")
    val genres: ArrayList<Genres?>
)
