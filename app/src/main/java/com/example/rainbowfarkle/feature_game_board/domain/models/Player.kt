package com.example.rainbowfarkle.feature_game_board.domain.models

data class Player(
    val position: Int = 0,
    val name: String = "",
    val label: String = "",
    val place: Int = 0,
    val points: Float = 0f,
    val isWinner: Boolean = false
)
