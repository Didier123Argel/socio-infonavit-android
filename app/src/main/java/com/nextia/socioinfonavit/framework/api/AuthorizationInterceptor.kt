package com.nextia.socioinfonavit.framework.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationInterceptor @Inject constructor() : Interceptor {
    private var authToken: String? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (authToken != null) {
            println("Bearer $authToken")

            val original = chain.request()
            val builder = original.newBuilder()
                .method(original.method, original.body)
            builder.header("Authorization", "$authToken")
            chain.proceed(builder.build())
        } else {
            chain.proceed(chain.request())
        }
    }

    fun setAuthToken(token: String?) {
        authToken = token
    }
}