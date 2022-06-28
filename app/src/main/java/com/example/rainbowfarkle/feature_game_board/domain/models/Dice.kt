package com.example.rainbowfarkle.feature_game_board.domain.models

import androidx.annotation.DrawableRes
import com.example.rainbowfarkle.R

data class Dice(
    val position: Int,
    val info: DiceInfo,
    val isSelected: Boolean,
    val locked: Boolean,
    val animationState: AnimationState = AnimationState.NONE
) {
    companion object {
        fun createDice(): Map<Int, Dice> = DiceInfo.values().mapIndexed { index, diceInfo ->
            index to Dice(
                position = index,
                info = diceInfo,
                isSelected = false,
                locked = false
            )
        }.toMap()
    }
}

enum class DiceInfo(
    @DrawableRes val drawableId: Int,
    val value: Int
) {
    ONE(R.drawable.dice_one, 1),
    FOUR(R.drawable.dice_four, 4),
    TWO(R.drawable.dice_two, 2),
    FIVE(R.drawable.dice_five, 5),
    THREE(R.drawable.dice_three, 3),
    SIX(R.drawable.dice_six, 6),
}

enum class AnimationState {
    NONE,
    ANIMATE
}

fun Iterable<Dice>.filterNot(dice: Dice) = filterNot { it.info.value == dice.info.value }