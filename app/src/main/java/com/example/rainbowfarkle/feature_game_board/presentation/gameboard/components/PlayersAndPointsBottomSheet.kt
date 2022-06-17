package com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.rainbowfarkle.R
import com.example.rainbowfarkle.ui.widgets.MinusRoundIconButton
import com.example.rainbowfarkle.ui.widgets.PlusRoundIconButton
import com.example.rainbowfarkle.ui.widgets.ResourceIcon

@Composable
fun PlayersAndPointsBottomSheet(
    numOfPlayers: Int,
    pointsToWin: Float,
    onMinusPlayer: () -> Unit,
    onPlusPlayer: () -> Unit,
    onMinusPoint: () -> Unit,
    onPlusPoint: () -> Unit,
    onReset: () -> Unit,
    onSubmit: () -> Unit
) {
    Column(modifier = Modifier
        .windowInsetsPadding(
            WindowInsets.systemBars.only(
                WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
            )
        ).padding(16.dp)
    ) {
        SettingsRow(
            iconId = R.drawable.person,
            stringId = R.string.players,
            number = numOfPlayers,
            onMinusClick = onMinusPlayer,
            onPlusClick = onPlusPlayer
        )
        Spacer(modifier = Modifier.height(16.dp))
        SettingsRow(
            iconId = R.drawable.finish_flag,
            stringId = R.string.points,
            number = pointsToWin.toInt(),
            onMinusClick = onMinusPoint,
            onPlusClick = onPlusPoint
        )
        Spacer(modifier = Modifier.height(32.dp))
        BottomButtonRow(
            onReset = onReset,
            onSubmit = onSubmit
        )
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
fun BottomButtonRow(
    modifier: Modifier = Modifier,
    onReset: () -> Unit,
    onSubmit: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        TextButton(
            onClick = { onReset() },
        ) {
            Text(text = stringResource(id = R.string.reset))
        }
        Button(onClick = { onSubmit() }) {
            Text(text = stringResource(id = R.string.submit))
        }
    }
}