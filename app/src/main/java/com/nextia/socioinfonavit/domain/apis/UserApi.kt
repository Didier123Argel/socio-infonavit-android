package com.nextia.socioinfonavit.domain.apis

import com.nextia.socioinfonavit.data.dto.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("v2/member/authentication/")
    fun login(
        @Body request: UserRequest
    ): Call<UserResponse>

    @GET("v1/member/landing_benevits/")
    fun getBenevits():Call<BenevitResponse>

    @POST("v1/member/member_benevits/search/")
    fun search(
        @Body request: SearchRequest
    ):Call<List<Benevit>>
}