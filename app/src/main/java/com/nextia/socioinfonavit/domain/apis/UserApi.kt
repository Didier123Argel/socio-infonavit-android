package com.nextia.socioinfonavit.domain.apis

import com.nextia.socioinfonavit.data.dto.BenevitResponse
import com.nextia.socioinfonavit.data.dto.LoginRequest
import com.nextia.socioinfonavit.data.dto.UserResponse
import com.nextia.socioinfonavit.data.dto.Wallet
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("login/")
    fun login(
        @Body request: LoginRequest
    ): Call<UserResponse>

    @DELETE("logout/")
    fun logout(): Call<Unit>

    @GET("member/wallets/")
    fun getWallets(): Call<List<Wallet>>

    @GET("member/landing_benevits/")
    fun getBenevits():Call<BenevitResponse>
}