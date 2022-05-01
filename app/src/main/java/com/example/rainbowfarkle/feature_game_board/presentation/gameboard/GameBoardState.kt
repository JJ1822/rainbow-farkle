package com.example.rainbowfarkle.feature_game_board.presentation.gameboard

import com.example.rainbowfarkle.feature_game_board.domain.models.Player
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameRules

data class GameBoardState(
    val pointsToWin: Int = GameRules.DEFAULT_POINTS_TO_WIN,
    val numberOfPlayers: Int = GameRules.DEFAULT_NUMBER_OF_PLAYERS,
    val players: List<Player> = emptyList()
)