package com.nextia.socioinfonavit.core.helpers

import android.content.Context
import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
import com.google.gson.Gson
import com.nextia.socioinfonavit.BuildConfig
import com.nextia.socioinfonavit.data.dto.UserResponse
import com.nextia.socioinfonavit.framework.api.AuthorizationInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Authenticator @Inject constructor(
    @ApplicationContext context: Context,
    private val authorizationInterceptor: AuthorizationInterceptor
) {

    companion object {
        private const val PREF_AUTHENTICATOR = BuildConfig.APPLICATION_ID
        private const val TOKEN = "key_token"
        private const val REFRESH_TOKEN = "key_refresh_token"
        private const val USER_OBJECT = "key_user_object"
    }

    private val prefs = context.getSharedPreferences(PREF_AUTHENTICATOR, Context.MODE_PRIVATE)

    init {
        authorizationInterceptor.setAuthToken(token)
    }

    val isAuthenticated: Boolean
        get() = token != null

    val token: String?
        get() = prefs.getString(TOKEN, null)

    val refreshToken: String?
        get() = prefs.getString(REFRESH_TOKEN, null)

    fun setToken(token: String, refreshToken: String){
        prefs.edit()
            .putString(TOKEN, token)
            .putString(REFRESH_TOKEN, refreshToken)
            .apply()
        authorizationInterceptor.setAuthToken(token)
    }

    var user: UserResponse
        get() = Gson().fromJson(prefs.getString(USER_OBJECT, UserResponse.emptyJson()), UserResponse::class.java)
        set(value) {prefs.edit().putString(USER_OBJECT, Gson().toJson(value)).apply()}

    fun getIntValueInToke(key: String): Int? {
        return try {
            val jwt = token?.let { JWT(it) }
            jwt?.getClaim(key)?.asInt()
        }catch (e: DecodeException){
            null
        }
    }

    fun getStringValueInToke(key: String): String? {
        return try {
            val jwt = token?.let { JWT(it) }
            jwt?.getClaim(key)?.asString()
        }catch (e: DecodeException){
            null
        }
    }


    fun clear(){
        prefs.edit()
            .putString(TOKEN, null)
            .putString(REFRESH_TOKEN, null)
            .apply()
        authorizationInterceptor.setAuthToken(null)
    }

}