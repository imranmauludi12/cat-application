package com.dicoding.mycatapplication.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetBreedsResponse(

	@field:SerializedName("data")
	val data: List<DataItem>? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("current_page")
	val currentPage: Int? = null

)

data class DataItem(

	@field:SerializedName("country")
	val country: String,

	@field:SerializedName("coat")
	val coat: String,

	@field:SerializedName("origin")
	val origin: String,

	@field:SerializedName("pattern")
	val pattern: String,

	@field:SerializedName("breed")
	val breed: String
)

