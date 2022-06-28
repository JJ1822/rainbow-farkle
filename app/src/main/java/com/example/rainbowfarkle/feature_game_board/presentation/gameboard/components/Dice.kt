package com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.rainbowfarkle.R
import com.example.rainbowfarkle.feature_game_board.domain.models.Dice
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RowScope.Dice(
    contentAlignment: Alignment = Alignment.Center,
    size: Int,
    dice: Dice,
    rotation: State<Float>,
    offset: State<Float>,
    placeholderDice: Dice,
    onDiceSelected: (Dice) -> Unit
) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f)
                .fillMaxHeight()
                .graphicsLayer {
                    when {
                        rotation.value <= 180f -> rotationX = rotation.value
                        rotation.value > 180f && rotation.value <= 360f -> rotationY = rotation.value
                        rotation.value > 360f -> rotationX = rotation.value
//                    offset.value > 0f ->
//                        translationX = if (index.isOdd()) -offset.value else offset.value

                    }
                    cameraDistance = 12f * density
                },
            contentAlignment = contentAlignment
        ) {
            when {
                rotation.value <= 90f -> DiceImage(
                    dice = dice,
                    onDiceSelected = onDiceSelected
                )
                rotation.value > 90f -> DicePlaceHolder(
                    diceDrawableId = placeholderDice.info.drawableId
                )
            }
        }
    }
//    Box(
//        modifier = Modifier
//            .padding(16.dp)
//            .weight(1f)
//            .fillMaxHeight()
//            .graphicsLayer {
//                when {
//                    rotation.value <= 180f -> rotationX = rotation.value
//                    rotation.value > 180f && rotation.value <= 360f -> rotationY = rotation.value
//                    rotation.value > 360f -> rotationX = rotation.value
////                    offset.value > 0f ->
////                        translationX = if (index.isOdd()) -offset.value else offset.value
//
//                }
//                cameraDistance = 12f * density
//            },
//        contentAlignment = contentAlignment
//    ) {
//        when {
//            rotation.value <= 90f -> DiceImage(
//                dice = dice,
//                onDiceSelected = onDiceSelected
//            )
//            rotation.value > 90f -> DicePlaceHolder(
//                diceDrawableId = placeholderDice.info.drawableId
//            )
//        }
//    }

@Composable
private fun DiceImage(
    dice: Dice,
    onDiceSelected: (Dice) -> Unit
) {
    Image(
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(18.dp))
            .clickable { onDiceSelected(dice) },
        contentScale = ContentScale.FillBounds,
        painter = painterResource(id = if (dice.isSelected) R.drawable.test else dice.info.drawableId),
        contentDescription = null
    )
}

@Composable
private fun DicePlaceHolder(
    @DrawableRes diceDrawableId: Int
) {
    Image(
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(18.dp)),
        contentScale = ContentScale.FillBounds,
        painter = painterResource(id = diceDrawableId),
        contentDescription = null
    )
}

fun Int.isOdd() = this % 2 == 0