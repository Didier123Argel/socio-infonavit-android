package com.nextia.socioinfonavit.domain.models.server

import com.google.gson.annotations.SerializedName

data class PaginatedResponse<T>(
    @SerializedName("data") val data: T,
    @SerializedName("pagination") val pagination: Pagination?)

data class Pagination(@SerializedName("total_rows") var totalItems : Int,
                      @SerializedName("per_page") var itemsPerPage : Int,
                      @SerializedName("current_page") var currentPage : Int,
                      @SerializedName("links") var links: Links
){

    fun hasNextPage(): Boolean = !links.next.isNullOrBlank()

    companion object{
        fun empty() = Pagination(0, 0, 0, Links(null, null, null, null))
    }
}

data class Links(
    @SerializedName("first") var first : String?,
    @SerializedName("last") var last : String?,
    @SerializedName("next") var next : String?,
    @SerializedName("prev") var prev : String?
)