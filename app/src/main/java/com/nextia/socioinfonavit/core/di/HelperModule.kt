package com.nextia.socioinfonavit.core.di

import android.content.Context
import com.nextia.socioinfonavit.R
import com.nextia.socioinfonavit.core.utils.CipherHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HelperModule {
    @Provides
    @Singleton
    fun provideCipherHelper(
        @ApplicationContext context: Context
    ): CipherHelper = CipherHelper(context.getString(R.string.asymmetric_encryption))
}