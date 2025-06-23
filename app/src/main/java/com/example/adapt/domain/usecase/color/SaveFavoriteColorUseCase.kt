package com.example.adapt.domain.usecase.color

import com.example.adapt.data.repository.UserRepository
import javax.inject.Inject

class SaveFavoriteColorUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(color: String) {
        userRepository.saveFavoriteColor(color)
    }
}