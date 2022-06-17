package com.example.rainbowfarkle.feature_game_board.presentation.gameboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.rainbowfarkle.feature_game_board.domain.models.Dice

@Composable
fun SecondaryDiceRow(
    bankPoints: Int,
    secondaryDice: List<Dice>,
    onSecondaryDiceSelected: (Dice) -> Unit,
) {
    val matrix = ColorMatrix().apply { setToSaturation(0F) }

    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Start
        ) {
            secondaryDice.forEach { dice ->
                Image(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .padding(vertical = 4.dp)
                        .fillMaxHeight()
                        .clickable { onSecondaryDiceSelected(dice) },
                    painter = painterResource(id = dice.info.drawableId),
                    contentDescription = "",
                    alpha = if (dice.locked) .5f else 1f,
                    colorFilter = if (dice.locked) ColorFilter.colorMatrix(matrix) else null,
                    contentScale = ContentScale.FillHeight
                )

            }
        }
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bank Points",
                maxLines = 1
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                color = Color.LightGray,
                thickness = 2.dp
            )
            Text(text = "$bankPoints")
        }
    }
}