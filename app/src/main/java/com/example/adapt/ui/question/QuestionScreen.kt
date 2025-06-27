package com.example.adapt.ui.question

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.adapt.ui.composables.CircularLayout
import com.example.adapt.ui.composables.ForwardButton
import com.example.adapt.ui.composables.JellyButton
import com.example.adapt.ui.composables.animations.LoadingScreen
import com.example.adapt.ui.composables.animations.ThreeDotsLoading
import com.example.adapt.ui.question.QuestionAction.SaveFavoriteColor
import com.example.adapt.ui.question.QuestionAction.SendPrompt
import com.example.compose.AdaptTheme
import com.example.compose.blue
import com.example.compose.green
import com.example.compose.purple
import com.example.compose.red
import com.example.compose.yellow

@Composable
fun QuestionUI(
    uiState: QuestionUiState,
    onAction: (QuestionAction) -> Unit,
) {

    when (uiState) {
        is QuestionUiState.Initial -> {
            if (uiState.isFullLoading) {
                // Show loading screen if the initial state is loading
                LoadingScreen()
            } else {
                // Show the question screen if not loading
                QuestionScreen(
                    modifier = Modifier.fillMaxSize(),
                    uiState = uiState,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun QuestionScreen(
    modifier: Modifier = Modifier,
    uiState: QuestionUiState.Initial,
    onAction: (QuestionAction) -> Unit,
) {


    // Initial state, show the question
    val colors = listOf(
        red to "Red",
        blue to "Blue",
        green to "Green",
        yellow to "Yellow",
        purple to "Purple"
    )

    var selectedColor by remember {
        mutableStateOf(
            uiState.selectedColor?.let {
                Color(it.toColorInt())
            } ?: Color.White
        )
    }


    // Create a transition for the background color
    val transition =
        updateTransition(targetState = selectedColor, label = "BackgroundColorTransition")
    val animatedBackgroundColor by transition.animateColor(
        label = "AnimatedBackgroundColor",
        transitionSpec = { tween(durationMillis = 2000) } // Slow animation duration
    ) { it }

    LaunchedEffect(selectedColor) {
        // Trigger the action to send the prompt when the color changes
        onAction(SendPrompt(selectedColor.toString()))
    }


    Column(
        modifier = modifier
            .background(animatedBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(3f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularLayout {
                colors.forEach { (color, name) ->
                    JellyButton(
                        color = color,
                        title = name,
                        onClick = {
                            selectedColor = color
                            onAction(SaveFavoriteColor(name))
                        }
                    )
                }


            }
        }





        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Which is your favorite color?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            if (uiState.isLoading) {
                ThreeDotsLoading(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp),
                    dotSize = 23.dp,
                    dotSpacing = 6.dp,
                    animationDelay = 250,
                    dotColor = Color.Black
                )
            } else {
                uiState.feedbackColor?.let {
                    if (it.isNotEmpty()) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.selectedColor != null) {
                ForwardButton(
                    onClick = {
//                                onAction(QuestionAction.Next)
                    },
                    color = selectedColor
                )
            }
        }


    }


}

@Preview(showSystemUi = true)
@Composable
fun QuestionScreenPreview() {
    AdaptTheme {
        QuestionUI(
            uiState = QuestionUiState.Initial(),
            onAction = {}
        )
    }

}

@Preview(showSystemUi = true)
@Composable
fun QuestionLoadingScreenPreview() {
    AdaptTheme {
        QuestionUI(
            uiState = QuestionUiState.Initial(
                isFullLoading = true
            ),
            onAction = {}
        )
    }

}