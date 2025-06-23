package com.example.adapt.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class DataStoreManager(private val context: Context) {

    companion object {
        private val FAVORITE_COLOR_KEY = stringPreferencesKey("favorite_color")
    }

    // Save the selected color
    suspend fun saveFavoriteColor(color: String) {
        context.dataStore.edit { preferences ->
            preferences[FAVORITE_COLOR_KEY] = color
        }
    }

    // Retrieve the selected color
    val favoriteColor: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[FAVORITE_COLOR_KEY]
    }
}