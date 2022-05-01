package com.example.rainbowfarkle.feature_game_board.presentation.gameboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rainbowfarkle.R
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components.LeaderboardPage
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components.SettingBottomSheet
import com.example.rainbowfarkle.ui.widgets.ResourceIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameBoardScreen(
    navController: NavController,
    viewModel: GameBoardViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded)
    val bottomSheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { LeaderboardPage() }
    ) {
        // Screen content
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = bottomSheetShape,
            sheetContent = { SettingBottomSheet() }
        ) {
            HeaderRow(
                scaffoldState = scaffoldState,
                coroutineScope = coroutineScope,
                onSettingsClick = { viewModel.onEvent(GameBoardEvent.Settings) }
            )
        }
    }
}

@Composable
private fun HeaderRow(
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            contentPadding = PaddingValues(vertical = 4.dp, horizontal = 12.dp),
            modifier = Modifier.defaultMinSize(
                minWidth = ButtonDefaults.MinWidth,
                minHeight = 10.dp
            ),
            shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
            onClick = {
                coroutineScope.launch { scaffoldState.drawerState.open() }
            }
        ) {
            Text(text = "Leaderboard")
        }
        ResourceIcon(
            resourceId = R.drawable.settings,
            modifier = Modifier.clickable { onSettingsClick() }
        )
    }
}