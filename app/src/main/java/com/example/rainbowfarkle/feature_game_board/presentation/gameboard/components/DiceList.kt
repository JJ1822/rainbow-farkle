package com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.example.rainbowfarkle.feature_game_board.domain.models.Dice
import com.example.rainbowfarkle.feature_game_board.domain.models.filterNot
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameState

internal const val ROTATION_VALUE = 270f
internal const val OFFSET_VALUE = 800f

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ColumnScope.DiceList(
    diceMap: Map<Int, Dice>,
    gameState: GameState,
    onDiceSelected: (Dice) -> Unit,
    rollDice: () -> Unit,
    checkDice: () -> Unit,
    bankPoints: () -> Unit
) {
    val listOfDice = when (diceMap.values.size) {
        1, 3 -> diceMap.values.chunked(1)
        else -> diceMap.values.chunked(2)
    }
    val diceValues by remember { mutableStateOf(Dice.createDice().values) }
    var offsetValue by remember { mutableStateOf(0f) }
    var rotationValue by remember { mutableStateOf(0f) }
    val rotation = animateFloatAsState(
        targetValue = rotationValue,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing,
        ),
        finishedListener = {
            if (it == ROTATION_VALUE) rollDice() else checkDice()
            rotationValue = 0f
        }
    )

    val offset = animateFloatAsState(
        targetValue = offsetValue,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing,
        ),
        finishedListener = {
            if (it == OFFSET_VALUE) bankPoints() else rollDice()
            offsetValue = 0f
        }
    )


    LaunchedEffect(gameState) {
        when (gameState) {
            GameState.ROLLING -> rotationValue = ROTATION_VALUE
            GameState.BETWEEN_TURNS -> offsetValue = OFFSET_VALUE
            else -> {}
        }
    }

    AnimatedContent(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 16.dp),
        targetState = diceMap,
        transitionSpec = {
            slideInHorizontally(animationSpec = tween(200, 1000)) { fullWidth ->
                fullWidth
            } with
                    slideOutHorizontally(
                        animationSpec = tween(
                            200,
                            1000
                        )
                    ) { fullWidth ->
                        fullWidth
                    }
        }
    ) { list ->
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            list.values.chunked(2).forEach { subDice ->
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    subDice.forEachIndexed { index, dice ->
                        Dice(
                            size = diceMap.size,
                            dice = dice,
                            rotation = rotation,
                            offset = offset,
                            placeholderDice = diceValues.filterNot(dice).random(),
                            onDiceSelected = onDiceSelected
                        )
                    }
                }
            }
        }
    }

//    Column(
//        modifier = Modifier
//            .weight(1f)
//            .padding(horizontal = 16.dp)
//    ) {
//        listOfDice.forEach { subDice ->
//            Row(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//            ) {
//                subDice.forEachIndexed { index, dice ->
//                    Dice(
//                        size = diceMap.size,
//                        dice = dice,
//                        rotation = rotation,
//                        offset = offset,
//                        placeholderDice = diceValues.filterNot(dice).random(),
//                        onDiceSelected = onDiceSelected
//                    )
//                }
//            }
//        }
//    }
}

fun AnimatedContent(modifier: Modifier, targetState: List<List<Dice>>, transitionSpec: AnimatedContentScope<List<List<Dice>>>.() -> Unit, content: AnimatedVisibilityScope.(targetState: List<List<Dice>>) -> Unit) {

}
