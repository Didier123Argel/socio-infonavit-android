package com.nextia.socioinfonavit.data.dto

import com.google.gson.annotations.SerializedName

data class SearchRequest(
    @SerializedName("query") var text: String
)