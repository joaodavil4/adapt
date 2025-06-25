package com.example.adapt.ui.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun JellyButton(
    color: Color,
    title: String,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    var isHovered by remember { mutableStateOf(false) }
    val hoverScale = if (isHovered) 1.2f else scale
    val hoverColor = if (isHovered) color.copy(alpha = 0.8f) else color

    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .graphicsLayer(scaleX = hoverScale, scaleY = hoverScale)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event: PointerEvent = awaitPointerEvent()
                        isHovered = event.changes.any { it.pressed }
                    }
                }
            },
        colors = ButtonDefaults.buttonColors(
            containerColor = hoverColor
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp,
            hoveredElevation = 10.dp
        )
    ) {

    }
}

@Preview
@Composable
fun JellyButtonPreview() {
    JellyButton(
        color = Color.Blue,
        title = "Click Me",
        onClick = { /* Handle click */ }
    )
}