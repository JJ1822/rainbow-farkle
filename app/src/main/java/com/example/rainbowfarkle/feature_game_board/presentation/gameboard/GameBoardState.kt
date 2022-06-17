package com.example.rainbowfarkle.feature_game_board.presentation.gameboard

import com.example.rainbowfarkle.feature_game_board.domain.models.Dice
import com.example.rainbowfarkle.feature_game_board.domain.models.Player
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameRules
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameState

data class GameBoardState(
    val pointsToWin: Float = GameRules.DEFAULT_POINTS_TO_WIN,
    val numberOfPlayers: Int = GameRules.DEFAULT_NUMBER_OF_PLAYERS,
    val players: List<Player> = listOf(Player()),
    val gameState: GameState = GameState.PRE,
    val bottomSheetEnum: BottomSheetEnum = BottomSheetEnum.PLAYERS_POINTS,
    val dice: Map<Int, Dice> = Dice.createDice(),
    val secondaryDice: List<Dice> = emptyList(),
    val hasValidPoints: Boolean = false,
    val currentPlayerPosition: Int = 0,
    val bankPoints: Int = 0,
    val stashedBankPoints: Int = 0,
    val updateDice: Boolean = false
)