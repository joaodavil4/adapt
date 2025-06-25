package com.example.adapt.ui.composables

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier.size(300.dp)
    ) { measurables, constraints ->
        val radius = constraints.maxWidth / 2
        val itemCount = measurables.size
        val angleStep = 2 * PI / itemCount

        val placeables = measurables.map { it.measure(constraints) }

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEachIndexed { index, placeable ->
                val angle = index * angleStep
                val x = (radius + radius * cos(angle) - placeable.width / 2).toInt()
                val y = (radius + radius * sin(angle) - placeable.height / 2).toInt()
                placeable.place(x, y)
            }
        }
    }
}