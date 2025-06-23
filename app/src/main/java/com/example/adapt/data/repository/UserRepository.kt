package com.example.adapt.data.repository

import com.example.adapt.data.datastore.DataStoreManager
import kotlinx.coroutines.flow.Flow

class UserRepository(private val dataStoreManager: DataStoreManager) {

    // Save the favorite color
    suspend fun saveFavoriteColor(color: String) {
        dataStoreManager.saveFavoriteColor(color)
    }

    // Retrieve the favorite color
    fun getFavoriteColor(): Flow<String?> {
        return dataStoreManager.favoriteColor
    }
}