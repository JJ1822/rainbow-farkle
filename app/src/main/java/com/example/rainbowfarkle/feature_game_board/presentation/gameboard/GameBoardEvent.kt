package com.example.rainbowfarkle.feature_game_board.presentation.gameboard

sealed class GameBoardEvent {
    object MinusPoint : GameBoardEvent()
    object AddPoint : GameBoardEvent()
    object MinusPlayer : GameBoardEvent()
    object AddPlayer : GameBoardEvent()
    object Settings : GameBoardEvent()
    object Submit : GameBoardEvent()
    object Reset : GameBoardEvent()
}
