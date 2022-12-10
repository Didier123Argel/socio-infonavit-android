package com.nextia.socioinfonavit.domain.models.server

import com.google.gson.annotations.SerializedName

data class ErrorResponse (
    @SerializedName("code") val code : Int?,
    @SerializedName("message") val message : String?,
    @SerializedName("errors") val errors : List<String>? = null

){
    companion object{
        fun empty() = ErrorResponse(null, null, null)
    }
}