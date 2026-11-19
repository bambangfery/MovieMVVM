package com.bambang.moviemvvm.data.model

import com.google.gson.annotations.SerializedName

data class TrailerMovieResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("results")
	val results: ArrayList<TrailerMovie>
)