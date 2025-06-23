package com.example.adapt.di

import android.content.Context
import com.example.adapt.data.datastore.DataStoreManager
import com.example.adapt.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

    @Provides
    fun provideUserRepository(dataStoreManager: DataStoreManager): UserRepository {
        return UserRepository(dataStoreManager)
    }
}