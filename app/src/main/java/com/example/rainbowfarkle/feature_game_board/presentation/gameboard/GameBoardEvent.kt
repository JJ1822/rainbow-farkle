package com.example.rainbowfarkle.feature_game_board.presentation.gameboard

import com.example.rainbowfarkle.feature_game_board.domain.models.Dice
import com.example.rainbowfarkle.feature_game_board.domain.models.Player

sealed class GameBoardEvent {
    object MinusPoint : GameBoardEvent()
    object AddPoint : GameBoardEvent()
    object MinusPlayer : GameBoardEvent()
    object AddPlayer : GameBoardEvent()
    object Submit : GameBoardEvent()
    object PlayGame : GameBoardEvent()
    object ResetSettings : GameBoardEvent()
    object ResetPlayers : GameBoardEvent()
    object Roll : GameBoardEvent()
    object OnBankClick : GameBoardEvent()
    object BankPoints : GameBoardEvent()
    object Dismiss : GameBoardEvent()
    object RollDice : GameBoardEvent()
    object CheckDice : GameBoardEvent()

    class EndTurn(val player: Player) : GameBoardEvent()
    class OnTextChange(val text: String, val player: Player) : GameBoardEvent()
    class OnDiceSelected(val dice: Dice) : GameBoardEvent()
    class OnSecondaryDiceSelected(val dice: Dice) : GameBoardEvent()
}
