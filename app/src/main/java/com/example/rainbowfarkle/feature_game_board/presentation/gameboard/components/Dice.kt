package com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components

import android.view.animation.Animation
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.rainbowfarkle.R
import com.example.rainbowfarkle.feature_game_board.domain.models.Dice

@Composable
fun RowScope.Dice(
    contentAlignment: Alignment = Alignment.Center,
    dice: Dice,
    rotation: State<Float>,
    onDiceSelected: (Dice) -> Unit
) {
    val diceValues by remember { mutableStateOf(Dice.createDice().values) }
    val first by remember { mutableStateOf(diceValues.filterNot { it.info.value == dice.info.value }.random()) }
    val second by remember { mutableStateOf(diceValues.filterNot { it.info.value == first.info.value }.random()) }

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
            rotation.value > 90f && rotation.value <= 270f -> DicePlaceHolder(
                diceDrawableId = first.info.drawableId
            )
            rotation.value > 270f && rotation.value <= 450f -> DicePlaceHolder(
                diceDrawableId = second.info.drawableId
            )
            rotation.value > 450f -> DiceImage(
                dice = dice,
                onDiceSelected = onDiceSelected
            )
        }
    }
}

@Composable
private fun DiceImage(
    modifier: Modifier = Modifier,
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

enum class CardFace(val angle: Float) {
    Front(0f) {
        override val next: CardFace
            get() = Back
    },
    Back(180f) {
        override val next: CardFace
            get() = Front
    };

    abstract val next: CardFace
}

enum class RotationAxis {
    AxisX,
    AxisY,
}

//@ExperimentalMaterialApi
//@Composable
//fun FlipCard(
//    cardFace: CardFace,
//    onClick: (CardFace) -> Unit,
//    modifier: Modifier = Modifier,
//    axis: RotationAxis = RotationAxis.AxisY,
//    back: @Composable () -> Unit = {},
//    front: @Composable () -> Unit = {},
//) {
//    val rotation = animateFloatAsState(
//        targetValue = cardFace.angle,
//        animationSpec = tween(
//            durationMillis = 400,
//            easing = FastOutSlowInEasing,
//        )
//    )
//    Card(
//        onClick = { onClick(cardFace) },
//        modifier = modifier
//            .graphicsLayer {
//                if (axis == RotationAxis.AxisX) {
//                    rotationX = rotation.value
//                } else {
//                    rotationY = rotation.value
//                }
//                cameraDistance = 12f * density
//            },
//    ) {
//        if (rotation.value <= 90f) {
//            Box(
//                Modifier.fillMaxSize()
//            ) {
//                front()
//            }
//        } else {
//            Box(
//                Modifier
//                    .fillMaxSize()
//                    .graphicsLayer {
//                        if (axis == RotationAxis.AxisX) {
//                            rotationX = 180f
//                        } else {
//                            rotationY = 180f
//                        }
//                    },
//            ) {
//                back()
//            }
//        }
//    }
//}