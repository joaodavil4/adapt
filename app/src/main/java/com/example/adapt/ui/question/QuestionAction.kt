package com.example.adapt.ui.question

sealed class QuestionAction {
    data class SaveFavoriteColor(
        val color: String
    ) : QuestionAction()
    data class SendPrompt(
        val prompt: String
    ) : QuestionAction()
}