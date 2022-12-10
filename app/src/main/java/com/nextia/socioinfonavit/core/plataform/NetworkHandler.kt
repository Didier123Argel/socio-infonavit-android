package com.nextia.socioinfonavit.core.plataform

import android.content.Context
import com.nextia.socioinfonavit.core.extension.networkInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler
@Inject constructor(@ApplicationContext private val context: Context){
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}