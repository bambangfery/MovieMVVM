package com.bambang.moviemvvm.data.model

import com.google.gson.annotations.SerializedName

data class Genres(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("name")
	val name: String

)
