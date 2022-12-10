package com.nextia.socioinfonavit.core.di

import com.nextia.socioinfonavit.framework.api.ApiProvider
import com.nextia.socioinfonavit.framework.api.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideApiProvider(authInterceptor: AuthorizationInterceptor) : ApiProvider =
        ApiProvider("", authInterceptor)

}