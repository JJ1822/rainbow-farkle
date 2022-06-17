package com.example.rainbowfarkle.feature_game_board.presentation.gameboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.rainbowfarkle.feature_game_board.domain.models.Dice
import com.example.rainbowfarkle.feature_game_board.domain.models.Dice.Companion.createDice
import com.example.rainbowfarkle.feature_game_board.domain.models.DiceInfo
import com.example.rainbowfarkle.feature_game_board.domain.models.Player
import com.example.rainbowfarkle.feature_game_board.presentation.util.FarkleDetector
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameRules
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameRules.PLAYER
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameRules.POINT
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameBoardViewModel @Inject constructor(
    private val farkleDetector: FarkleDetector
) : ViewModel() {
    private val _state = mutableStateOf(GameBoardState())
    val state get() = _state.value

    fun onEvent(event: GameBoardEvent) {
        when (event) {
            GameBoardEvent.MinusPlayer -> minusPlayer()
            GameBoardEvent.AddPlayer -> addPlayer()
            GameBoardEvent.MinusPoint -> minusPoints()
            GameBoardEvent.AddPoint -> addPoints()
            GameBoardEvent.Submit -> onSubmit()
            GameBoardEvent.PlayGame -> onPlayGame()
            GameBoardEvent.ResetSettings -> resetSettings()
            GameBoardEvent.ResetPlayers -> resetPlayerNames()
            GameBoardEvent.Roll -> roll()
            GameBoardEvent.BankPoints -> bankPoints()
            GameBoardEvent.Dismiss -> dismiss()
            GameBoardEvent.UpdateRollDice -> updateRolledDice()

            is GameBoardEvent.EndTurn -> endTurn(event.player)
            is GameBoardEvent.OnTextChange -> onTextChange(event.text, event.player)
            is GameBoardEvent.OnDiceSelected -> onDiceSelected(event.dice)
            is GameBoardEvent.OnSecondaryDiceSelected -> onSecondaryDiceSelected(event.dice)
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

    private fun onSubmit() {
        updateState {
            copy(
                players = List(state.numberOfPlayers) {
                    Player(
                        position = it,
                        label = "Player ${it + 1}",
                        place = it,
                        points = 0f
                    )
                },
                bottomSheetEnum = BottomSheetEnum.NAMES
            )
        }
    }

    private fun onPlayGame() {
        updateState {
            copy(
                gameState = GameState.START_ROUND,
                players = state.players.mapIndexed { index, player ->
                    player.copy(
                        name = player.name.ifEmpty { "Player ${index + 1}" }
                    )
                }
            )
        }
    }

    private fun resetSettings() {
        updateState {
            copy(
                pointsToWin = GameRules.DEFAULT_POINTS_TO_WIN,
                numberOfPlayers = GameRules.DEFAULT_NUMBER_OF_PLAYERS
            )
        }
    }

    private fun resetPlayerNames() {
        updateState { copy(players = players.map { it.copy(name = "") }) }
    }

    private fun endTurn(player: Player) {
        val currentPlayerPoints = player.points + state.stashedBankPoints + state.bankPoints
        val nextPlayerPosition = (state.currentPlayerPosition + 1).mod(state.players.size)
        val gameState = if (state.players[nextPlayerPosition].isWinner) GameState.END else GameState.START_ROUND

        updateState {
            copy(
                gameState = gameState,
                hasValidPoints = false,
                dice = createDice(),
                secondaryDice = emptyList(),
                bankPoints = 0,
                stashedBankPoints = 0,
                players = players.mapIndexed { index, currentPlayer ->
                    if (index == player.position) {
                        currentPlayer.copy(
                            points = currentPlayerPoints,
                            isWinner = currentPlayerPoints >= pointsToWin
                        )
                    } else currentPlayer
                },
                currentPlayerPosition = nextPlayerPosition
            )
        }
    }

    private fun roll() {
        updateState {
            copy(
                gameState = GameState.ROLLING
            )
        }
    }

    private fun updateRolledDice() {
        val rolledDice = state.dice.mapValues { it.value.copy(info = DiceInfo.values().random()) }
        val gameState = if (farkleDetector.isFarkle(rolledDice.values)) GameState.FARKLE else GameState.PLAYING

        updateState {
            copy(
                dice = rolledDice,
                gameState = gameState
            )
        }
    }

    private fun bankPoints() {
        updateState {
            copy(
                stashedBankPoints = stashedBankPoints + bankPoints,
                bankPoints = 0,
                gameState = GameState.ROLLING,
                secondaryDice = secondaryDice.map { it.copy(locked = true) }.takeIf { it.size != 6 } ?: emptyList(),
                hasValidPoints = false,
                dice = dice.filterNot { it.value.isSelected }.ifEmpty { createDice() }
            )
        }
    }

    private fun dismiss() {
        updateState {
            copy(
                bankPoints = 0,
                stashedBankPoints = 0
            )
        }
        endTurn(state.players[state.currentPlayerPosition])
    }


    private fun onTextChange(text: String, player: Player) {
        updateState {
            copy(
                players = players.mapIndexed { index, defaultPlayer ->
                    if (index == player.position) {
                        defaultPlayer.copy(name = text)
                    } else {
                        defaultPlayer
                    }
                }
            )
        }
    }

    private fun onDiceSelected(currentDice: Dice) {
        val updatedSecondaryDice = if (currentDice.isSelected) {
            state.secondaryDice.mapNotNull { if (it.position == currentDice.position) null else it }
        } else {
            state.secondaryDice + listOf(currentDice)
        }
        val bankPoints = farkleDetector.getPoints(updatedSecondaryDice.filter { !it.locked })

        if (state.gameState == GameState.PLAYING) {
            updateState {
                copy(
                    dice = dice.mapValues {
                        if (it.value.position == currentDice.position)
                            it.value.copy(isSelected = !it.value.isSelected)
                        else it.value
                    },
                    secondaryDice = updatedSecondaryDice,
                    hasValidPoints = bankPoints != null,
                    bankPoints = bankPoints ?: 0
                )
            }
        }
    }

    private fun onSecondaryDiceSelected(currentDice: Dice) {
        val updatedSecondaryDice = state.secondaryDice.mapNotNull {
            if (it.position == currentDice.position) null else it
        }
        val bankPoints = farkleDetector.getPoints(updatedSecondaryDice.filter { !it.locked })
        updateState {
            copy(
                dice = dice.mapValues {
                    if (it.value.position == currentDice.position)
                        it.value.copy(isSelected = false)
                    else it.value
                },
                secondaryDice = updatedSecondaryDice,
                hasValidPoints = bankPoints != null,
                bankPoints = bankPoints ?: 0
            )
        }
    }

    private fun updateState(state: GameBoardState.() -> GameBoardState) {
        val newState = state(_state.value)
        _state.value = newState
    }
}