package com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components

import android.widget.ProgressBar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.rainbowfarkle.ui.theme.Shapes

@Composable
fun RainbowProgressBar(
    progress: Float
) {
    val gradient =
        Brush.horizontalGradient(listOf(Color(0xFFF6C143), Color(0xFFEC6655), Color(0xFFEB4277), Color(0xFFAB68CE), Color(0xFF6C96F7), Color(0xFFE65DDD1)))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .background(Color.White),



    ) {
        Box(modifier = Modifier
            .height(24.dp)
            .fillMaxWidth(progress)
            .clip(RoundedCornerShape(8.dp))
            .background(gradient)
        ) {}
    }
}