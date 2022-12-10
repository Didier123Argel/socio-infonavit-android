package com.nextia.socioinfonavit.core.di

import com.nextia.socioinfonavit.data.repositories.UserRepositoryImplement
import com.nextia.socioinfonavit.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImplement: UserRepositoryImplement): UserRepository = userRepositoryImplement
}