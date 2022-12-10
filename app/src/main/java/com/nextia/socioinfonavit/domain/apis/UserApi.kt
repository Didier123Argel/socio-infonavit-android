package com.nextia.socioinfonavit.domain.apis

import com.nextia.socioinfonavit.data.dto.LoginRequest
import com.nextia.socioinfonavit.data.dto.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface UserApi {
    @POST("login/")
    fun login(
        @Body request: LoginRequest
    ): Call<UserResponse>

    @DELETE("logout/")
    fun logout(): Call<Unit>
}