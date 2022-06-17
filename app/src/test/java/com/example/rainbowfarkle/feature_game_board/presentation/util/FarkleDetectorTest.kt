package com.example.rainbowfarkle.feature_game_board.presentation.util

import com.example.rainbowfarkle.feature_game_board.domain.models.Dice
import com.example.rainbowfarkle.feature_game_board.domain.models.DiceInfo
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.util.Collections.swap


class FarkleDetectorTest {
    private val farkleDetector = FarkleDetector()

    @Test
    fun testSingleDice() {
        singleDiceBuilder().forEach { dice ->
            when {
                dice.contains(ONE) -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(100)
                }
                dice.contains(FIVE) -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(50)
                }
                else -> {
                    assertThat(farkleDetector.isFarkle(dice)).isTrue()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                }
            }
        }
    }

    @Test
    fun testDoubleDice() {
        doubleDiceBuilder().forEach { dice ->
            when {
                dice.all { it.info.value == 1 } -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(200)
                }
                dice.all { it.info.value == 5 } -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(100)
                }
                dice.contains(ONE) -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    if (dice.contains(FIVE)) {
                        assertThat(farkleDetector.getPoints(dice)).isEqualTo(150)
                    } else {
                        assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.contains(FIVE) -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                }
                else -> {
                    assertThat(farkleDetector.isFarkle(dice)).isTrue()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                }
            }
        }
    }

    @Test
    fun testTripleDice() {
        tripleDiceBuilder().forEach { dice ->
            println(dice.toString())
            when {
                dice.all { it.info.value == 1 } -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(750)
                }
                dice.all { it.info.value == 2 } -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(200)
                }
                dice.all { it.info.value == 3 } -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(300)
                }
                dice.all { it.info.value == 4 } -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(400)
                }
                dice.all { it.info.value == 5 } -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(500)
                }
                dice.all { it.info.value == 6 } -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(600)
                }
                dice.filter { it == ONE }.size == 2 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    if (dice.contains(FIVE)) {
                        assertThat(farkleDetector.getPoints(dice)).isEqualTo(250)
                    } else {
                        assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == FIVE }.size == 2 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    if (dice.contains(ONE)) {
                        assertThat(farkleDetector.getPoints(dice)).isEqualTo(200)
                    } else {
                        assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.contains(ONE) || dice.contains(FIVE) -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                }
                else -> {
                    assertThat(farkleDetector.isFarkle(dice)).isTrue()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                }
            }
        }
    }

    @Test
    fun testFourDice() {
        fourDiceBuilder().forEach { dice ->
            println(dice.toString())
            when {
                dice.all { it.info == dice.first().info } -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(1000)
                }
                dice.filter { it == ONE }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    if (dice.contains(FIVE)) {
                        assertThat(farkleDetector.getPoints(dice)).isEqualTo(800)
                    } else {
                        assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == TWO }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.contains(FIVE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            250
                        )
                        dice.contains(ONE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            300
                        )
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == THREE }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.contains(FIVE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            350
                        )
                        dice.contains(ONE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            400
                        )
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == FOUR }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.contains(FIVE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            450
                        )
                        dice.contains(ONE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            500
                        )
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == FIVE }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.contains(ONE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            600
                        )
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == SIX }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.contains(FIVE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            650
                        )
                        dice.contains(ONE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            700
                        )
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == ONE }.size == 2 && dice.filter { it == FIVE }.size == 2 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(300)
                }
                dice.contains(ONE) || dice.contains(FIVE) -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                }
                else -> {
                    assertThat(farkleDetector.isFarkle(dice)).isTrue()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                }
            }
        }
    }

    @Test
    fun testFiveDice() {
       fiveDiceBuilder().forEach { dice ->
            println(dice.toString())
            when {
                dice.all { it.info == dice.first().info } -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(1500)
                }
                dice.filter { it == ONE }.size == 4 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    if (dice.contains(FIVE)) {
                        assertThat(farkleDetector.getPoints(dice)).isEqualTo(1050)
                    } else {
                        assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == TWO }.size == 4 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.contains(FIVE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            1050
                        )
                        dice.contains(ONE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            1100
                        )
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == THREE }.size == 4 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.contains(FIVE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            1050
                        )
                        dice.contains(ONE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            1100
                        )
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == FOUR }.size == 4 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.contains(FIVE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            1050
                        )
                        dice.contains(ONE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            1100
                        )
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == FIVE }.size == 4 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.contains(ONE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            1100
                        )
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == SIX }.size == 4 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.contains(FIVE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            1050
                        )
                        dice.contains(ONE) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(
                            1100
                        )
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == ONE }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    if (dice.filter { it == FIVE }.size == 2) {
                        assertThat(farkleDetector.getPoints(dice)).isEqualTo(850)
                    } else {
                        assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == TWO }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.filter { it == FIVE }.size == 2 -> assertThat(
                            farkleDetector.getPoints(
                                dice
                            )
                        ).isEqualTo(300)
                        dice.filter { it == ONE }.size == 2 -> assertThat(
                            farkleDetector.getPoints(
                                dice
                            )
                        ).isEqualTo(400)
                        dice.containsAll(listOf(ONE, FIVE)) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(350)
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == THREE }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.filter { it == FIVE }.size == 2 -> assertThat(
                            farkleDetector.getPoints(
                                dice
                            )
                        ).isEqualTo(400)
                        dice.filter { it == ONE }.size == 2 -> assertThat(
                            farkleDetector.getPoints(
                                dice
                            )
                        ).isEqualTo(500)
                        dice.containsAll(listOf(ONE, FIVE)) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(450)
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == FOUR }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.filter { it == FIVE }.size == 2 -> assertThat(
                            farkleDetector.getPoints(
                                dice
                            )
                        ).isEqualTo(500)
                        dice.filter { it == ONE }.size == 2 -> assertThat(
                            farkleDetector.getPoints(
                                dice
                            )
                        ).isEqualTo(600)
                        dice.containsAll(listOf(ONE, FIVE)) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(550)
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == FIVE }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.filter { it == ONE }.size == 2 -> assertThat(
                            farkleDetector.getPoints(
                                dice
                            )
                        ).isEqualTo(700)
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == SIX }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.filter { it == FIVE }.size == 2 -> assertThat(
                            farkleDetector.getPoints(
                                dice
                            )
                        ).isEqualTo(700)
                        dice.filter { it == ONE }.size == 2 -> assertThat(
                            farkleDetector.getPoints(
                                dice
                            )
                        ).isEqualTo(800)
                        dice.containsAll(listOf(ONE, FIVE)) -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(750)
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.contains(ONE) || dice.contains(FIVE) -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                }
                else -> {
                    assertThat(farkleDetector.isFarkle(dice)).isTrue()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                }
            }
        }
    }

    @Test
    fun testSixDice() {
        sixDiceBuilder().forEach { dice ->
            val sortedDice = dice.sortedBy { it.info.value }
            val first = sortedDice.slice(IntRange(0, 1))
            val second = sortedDice.slice(IntRange(2, 3))
            val third = sortedDice.slice(IntRange(4, 5))
            when {
                dice.all { it.info == dice.first().info } -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(2000)
                }
                HashSet(dice).size == 6 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(2500)
                }
                dice.filter { it == ONE }.size == 5 ||
                        dice.filter { it == TWO }.size == 5 ||
                        dice.filter { it == THREE }.size == 5 ||
                        dice.filter { it == FOUR }.size == 5 ||
                        dice.filter { it == FIVE }.size == 5 ||
                        dice.filter { it == SIX }.size == 5 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.filter { it == FIVE }.size == 1 -> assertThat(
                            farkleDetector.getPoints(
                                dice
                            )
                        ).isEqualTo(1550)
                        dice.filter { it == ONE }.size == 1 -> assertThat(
                            farkleDetector.getPoints(
                                dice
                            )
                        ).isEqualTo(1600)
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == ONE }.size == 4 ||
                        dice.filter { it == TWO }.size == 4 ||
                        dice.filter { it == THREE }.size == 4 ||
                        dice.filter { it == FOUR }.size == 4 ||
                        dice.filter { it == FIVE }.size == 4 ||
                        dice.filter { it == SIX }.size == 4 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.filter { it == ONE }.size == 2 -> assertThat(
                            farkleDetector.getPoints(
                                dice
                            )
                        ).isEqualTo(1200)
                        dice.filter { it == FIVE }.size == 2 -> assertThat(
                            farkleDetector.getPoints(
                                dice
                            )
                        ).isEqualTo(1100)
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == ONE }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.filter { it == TWO }.size == 3 ||
                                dice.filter { it == THREE }.size == 3 ||
                                dice.filter { it == FOUR }.size == 3 ||
                                dice.filter { it == FIVE }.size == 3 ||
                                dice.filter { it == SIX }.size == 3 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(2500)
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == TWO }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.filter { it == THREE }.size == 3 ||
                                dice.filter { it == FOUR }.size == 3 ||
                                dice.filter { it == FIVE }.size == 3 ||
                                dice.filter { it == SIX }.size == 3 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(2500)
                        dice.filter { it == ONE }.size == 2 &&
                                dice.filter { it == FIVE }.size == 1 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(450)
                        dice.filter { it == ONE }.size == 1 &&
                                dice.filter { it == FIVE }.size == 2 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(400)
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == THREE }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.filter { it == FOUR }.size == 3 ||
                                dice.filter { it == FIVE }.size == 3 ||
                                dice.filter { it == SIX }.size == 3 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(2500)
                        dice.filter { it == ONE }.size == 2 &&
                                dice.filter { it == FIVE }.size == 1 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(550)
                        dice.filter { it == ONE }.size == 1 &&
                                dice.filter { it == FIVE }.size == 2 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(500)
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == FOUR }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.filter { it == FIVE }.size == 3 ||
                                dice.filter { it == SIX }.size == 3 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(2500)
                        dice.filter { it == ONE }.size == 2 &&
                                dice.filter { it == FIVE }.size == 1 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(650)
                        dice.filter { it == ONE }.size == 1 &&
                                dice.filter { it == FIVE }.size == 2 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(600)
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == FIVE }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.filter { it == SIX }.size == 3 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(2500)
                        dice.filter { it == ONE }.size == 2 &&
                                dice.filter { it == FIVE }.size == 1 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(750)
                        dice.filter { it == ONE }.size == 1 &&
                                dice.filter { it == FIVE }.size == 2 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(700)
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                dice.filter { it == SIX }.size == 3 -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    when {
                        dice.filter { it == ONE }.size == 2 &&
                                dice.filter { it == FIVE }.size == 1 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(850)
                        dice.filter { it == ONE }.size == 1 &&
                                dice.filter { it == FIVE }.size == 2 ->
                            assertThat(farkleDetector.getPoints(dice)).isEqualTo(800)
                        else -> assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                    }
                }
                first.all { it == first.first() } &&
                        second.all { it == second.first() } &&
                        third.all { it == third.first() } -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(2500)
                }

                dice.contains(ONE) || dice.contains(FIVE) -> {
                    assertThat(farkleDetector.isFarkle(dice)).isFalse()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                }
                else -> {
                    assertThat(farkleDetector.isFarkle(dice)).isTrue()
                    assertThat(farkleDetector.getPoints(dice)).isEqualTo(null)
                }
            }
        }
    }

    companion object {
        private val orderedDice = DiceInfo.values().apply {
            sortBy { it.value }
        }

        fun singleDiceBuilder(): List<List<Dice>> {
            val dice = mutableListOf<List<Dice>>()
            orderedDice.forEach {
                dice.add(listOf(buildDice(it)))
            }
            return dice
        }

        fun doubleDiceBuilder(): List<List<Dice>> {
            val dice = mutableListOf<List<Dice>>()
            orderedDice.forEach { first ->
                orderedDice.forEach { second ->
                    dice.add(listOf(buildDice(first), buildDice(second)))
                }
            }
            return dice
        }

        fun tripleDiceBuilder(): List<List<Dice>> {
            val dice = mutableListOf<List<Dice>>()
            orderedDice.forEach { first ->
                orderedDice.forEach { second ->
                    orderedDice.forEach { third ->
                        dice.add(listOf(buildDice(first), buildDice(second), buildDice(third)))
                    }
                }
            }
            return dice
        }

        fun fourDiceBuilder(): List<List<Dice>> {
            val dice = mutableListOf<List<Dice>>()
            orderedDice.forEach { first ->
                orderedDice.forEach { second ->
                    orderedDice.forEach { third ->
                        orderedDice.forEach { fourth ->
                            dice.add(
                                listOf(
                                    buildDice(first),
                                    buildDice(second),
                                    buildDice(third),
                                    buildDice(fourth)
                                )
                            )
                        }
                    }
                }
            }
            return dice
        }

        fun fiveDiceBuilder(): List<List<Dice>> {
            val dice = mutableListOf<List<Dice>>()
            orderedDice.forEach { first ->
                orderedDice.forEach { second ->
                    orderedDice.forEach { third ->
                        orderedDice.forEach { fourth ->
                            orderedDice.forEach { five ->
                                dice.add(
                                    listOf(
                                        buildDice(first),
                                        buildDice(second),
                                        buildDice(third),
                                        buildDice(fourth),
                                        buildDice(five)
                                    )
                                )
                            }
                        }
                    }
                }
            }
            return dice
        }

        fun sixDiceBuilder(): List<List<Dice>> {
            val dice = mutableListOf<List<Dice>>()
            orderedDice.forEach { first ->
                orderedDice.forEach { second ->
                    orderedDice.forEach { third ->
                        orderedDice.forEach { fourth ->
                            orderedDice.forEach { five ->
                                orderedDice.forEach { six ->
                                    dice.add(
                                        listOf(
                                            buildDice(first),
                                            buildDice(second),
                                            buildDice(third),
                                            buildDice(fourth),
                                            buildDice(five),
                                            buildDice(six)
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
            return dice
        }


        fun buildDice(diceInfo: DiceInfo) = Dice(
            info = diceInfo,
            position = 0,
            isSelected = false,
            locked = false
        )

        private val ONE = Dice(
            info = DiceInfo.ONE,
            position = 0,
            isSelected = false,
            locked = false
        )
        private val TWO = Dice(
            info = DiceInfo.TWO,
            position = 0,
            isSelected = false,
            locked = false
        )
        private val THREE = Dice(
            info = DiceInfo.THREE,
            position = 0,
            isSelected = false,
            locked = false
        )
        private val FOUR = Dice(
            info = DiceInfo.FOUR,
            position = 0,
            isSelected = false,
            locked = false
        )
        private val FIVE = Dice(
            info = DiceInfo.FIVE,
            position = 0,
            isSelected = false,
            locked = false
        )
        private val SIX = Dice(
            info = DiceInfo.SIX,
            position = 0,
            isSelected = false,
            locked = false
        )
    }
}