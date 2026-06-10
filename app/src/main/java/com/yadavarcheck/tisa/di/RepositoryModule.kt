package com.yadavarcheck.tisa.di
import com.yadavarcheck.tisa.data.repository.CheckRepositoryImpl
import com.yadavarcheck.tisa.data.repository.CustomerRepositoryImpl
import com.yadavarcheck.tisa.data.repository.NotificationRepositoryImpl
import com.yadavarcheck.tisa.domain.repository.CheckRepository
import com.yadavarcheck.tisa.domain.repository.CustomerRepository
import com.yadavarcheck.tisa.domain.repository.NotificationRepository
import dagger.Binds; import dagger.Module; import dagger.hilt.InstallIn; import dagger.hilt.components.SingletonComponent; import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton abstract fun bindCheckRepository(impl: CheckRepositoryImpl): CheckRepository
    @Binds @Singleton abstract fun bindNotificationRepository(impl: NotificationRepositoryImpl): NotificationRepository
    @Binds @Singleton abstract fun bindCustomerRepository(impl: CustomerRepositoryImpl): CustomerRepository
}
