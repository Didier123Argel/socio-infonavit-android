package com.nextia.socioinfonavit.core.di

import com.nextia.socioinfonavit.BuildConfig
import com.nextia.socioinfonavit.domain.apis.BenefitsApi
import com.nextia.socioinfonavit.domain.apis.UserApi
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
        ApiProvider(BuildConfig.API_BASE_URL, authInterceptor)

    @Provides
    @Singleton
    fun providesUserApi(apiProvider: ApiProvider) : UserApi =
        apiProvider.getEndpoint(UserApi::class.java)

    @Provides
    @Singleton
    fun providesBenefitsApi(apiProvider: ApiProvider) : BenefitsApi =
        apiProvider.getEndpoint(BenefitsApi::class.java)

}