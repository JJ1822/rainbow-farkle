package com.example.rainbowfarkle.feature_game_board.presentation.util

object GameRules {
    const val DEFAULT_POINTS_TO_WIN = 5000f
    const val DEFAULT_NUMBER_OF_PLAYERS = 1
    const val MINIMUM_POINTS = 1000
    const val MAXIMUM_POINTS = 20000
    const val MINIMUM_PLAYERS = 1
    const val MAXIMUM_PLAYERS = 8
    const val PLAYER = 1
    const val POINT = 1000
}

enum class GameState {
    ROLLING,
    START_ROUND,
    PLAYING,
    END,
    FARKLE,
    PRE,
    BETWEEN_TURNS
}