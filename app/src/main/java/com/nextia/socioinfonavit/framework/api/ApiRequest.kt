package com.nextia.socioinfonavit.framework.api

import android.util.Log
import com.google.gson.JsonParseException
import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.functional.Either.*
import com.nextia.socioinfonavit.core.functional.Either
import com.nextia.socioinfonavit.domain.models.server.ErrorResponse
import okhttp3.Headers
import org.json.JSONObject
import retrofit2.Call

interface ApiRequest {
    fun <T, R> makeRequest(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Right(transform((response.body() ?: default)))
                false -> {
                    try {
                        val errorBody = JSONObject(response.errorBody()?.string()?:"{\"message\":\"Server Error\"}, \"error\":{\"Server Error\"}")

                        when(response.code()){
                            401 -> Left(Failure.Unauthorized(ErrorResponse(response.code(), errorBody.getString("error"))))
                            else -> Left(Failure.ServerError(ErrorResponse(response.code(), errorBody.getString("error"))))
                        }
                    }
                    catch (e: JsonParseException){
                        Left(Failure.ServerError(ErrorResponse(1,"Unknown Server Error")
                        ))
                    }
                }
            }
        } catch (exception: Throwable) {
            Log.e("ApiRequest", exception.message?:"message empty", exception)
            Left(Failure.DebugError(
                "message = {\n${exception.message?:"null"}\n}\n" +
                "stackTrace = {\n${exception.stackTrace.contentToString()?:"null"}\n}"))
        }
    }

    fun <T, R> makeRequest(call: Call<T>, transform: (T, Headers) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Right(transform((response.body() ?: default), response.headers()))
                false -> {
                    try {
                        val errorBody = JSONObject(response.errorBody()?.string()?:"{\"message\":\"Server Error\"}, \"error\":{\"Server Error\"}")

                        when(response.code()){
                            401 -> Left(Failure.Unauthorized(ErrorResponse(response.code(), errorBody.getString("error"))))
                            else -> Left(Failure.ServerError(ErrorResponse(response.code(), errorBody.getString("error"))))
                        }
                    }
                    catch (e: JsonParseException){
                        Left(Failure.ServerError(ErrorResponse(1,"Unknown Server Error")
                        ))
                    }
                }
            }
        } catch (exception: Throwable) {
            Log.e("ApiRequest", exception.message?:"message empty", exception)
            Left(Failure.DebugError(
                "message = {\n${exception.message?:"null"}\n}\n" +
                        "stackTrace = {\n${exception.stackTrace.contentToString()?:"null"}\n}"))
        }
    }
}