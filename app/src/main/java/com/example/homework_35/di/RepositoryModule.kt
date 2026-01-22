package com.example.homework_35.di

import com.example.homework_35.data.repository.OrdersRepositoryImpl
import com.example.homework_35.domain.repository.OrdersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OrdersBindModule {
    @Binds
    @Singleton
    abstract fun bindOrdersRepository(
        impl: OrdersRepositoryImpl
    ): OrdersRepository
}