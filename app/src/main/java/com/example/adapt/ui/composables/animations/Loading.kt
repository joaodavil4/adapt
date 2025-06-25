package com.example.adapt.ui.composables.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ThreeDotsLoading(
    modifier: Modifier = Modifier,
    dotSize: Dp = 16.dp,
    dotSpacing: Dp = 8.dp,
    animationDelay: Int = 300,
    animationDuration: Int = 600,
    dotColor: Color = Color.Gray
) {
    // Single Animatable scale value per dot
    @Composable
    fun PulsingDot(delayMillis: Int) {
        val scale = remember { Animatable(0f) }

        LaunchedEffect(Unit) {
            delay(delayMillis.toLong())
            scale.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(animationDuration, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }

        Box(
            modifier = modifier
                .size(dotSize)
                .graphicsLayer { scaleX = scale.value; scaleY = scale.value }
                .background(color = dotColor, shape = CircleShape)
        )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(dotSpacing)) {
        // Offset pulses: 0ms, 300ms, 600ms
        PulsingDot(delayMillis = 0)
        PulsingDot(delayMillis = animationDelay)
        PulsingDot(delayMillis = animationDelay * 2)
    }
}

@Preview
@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        ThreeDotsLoading(
            dotSize = 12.dp,
            dotSpacing = 6.dp,
            animationDelay = 250,
            dotColor = Color.Black
        )
    }
}