package ir.yadavarchek.pro.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.yadavarchek.pro.data.repository.CheckRepositoryImpl
import ir.yadavarchek.pro.data.repository.CustomerRepositoryImpl
import ir.yadavarchek.pro.data.repository.NotificationRepositoryImpl
import ir.yadavarchek.pro.domain.repository.CheckRepository
import ir.yadavarchek.pro.domain.repository.CustomerRepository
import ir.yadavarchek.pro.domain.repository.NotificationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindCheckRepository(impl: CheckRepositoryImpl): CheckRepository

    @Binds @Singleton
    abstract fun bindCustomerRepository(impl: CustomerRepositoryImpl): CustomerRepository

    @Binds @Singleton
    abstract fun bindNotificationRepository(impl: NotificationRepositoryImpl): NotificationRepository
}
