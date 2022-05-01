package com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.GameBoardViewModel
import com.example.rainbowfarkle.ui.theme.Shapes
import com.example.rainbowfarkle.ui.widgets.PlayerInputField

@Composable
fun LeaderboardPage(
    viewModel: GameBoardViewModel = hiltViewModel()
) {
    val state = viewModel.state
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        if (state.players.isEmpty()) {
            items(count = 12) { index ->
                PlayerInputField(value = "", onValueChange = {} )
                Spacer(modifier = Modifier.height(16.dp))
//                OutlinedTextField(
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(8.dp))
//                        .border(2.dp, Color.Gray)
//                        .background(Color.White)

//                    value = "",
//                    placeholder = { Text(text = "Player ${index + 1}") },
//                    onValueChange = {}
//                )
            }
        } else {

        }
    }
}