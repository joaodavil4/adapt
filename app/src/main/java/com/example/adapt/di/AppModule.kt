package com.example.adapt.di

import android.content.Context
import com.example.adapt.data.datastore.DataStoreManager
import com.example.adapt.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(dataStoreManager: DataStoreManager): UserRepository {
        return UserRepository(dataStoreManager)
    }
}