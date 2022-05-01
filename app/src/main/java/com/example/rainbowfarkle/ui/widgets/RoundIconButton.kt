package com.example.rainbowfarkle.ui.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rainbowfarkle.R

@Composable
fun PlusRoundIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        ResourceIcon(resourceId = R.drawable.plus)
    }
}

@Composable
fun MinusRoundIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        ResourceIcon(resourceId = R.drawable.minus)
    }
}