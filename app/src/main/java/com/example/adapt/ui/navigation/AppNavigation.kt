package com.example.adapt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.adapt.ui.question.QuestionScreen
import com.example.adapt.ui.question.QuestionViewModel

@Suppress("UnusedPrivateProperty")
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.QUESTION
    ) {
        composable(route = Routes.QUESTION) {
            val questionViewModel: QuestionViewModel = hiltViewModel()
            val questionScreenUiState by remember { questionViewModel.uiState }
                .collectAsStateWithLifecycle()

            QuestionScreen(
                uiState = questionScreenUiState,
                onAction = questionViewModel::onAction
            )

        }
    }
}