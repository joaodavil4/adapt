package com.example.adapt.di

import com.example.adapt.data.repository.UserRepository
import com.example.adapt.domain.usecase.color.GetFavoriteColorUseCase
import com.example.adapt.domain.usecase.color.SaveFavoriteColorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideSaveFavoriteColorUseCase(userRepository: UserRepository): SaveFavoriteColorUseCase {
        return SaveFavoriteColorUseCase(userRepository)
    }

    @Provides
    fun provideGetFavoriteColorUseCase(userRepository: UserRepository): GetFavoriteColorUseCase {
        return GetFavoriteColorUseCase(userRepository)
    }
}