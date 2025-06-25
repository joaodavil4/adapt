package com.example.adapt.ui.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adapt.domain.usecase.color.GetFavoriteColorUseCase
import com.example.adapt.domain.usecase.color.SaveFavoriteColorUseCase
import com.example.adapt.domain.usecase.color.SendPromptUseCase
import com.example.adapt.ui.Result
import com.example.adapt.util.PROMPT_COLOR
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
    private val getFavoriteColorUseCase: GetFavoriteColorUseCase,
    private val sendPromptUseCase: SendPromptUseCase
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
            is QuestionAction.SendPrompt -> sendPrompt(action.prompt)
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

    private fun sendPrompt(prompt: String) {
        viewModelScope.launch {
            sendPromptUseCase.execute(
                prompt = PROMPT_COLOR.plus(prompt)
            ).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        // Handle error state, e.g., show a message to the user
                        _uiState.update { currentState ->
                            when (currentState) {
                                is QuestionUiState.Initial -> currentState.copy(
                                    isLoading = false,
                                    feedbackColor = result.message
                                )
                            }
                        }
                    }

                    Result.Loading -> {
                        // Handle loading state if needed
                        _uiState.update { currentState ->
                            when (currentState) {
                                is QuestionUiState.Initial -> currentState.copy(isLoading = true)
                            }
                        }
                    }

                    is Result.Success<*> -> {
                        val response = result.data as String
                        // Handle the response from the prompt
                        _uiState.update { currentState ->
                            when (currentState) {
                                is QuestionUiState.Initial -> currentState.copy(
                                    isLoading = false,
                                    feedbackColor = response
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed interface QuestionUiState {

    /**
     * Initial state before any action is taken
     */
    data class Initial(
        val isLoading: Boolean = false,
        val selectedColor: String? = null,
        val feedbackColor: String? = null,
        val canGoForward: Boolean = false
    ) : QuestionUiState

}