package com.nextia.socioinfonavit.data.dto

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("user") val userRequest: UserRequest
    )

data class UserRequest (
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)


data class UserResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: String,
    @SerializedName("member") val member: Member,
    @SerializedName("sign_in_count") val sign_in_count: Int
    ) {
    companion object {
        fun empty() = UserResponse(
            -1,
            "",
            "",
            Member(-1, -1, "", "", "", "", "", ""),
            -1)

        fun emptyJson() = Gson().toJson(empty())
    }
}

data class Member (
    @SerializedName("id") val id: Long,
    @SerializedName("user_id") val user_id: Long,
    @SerializedName("id_socio_infonavit") val id_socio_infonavit: String,
    @SerializedName("name") val name: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("mobile") val mobile: String,
    @SerializedName("zipcode") val zipcode: String,
    @SerializedName("avatar") val avatar: String
    )