package com.example.rainbowfarkle.feature_game_board.domain.models

data class GameBoard(
    val pointsToWin: Int,
    val numberOfPlayers: Int,
    val players: List<Player>,
    val dice: List<Dice>,
    val id: String
)
