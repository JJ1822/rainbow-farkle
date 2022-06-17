package com.example.rainbowfarkle.feature_game_board.presentation.gameboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components.BottomButtonRow
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components.DiceList
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components.FarkleAlertDialog
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components.LeaderBoard
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components.NamesBottomSheet
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components.PlayerCard
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components.PlayersAndPointsBottomSheet
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components.SecondaryDiceRow
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameBoardScreen(
    navController: NavController,
    viewModel: GameBoardViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Expanded,
        confirmStateChange = { false }
    )
    val bottomSheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
    val state = viewModel.state

    LaunchedEffect(key1 = state.gameState) {
        if (state.gameState == GameState.START_ROUND) {
            coroutineScope.launch {
                sheetState.hide()
            }
        }
    }

    // Screen content
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = bottomSheetShape,
        sheetContent = {
            Modal(state = state, viewModel = viewModel)
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(vertical = 16.dp)
            ) {
                when (state.gameState) {
                    GameState.FARKLE -> FarkleAlertDialog { viewModel.onEvent(GameBoardEvent.Dismiss) }
                    GameState.END -> LeaderBoard(players = state.players)
                    else -> {
                        // no op
                    }
                }

                PlayerCard(
                    players = state.players,
                    currentPlayerPosition = state.currentPlayerPosition,
                    pointsToWin = state.pointsToWin
                )

                DiceList(
                    dice = state.dice,
                    gameState = state.gameState,
//                    updateDice = state.updateDice,
                    updateDiceRoll = { viewModel.onEvent(GameBoardEvent.UpdateRollDice) },
                    onDiceSelected = {
                        viewModel.onEvent(
                            GameBoardEvent.OnDiceSelected(
                                it
                            )
                        )
                    }
                )

                SecondaryDiceRow(
                    bankPoints = state.stashedBankPoints + state.bankPoints,
                    secondaryDice = state.secondaryDice,
                    onSecondaryDiceSelected = {
                        viewModel.onEvent(GameBoardEvent.OnSecondaryDiceSelected(it))
                    }
                )

                BottomButtonRow(
                    player = state.players[state.currentPlayerPosition],
                    gameState = state.gameState,
                    hasValidPoints = state.hasValidPoints,
                    onRollClick = { viewModel.onEvent(GameBoardEvent.Roll) },
                    onBankPointsClick = { viewModel.onEvent(GameBoardEvent.BankPoints) },
                    onEndTurnClick = { viewModel.onEvent(GameBoardEvent.EndTurn(it)) }
                )
            }
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Modal(state: GameBoardState, viewModel: GameBoardViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current

    if (state.bottomSheetEnum == BottomSheetEnum.PLAYERS_POINTS) {
        PlayersAndPointsBottomSheet(
            numOfPlayers = state.numberOfPlayers,
            pointsToWin = state.pointsToWin,
            onMinusPlayer = { viewModel.onEvent(GameBoardEvent.MinusPlayer) },
            onPlusPlayer = { viewModel.onEvent(GameBoardEvent.AddPlayer) },
            onMinusPoint = { viewModel.onEvent(GameBoardEvent.MinusPoint) },
            onPlusPoint = { viewModel.onEvent(GameBoardEvent.AddPoint) },
            onReset = { viewModel.onEvent(GameBoardEvent.ResetSettings) },
            onSubmit = { viewModel.onEvent(GameBoardEvent.Submit) }
        )
    } else {
        NamesBottomSheet(
            players = state.players,
            onReset = { viewModel.onEvent(GameBoardEvent.ResetPlayers) },
            onSubmit = {
                keyboardController?.hide()
                viewModel.onEvent(GameBoardEvent.PlayGame)
            },
            onTextChange = { text, player ->
                viewModel.onEvent(GameBoardEvent.OnTextChange(text, player))
            }
        )
    }
}