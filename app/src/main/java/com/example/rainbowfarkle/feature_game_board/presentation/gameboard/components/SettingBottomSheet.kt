package com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rainbowfarkle.R
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.GameBoardEvent
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.GameBoardViewModel
import com.example.rainbowfarkle.ui.theme.RainbowFarkleTheme
import com.example.rainbowfarkle.ui.widgets.MinusRoundIconButton
import com.example.rainbowfarkle.ui.widgets.PlusRoundIconButton
import com.example.rainbowfarkle.ui.widgets.ResourceIcon

@Composable
fun SettingBottomSheet(
    viewModel: GameBoardViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        SettingsRow(
            iconId = R.drawable.person,
            stringId = R.string.players,
            number = state.numberOfPlayers,
            onMinusClick = { viewModel.onEvent(GameBoardEvent.MinusPlayer) },
            onPlusClick = { viewModel.onEvent(GameBoardEvent.AddPlayer) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SettingsRow(
            iconId = R.drawable.finish_flag,
            stringId = R.string.points,
            number = state.pointsToWin,
            onMinusClick = { viewModel.onEvent(GameBoardEvent.MinusPoint) },
            onPlusClick = { viewModel.onEvent(GameBoardEvent.AddPoint) }
        )
        Spacer(modifier = Modifier.height(32.dp))
        BottomButtonRow { viewModel.onEvent(GameBoardEvent.Submit) }
    }
}

@Composable
private fun SettingsRow(
    @DrawableRes iconId: Int,
    @StringRes stringId: Int,
    number: Int,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ResourceIcon(
            modifier = Modifier.size(24.dp),
            resourceId = iconId
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = stringId)
        )
        PlusMinusRow(
            number = number,
            onMinusClick = onMinusClick,
            onPlusClick = onPlusClick
        )
    }
}

@Composable
private fun PlusMinusRow(
    number: Int,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        MinusRoundIconButton(
            modifier = Modifier.size(36.dp),
            onClick = onMinusClick
        )
        Text(
            modifier = Modifier.requiredWidth(64.dp),
            textAlign = TextAlign.Center,
            text = "$number"
        )
        PlusRoundIconButton(
            modifier = Modifier.size(36.dp),
            onClick = onPlusClick
        )
    }
}

@Composable
private fun BottomButtonRow(onSubmit: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        TextButton(
            onClick = { /*TODO*/ },
        ) {
            Text(text = stringResource(id = R.string.reset))
        }
        Button(onClick = { onSubmit() }) {
            Text(text = stringResource(id = R.string.submit))
        }
    }
}

@Preview
@Composable
fun SettingBottomSheet_Preview() {
    SettingBottomSheet()
}