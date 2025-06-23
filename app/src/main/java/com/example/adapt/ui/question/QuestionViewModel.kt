package com.example.adapt.ui.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adapt.data.repository.UserRepository
import com.example.adapt.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    private val _selectedColor = MutableStateFlow<String?>(null)
    val selectedColor: StateFlow<String?> = _selectedColor.asStateFlow()

    fun setFavoriteColor(color: String) {
        _selectedColor.value = color
        viewModelScope.launch {
            userRepository.saveFavoriteColor(color)
        }
    }

}