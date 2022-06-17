package com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.rainbowfarkle.R
import com.example.rainbowfarkle.feature_game_board.domain.models.Player

@Composable
fun NamesBottomSheet(
    players: List<Player>,
    onReset: () -> Unit,
    onSubmit: () -> Unit,
    onTextChange: (String, Player) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .imePadding()
            .windowInsetsPadding(
                androidx.compose.foundation.layout.WindowInsets.systemBars.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            )
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
        state = listState
    ) {
        items(players) { player ->
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = player.name,
                onValueChange = { text ->
                    onTextChange(text, player) },
                label = { Text(text = stringResource(id = R.string.player_num, player.position + 1)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = if (player.position == players.lastIndex) {
                        ImeAction.Done
                    } else {
                        ImeAction.Next
                    }
                ),
                keyboardActions = KeyboardActions(onDone = { onSubmit() })
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            BottomButtonRow(
                modifier = Modifier.padding(bottom = 16.dp),
                onReset = onReset,
                onSubmit = onSubmit
            )
        }
    }
}