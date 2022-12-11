package com.nextia.socioinfonavit.framework.api

import android.annotation.SuppressLint
import com.google.gson.GsonBuilder
import com.nextia.socioinfonavit.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ApiProvider(baseUrl: String, authorizationInterceptor: AuthorizationInterceptor) {
    private var retrofit: Retrofit

    init {
        val httpClientBuilder = getUnsafeOkHttpClient()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            httpClientBuilder.addInterceptor(logging)
        }

        httpClientBuilder.apply {
            addInterceptor(authorizationInterceptor)
            cache(null)
        }

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ")
            .setLenient()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClientBuilder.build())
            .build()

    }

    fun <S> getEndpoint(serviceClass: Class<S>): S = retrofit.create(serviceClass)

    /**
     * TODO(It seems your certificate has expired. This is a temporary solution)
     **/
    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder =
        try {
            val trustAllCerts: Array<TrustManager> = arrayOf(
                @SuppressLint("CustomX509TrustManager")
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate?>?,
                                                    authType: String?) = Unit

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate?>?,
                                                    authType: String?) = Unit

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
            )
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory,
                trustAllCerts[0] as X509TrustManager
            )
            builder.hostnameVerifier { _, _ -> true }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
}