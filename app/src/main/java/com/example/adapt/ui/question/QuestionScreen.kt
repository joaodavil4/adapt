package com.example.adapt.ui.question

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.adapt.ui.composables.CircularLayout
import com.example.adapt.ui.composables.JellyButton
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

    QuestionScreen(
        modifier = Modifier
            .fillMaxSize(),
        uiState = uiState,
        onAction = onAction
    )


}

@Composable
fun QuestionScreen(
    modifier: Modifier = Modifier,
    uiState: QuestionUiState,
    onAction: (QuestionAction) -> Unit,
) {
    when (uiState) {
        is QuestionUiState.Initial -> {
            // Initial state, show the question
            val colors = listOf(
                red to "Red",
                blue to "Blue",
                green to "Green",
                yellow to "Yellow",
                purple to "Purple"
            )
            var selectedColor by remember { mutableStateOf(Color.White) }

            // Create a transition for the background color
            val transition =
                updateTransition(targetState = selectedColor, label = "BackgroundColorTransition")
            val animatedBackgroundColor by transition.animateColor(
                label = "AnimatedBackgroundColor",
                transitionSpec = { tween(durationMillis = 2000) } // Slow animation duration
            ) { it }


            Box(
                modifier = modifier
                    .background(animatedBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                CircularLayout {
                    colors.forEach { (color, name) ->
                        JellyButton(
                            color = color,
                            title = name,
                            onClick = {
                                selectedColor = color
                                onAction(QuestionAction.SaveFavoriteColor(name))
                            }
                        )
                    }
                }
                Text(
                    text = "Which is your favorite color?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
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