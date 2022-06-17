package com.example.rainbowfarkle.feature_game_board.presentation.util

import com.example.rainbowfarkle.feature_game_board.domain.models.Dice
import javax.inject.Inject

class FarkleDetector @Inject constructor() {

    fun isFarkle(dice: Collection<Dice>): Boolean {
        dice.forEach {
            if (it.info.value == ONE || it.info.value == FIVE) {
                return false
            }
        }
        when (dice.filter { it.info.value == ONE }.size) {
            3, 4, 5, 6 -> return false
        }
        when (dice.filter { it.info.value == TWO }.size) {
            3, 4, 5, 6 -> return false
        }
        when (dice.filter { it.info.value == THREE }.size) {
            3, 4, 5, 6 -> return false
        }
        when (dice.filter { it.info.value == FOUR }.size) {
            3, 4, 5, 6 -> return false
        }
        when (dice.filter { it.info.value == FIVE }.size) {
            3, 4, 5, 6 -> return false
        }
        when (dice.filter { it.info.value == SIX }.size) {
            3, 4, 5, 6 -> return false
        }
        return getPoints(dice.toList()) == null
    }

    fun getPoints(dice: List<Dice>): Int? {
        val sortedDice = dice.sortedBy { it.info.value }
        var points: Int? = null
        if (dice.isEmpty()) return points
        points = when (dice.size) {
            1 -> singleDicePoints(sortedDice.first())
            2 -> doubleDicePoints(sortedDice)
            3 -> tripleDicePoints(sortedDice)
            4 -> quadDicePoints(sortedDice)
            5 -> fiveDicePoints(sortedDice)
            6 -> sixDicePoints(sortedDice)
            else -> null
        }

        return points
    }

    private fun singleDicePoints(dice: Dice?): Int? {
        return dice?.let {
            when (dice.info.value) {
                FIVE -> FIFTY_POINTS
                ONE -> ONE_HUNDRED_POINTS
                else -> null
            }
        }
    }

    private fun doubleDicePoints(dice: List<Dice>): Int? {
        var points = 0
        dice.forEach {
            points += when (it.info.value) {
                FIVE -> FIFTY_POINTS
                ONE -> ONE_HUNDRED_POINTS
                else -> return null
            }
        }
        return points
    }

    private fun tripleDicePoints(dice: List<Dice>): Int? {
        val value = dice.firstOrNull()?.info?.value ?: return null
        if (dice.all { it.info.value == ONE }) return SEVEN_HUNDRED_FIFTY_POINTS
        if (dice.all { it.info.value == value }) return value * ONE_HUNDRED_POINTS

        var points = 0
        dice.forEach {
            points += when (it.info.value) {
                FIVE -> FIFTY_POINTS
                ONE -> ONE_HUNDRED_POINTS
                else -> return null
            }
        }
        return points
    }

    private fun quadDicePoints(dice: List<Dice>): Int? {
        val value = dice.firstOrNull()?.info?.value ?: return null
        if (dice.all { it.info.value == value }) return ONE_THOUSAND_POINTS
        when {
            dice.filter { it.info.value == FIVE }.size == 3 &&
                    dice.filter { it.info.value == ONE }.size == 1 -> {
                return hundredPoints(6)
            }
        }
        tripleDicePoints(dice.slice(IntRange(0, dice.lastIndex - 1)))?.let {
            val singlePoints = singleDicePoints(dice.last()) ?: return null
            return it + singlePoints
        }
        tripleDicePoints(dice.slice(IntRange(1, dice.lastIndex)))?.let {
            val singlePoints = singleDicePoints(dice.first()) ?: return null
            return it + singlePoints
        }
        return null
    }

    private fun fiveDicePoints(dice: List<Dice>): Int? {
        val value = dice.firstOrNull()?.info?.value ?: return null
        if (dice.all { it.info.value == value }) return FIFTEEN_HUNDRED_POINTS
        when {
            dice.filter { it.info.value == ONE }.size == 4 ||
                    dice.filter { it.info.value == TWO }.size == 4 ||
                    dice.filter { it.info.value == THREE }.size == 4 ||
                    dice.filter { it.info.value == FOUR }.size == 4 ||
                    dice.filter { it.info.value == FIVE }.size == 4 ||
                    dice.filter { it.info.value == SIX }.size == 4 -> {
                if (dice.filter { it.info.value == FIVE }.size == 1) {
                    return 1050
                } else if (dice.filter { it.info.value == ONE }.size == 1) {
                    return 1100
                }
                return null
            }
            dice.filter { it.info.value == ONE }.size == 3 -> {
                if (dice.filter { it.info.value == FIVE }.size == 2) {
                    return 850
                }
                return null
            }
            dice.filter { it.info.value == TWO }.size == 3 -> {
                return when {
                    dice.filter { it.info.value == ONE }.size == 2 -> 400
                    dice.filter { it.info.value == FIVE }.size == 2 -> 300
                    dice.filter { it.info.value == ONE }.size == 1 &&
                            dice.filter { it.info.value == FIVE }.size == 1 -> 350
                    else -> null
                }
            }
            dice.filter { it.info.value == THREE }.size == 3 -> {
                return when {
                    dice.filter { it.info.value == ONE }.size == 2 -> 500
                    dice.filter { it.info.value == FIVE }.size == 2 -> 400
                    dice.filter { it.info.value == ONE }.size == 1 &&
                            dice.filter { it.info.value == FIVE }.size == 1 -> 450
                    else -> null
                }
            }
            dice.filter { it.info.value == FOUR }.size == 3 -> {
                return when {
                    dice.filter { it.info.value == ONE }.size == 2 -> 600
                    dice.filter { it.info.value == FIVE }.size == 2 -> 500
                    dice.filter { it.info.value == ONE }.size == 1 &&
                            dice.filter { it.info.value == FIVE }.size == 1 -> 550
                    else -> null
                }
            }
            dice.filter { it.info.value == FIVE }.size == 3 -> {
                return when (dice.filter { it.info.value == ONE }.size) {
                    2 -> 700
                    else -> null
                }
            }
            dice.filter { it.info.value == SIX }.size == 3 -> {
                return when {
                    dice.filter { it.info.value == ONE }.size == 2 -> 800
                    dice.filter { it.info.value == FIVE }.size == 2 -> 700
                    dice.filter { it.info.value == ONE }.size == 1 &&
                            dice.filter { it.info.value == FIVE }.size == 1 -> 750
                    else -> null
                }
            }
        }
        quadDicePoints(dice.slice(IntRange(0, dice.lastIndex - 1)))?.let {
            val singlePoints = singleDicePoints(dice.last()) ?: return null
            return it + singlePoints
        }
        quadDicePoints(dice.slice(IntRange(1, dice.lastIndex)))?.let {
            val singlePoints = singleDicePoints(dice.first()) ?: return null
            return it + singlePoints
        }
        return null
    }

    private fun sixDicePoints(dice: List<Dice>): Int? {
        val value = dice.firstOrNull()?.info?.value ?: return null
        if (dice.all { it.info.value == value }) return TWO_THOUSAND_POINTS
        if (dice.isStraight()) return TWENTY_FIVE_HUNDRED_POINTS
        if (dice.isTripleDouble()) return TWENTY_FIVE_HUNDRED_POINTS
        if (dice.isDoubleTriple()) return TWENTY_FIVE_HUNDRED_POINTS
        when {
            dice.filter { it.info.value == ONE }.size == 5 ||
                    dice.filter { it.info.value == TWO }.size == 5 ||
                    dice.filter { it.info.value == THREE }.size == 5 ||
                    dice.filter { it.info.value == FOUR }.size == 5 ||
                    dice.filter { it.info.value == FIVE }.size == 5 ||
                    dice.filter { it.info.value == SIX }.size == 5 -> {
                if (dice.filter { it.info.value == FIVE }.size == 1) {
                    return 1550
                } else if (dice.filter { it.info.value == ONE }.size == 1) {
                    return 1600
                }
                return null
            }
        }
        when {
            dice.filter { it.info.value == ONE }.size == 4 ||
                    dice.filter { it.info.value == TWO }.size == 4 ||
                    dice.filter { it.info.value == THREE }.size == 4 ||
                    dice.filter { it.info.value == FOUR }.size == 4 ||
                    dice.filter { it.info.value == FIVE }.size == 4 ||
                    dice.filter { it.info.value == SIX }.size == 4 -> {
                if (dice.filter { it.info.value == FIVE }.size == 2) {
                    return 1100
                } else if (dice.filter { it.info.value == ONE }.size == 2) {
                    return 1200
                }
                return null
            }
        }

        fiveDicePoints(dice.slice(IntRange(0, dice.lastIndex - 1)))?.let {
            val singlePoints = singleDicePoints(dice.last()) ?: return null
            return it + singlePoints
        }
        fiveDicePoints(dice.slice(IntRange(1, dice.lastIndex)))?.let {
            val singlePoints = singleDicePoints(dice.first()) ?: return null
            return it + singlePoints
        }

        quadDicePoints(dice.slice(IntRange(0, dice.lastIndex - 2)))?.let {
            val points = doubleDicePoints(dice.slice(IntRange(dice.lastIndex - 1, dice.lastIndex))) ?: return null
            return it + points
        }
        quadDicePoints(dice.slice(IntRange(1, dice.lastIndex - 1)))?.let {
            val firstPoints = singleDicePoints(dice.first()) ?: return null
            val lastPoints = singleDicePoints(dice.last()) ?: return null
            return it + firstPoints + lastPoints
        }
        quadDicePoints(dice.slice(IntRange(2, dice.lastIndex)))?.let {
            val points = doubleDicePoints(dice.slice(IntRange(0, 1))) ?: return null
            return it + points
        }
        return null
    }

    private fun List<Dice>.isStraight(): Boolean {
        forEachIndexed { index, dice ->
            if (index + 1 != dice.info.value) {
                return false
            }
        }
        return true
    }

    private fun List<Dice>.isTripleDouble(): Boolean {
        val first = slice(IntRange(0, 2))
        val second = slice(IntRange(3, 5))
        return first.all { it.info.value == first.first().info.value } &&
                second.all { it.info.value == second.first().info.value }
    }

    private fun List<Dice>.isDoubleTriple(): Boolean {
        val first = slice(IntRange(0, 1))
        val second = slice(IntRange(2, 3))
        val third = slice(IntRange(4, 5))

        return first.all { it.info.value == first.first().info.value } &&
                second.all { it.info.value == second.first().info.value } &&
                third.all { it.info.value == third.first().info.value } &&
                HashSet(this).size == 3
    }

    companion object {
        fun hundredPoints(num: Int) = ONE_HUNDRED_POINTS * num
        private const val ONE = 1
        private const val TWO = 2
        private const val THREE = 3
        private const val FOUR = 4
        private const val FIVE = 5
        private const val SIX = 6
        private const val FIFTY_POINTS = 50
        private const val ONE_HUNDRED_POINTS = 100
        private const val SEVEN_HUNDRED_FIFTY_POINTS = 750
        private const val ONE_THOUSAND_POINTS = 1000
        private const val FIFTEEN_HUNDRED_POINTS = 1500
        private const val TWO_THOUSAND_POINTS = 2000
        private const val TWENTY_FIVE_HUNDRED_POINTS = 2500
    }
}