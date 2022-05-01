package com.example.rainbowfarkle.ui.navigation

sealed class Screen(val route: String) {
    object LandingScreen : Screen("landing_screen")
    object GameBoardScreen : Screen("game_board_screen")
}