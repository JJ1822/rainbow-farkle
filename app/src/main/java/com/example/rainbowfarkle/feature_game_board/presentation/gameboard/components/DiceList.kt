package com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rainbowfarkle.feature_game_board.domain.models.Dice
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameState

private const val MID_ANIMATION = 270f

@Composable
fun ColumnScope.DiceList(
    dice: Map<Int, Dice>,
    gameState: GameState,
    onDiceSelected: (Dice) -> Unit,
    updateDiceRoll: () -> Unit
) {
    val listOfDice = when (dice.values.size) {
        1, 3 -> dice.values.chunked(1)
        else -> dice.values.chunked(2)
    }
    var animationValue by remember { mutableStateOf(0f) }
    var updateDice by remember { mutableStateOf(false) }
    val rotation = animateFloatAsState(
        targetValue = animationValue,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing,
        ),
    )

    LaunchedEffect(gameState) {
        if (gameState == GameState.ROLLING) {
            animationValue = if (animationValue == 540f) { 0f } else 540f
        }
    }

    if (rotation.value > 180f && rotation.value < 360f) {
        updateDice = true
    }

    LaunchedEffect(updateDice) {
        if (updateDice) {
            updateDiceRoll()
        }
    }

    Column(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 16.dp)
    ) {
        listOfDice.forEach { subDice ->
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                subDice.forEach {
                    Dice(
                        dice = it,
                        rotation = rotation,
                        onDiceSelected = onDiceSelected
                    )
                }
            }
        }
    }
}

//@Composable
//private fun RowScope.DiceImage(
//    contentAlignment: Alignment = Alignment.Center,
//    dice: Dice,
//    onDiceSelected: (Dice) -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .padding(16.dp)
//            .weight(1f)
//            .fillMaxHeight(),
//        contentAlignment = contentAlignment
//    ) {
//        Image(
//            modifier = Modifier
//                .fillMaxHeight()
//                .aspectRatio(1f)
//                .clip(RoundedCornerShape(18.dp))
//                .clickable { onDiceSelected(dice) },
//            contentScale = ContentScale.FillBounds,
//            painter = painterResource(id = if (dice.isSelected) R.drawable.test else dice.info.drawableId),
//            contentDescription = null
//        )
//    }
//}