package com.yadavarcheck.tisa.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.yadavarcheck.tisa.data.repository.*
import com.yadavarcheck.tisa.domain.repository.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton abstract fun bindCheckRepo(impl: CheckRepositoryImpl): CheckRepository
    @Binds @Singleton abstract fun bindCustomerRepo(impl: CustomerRepositoryImpl): CustomerRepository
    @Binds @Singleton abstract fun bindNotifRepo(impl: NotificationRepositoryImpl): NotificationRepository
}
