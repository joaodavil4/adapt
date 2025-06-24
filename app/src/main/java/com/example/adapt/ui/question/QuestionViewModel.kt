package com.example.adapt.ui.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adapt.domain.usecase.color.GetFavoriteColorUseCase
import com.example.adapt.domain.usecase.color.SaveFavoriteColorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val saveFavoriteColorUseCase: SaveFavoriteColorUseCase,
    private val getFavoriteColorUseCase: GetFavoriteColorUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<QuestionUiState> =
        MutableStateFlow(QuestionUiState.Initial())
    val uiState: StateFlow<QuestionUiState> =
        _uiState.asStateFlow()

    private val _selectedColor = MutableStateFlow<String?>(null)
    val selectedColor: StateFlow<String?> = _selectedColor.asStateFlow()

    fun onAction(action: QuestionAction) {
        when (action) {
            is QuestionAction.SaveFavoriteColor -> setFavoriteColor(action.color)
        }
    }

    init {
        viewModelScope.launch {
            getFavoriteColorUseCase().collectLatest { color ->
                _selectedColor.value = color
            }
        }
    }

    private fun setFavoriteColor(color: String) {
        _selectedColor.update { color }
        _uiState.update { QuestionUiState.Initial(selectedColor = color) }
        viewModelScope.launch {
            saveFavoriteColorUseCase(color)
        }
    }
}

sealed interface QuestionUiState {

    /**
     * Initial state before any action is taken
     */
    data class Initial(
        val selectedColor: String? = null
    ) : QuestionUiState

}