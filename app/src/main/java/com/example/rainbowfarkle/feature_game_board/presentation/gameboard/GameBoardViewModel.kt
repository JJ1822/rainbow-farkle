package com.example.rainbowfarkle.feature_game_board.presentation.gameboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.rainbowfarkle.feature_game_board.domain.models.Player
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameRules
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameRules.PLAYER
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameRules.POINT
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameBoardViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(GameBoardState())
    val state get() = _state.value

    fun onEvent(event: GameBoardEvent) {
        when (event) {
            is GameBoardEvent.MinusPlayer -> minusPlayer()
            is GameBoardEvent.AddPlayer -> addPlayer()
            is GameBoardEvent.MinusPoint -> minusPoints()
            is GameBoardEvent.AddPoint -> addPoints()
            is GameBoardEvent.Settings -> openSettings()
            is GameBoardEvent.Submit -> onSubmit()
            is GameBoardEvent.Reset -> resetPage()
        }
    }

    private fun minusPlayer() {
        if (state.numberOfPlayers > GameRules.MINIMUM_PLAYERS) {
            updateState { copy(numberOfPlayers = state.numberOfPlayers - PLAYER) }
        }
    }

    private fun addPlayer() {
        if (state.numberOfPlayers < GameRules.MAXIMUM_PLAYERS) {
            updateState { copy(numberOfPlayers = state.numberOfPlayers + PLAYER) }
        }
    }

    private fun minusPoints() {
        if (state.pointsToWin > GameRules.MINIMUM_POINTS) {
            updateState { copy(pointsToWin = state.pointsToWin - POINT) }
        }
    }

    private fun addPoints() {
        if (state.pointsToWin < GameRules.MAXIMUM_POINTS) {
            updateState { copy(pointsToWin = state.pointsToWin + POINT) }
        }
    }

    private fun openSettings() {

    }

    private fun onSubmit() {
        val players = mutableListOf<Player>()
        repeat(state.numberOfPlayers) {
            val number = it + 1
            players.add(Player("Player $number", 0))
        }
        updateState { copy(players = players) }
    }

    private fun resetPage() {
        updateState {
            copy(
                pointsToWin = GameRules.DEFAULT_POINTS_TO_WIN,
                numberOfPlayers = GameRules.DEFAULT_NUMBER_OF_PLAYERS
            )
        }
    }

    private fun updateState(state: GameBoardState.() -> GameBoardState) {
        val newState = state(_state.value)
        _state.value = newState
    }
}