package com.nextia.socioinfonavit.framework.api

import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.functional.Either.*
import com.nextia.socioinfonavit.core.functional.Either
import com.nextia.socioinfonavit.core.plataform.NetworkHandler
import com.nextia.socioinfonavit.domain.models.server.ErrorResponse
import okhttp3.Headers
import org.json.JSONObject
import retrofit2.Call

interface ApiRequest {
    companion object {
        const val defaultServerError = "{\"message\":\"Server Error\"}, \"error\":{\"Server Error\"}"
    }

    fun <T, R> makeRequest(
        networkHandler: NetworkHandler,
        call: Call<T>,
        transform: (T, Headers) -> R,
        default: T
    ): Either<Failure, R> {
        return when (networkHandler.isConnected) {
            true -> {
                try {
                    val response = call.execute()
                    when (response.isSuccessful) {
                        true -> Right(transform((response.body() ?: default), response.headers()))
                        false -> {
                            val errorBody =
                                JSONObject(response.errorBody()?.string()?:defaultServerError)
                            when(response.code()){
                                422 -> Left(Failure.Unauthorized(ErrorResponse(
                                    response.code(),
                                    errorBody.getString("msg"))))
                                else -> Left(Failure.ServerError(ErrorResponse(
                                    response.code(),
                                    errorBody.getString("error"))))
                            }
                        }
                    }
                } catch (e: Exception) {
                    Left(Failure.ServerError(ErrorResponse(500, e.message)))
                }
            }
            false -> Left(Failure.NetworkConnection)
            else -> { Left(Failure.NetworkConnection) }
        }
    }
}