package com.example.adapt.ui.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun QuestionScreen(
    viewModel: QuestionViewModel = hiltViewModel()
) {
    val colors = listOf("Red", "Blue", "Green", "Yellow", "Purple")
    val selectedColor = viewModel.selectedColor.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Which is your favorite color?",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            colors.forEach { color ->
                Button(
                    onClick = { viewModel.setFavoriteColor(color) },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = color)
                }
            }
        }

        selectedColor.value?.let { color ->
            Text(
                text = "Your favorite color is: $color",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun QuestionScreenPreview() {
    QuestionScreen()
}