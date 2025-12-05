package com.example.homework_27.data.module

import com.example.homework_27.data.network.DefaultNetworkMonitor
import com.example.homework_27.data.network.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindNetworkMonitor(
        impl: DefaultNetworkMonitor
    ): NetworkMonitor
}