package com.example.rainbowfarkle.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.GameBoardScreen
import com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components.LandingScreen
import com.example.rainbowfarkle.ui.theme.RainbowFarkleTheme

@Composable
fun RainbowFarkleApp() {
    val navController = rememberNavController()
    RainbowFarkleTheme {
        NavHost(
            navController = navController,
            startDestination = Screen.LandingScreen.route
        ) {
            composable(Screen.LandingScreen.route) { LandingScreen(navController) }
            composable(Screen.GameBoardScreen.route) { GameBoardScreen(navController) }
        }
    }
}

