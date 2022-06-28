package com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rainbowfarkle.R
import com.example.rainbowfarkle.feature_game_board.domain.models.Player
import com.example.rainbowfarkle.feature_game_board.presentation.util.GameState

@Composable
fun BottomButtonRow(
    player: Player,
    gameState: GameState,
    hasValidPoints: Boolean,
    onRollClick: () -> Unit,
    onEndTurnClick: (Player) -> Unit,
    onBankPointsClick: () -> Unit
) {
    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        if (gameState == GameState.START_ROUND) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onRollClick,
                enabled = gameState == GameState.ROLLING || gameState == GameState.START_ROUND
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(id = R.string.roll)
                )
            }
        } else {
            Button(
                modifier = Modifier.weight(1f),
                onClick = onBankPointsClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                enabled = hasValidPoints
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(id = R.string.bank_points)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                modifier = Modifier.weight(1f),
                onClick = { onEndTurnClick(player) },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                enabled = gameState == GameState.PLAYING
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(id = R.string.end_turn)
                )
            }
        }
    }
}