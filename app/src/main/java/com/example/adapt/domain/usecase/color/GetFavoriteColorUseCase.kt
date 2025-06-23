package com.example.adapt.domain.usecase.color

import com.example.adapt.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteColorUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<String?> {
        return userRepository.getFavoriteColor()
    }
}