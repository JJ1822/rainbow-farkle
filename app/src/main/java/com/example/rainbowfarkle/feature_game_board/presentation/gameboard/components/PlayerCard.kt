package com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rainbowfarkle.R
import com.example.rainbowfarkle.feature_game_board.domain.models.Player

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerCard(
    players: List<Player>,
    currentPlayerPosition: Int,
    pointsToWin: Float
) {
    AnimatedContent(
        targetState = currentPlayerPosition,
        transitionSpec = {
            slideInHorizontally(animationSpec = tween(200, 1000)) { fullWidth -> fullWidth } with
                    slideOutHorizontally(
                        animationSpec = tween(
                            200,
                            1000
                        )
                    ) { fullWidth -> -fullWidth }
//            // Compare the incoming number with the previous number.
//            if (targetState > initialState) {
//                // If the target number is larger, it slides up and fades in
//                // while the initial (smaller) number slides up and fades out.
//                slideInVertically { height -> height } + fadeIn() with
//                        slideOutVertically { height -> -height } + fadeOut()
//            } else {
//                // If the target number is smaller, it slides down and fades in
//                // while the initial number slides down and fades out.
//                slideInVertically { height -> -height } + fadeIn() with
//                        slideOutVertically { height -> height } + fadeOut()
//            }.using(
//                // Disable clipping since the faded slide-in/out should
//                // be displayed out of bounds.
//                SizeTransform(clip = false)
//            )
        }
    ) { position ->
        val player = players[position]
        val playerPoints = player.points
        val places = stringArrayResource(id = R.array.places)
        val progress: Float = if (playerPoints == 0f) {
            .01F
        } else {
            playerPoints / pointsToWin
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier
                        .weight(.45f)
                        .padding(end = 8.dp)) {
                        Row {
                            Text(
                                text = player.name.uppercase(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row {
                            Text(
                                modifier = Modifier.padding(end = 2.dp),
                                text = "Rank:",
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = places[position])
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(.55f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "${playerPoints.toInt()}")
                            Text(text = "${pointsToWin.toInt()}")
                        }
                        Row {
                            RainbowProgressBar(progress)
                        }
                    }
                }
            }

        }
    }
}