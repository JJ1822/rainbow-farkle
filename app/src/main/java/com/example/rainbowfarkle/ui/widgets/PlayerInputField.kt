package com.example.rainbowfarkle.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PlayerInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    // parameters below will be passed to BasicTextField for correct behavior of the text field,
    // and to the decoration box for proper styling and sizing
    val enabled = true
    val singleLine = true

    val colors = TextFieldDefaults.outlinedTextFieldColors(
        unfocusedBorderColor = Color.LightGray,
        focusedBorderColor = Color.DarkGray
    )
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        // internal implementation of the BasicTextField will dispatch focus events
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(2.dp, Color.LightGray),
            elevation = 4.dp
        ) {
            Text(modifier = Modifier.padding(8.dp), text = "Player")
        }
        //        TextFieldDefaults.OutlinedTextFieldDecorationBox(
        //            value = value,
        //            visualTransformation = VisualTransformation.None,
        //            innerTextField = it,
        //            singleLine = singleLine,
        //            enabled = enabled,
        //            // same interaction source as the one passed to BasicTextField to read focus state
        //            // for text field styling
        //            interactionSource = interactionSource,
        //            // keep vertical paddings but change the horizontal
        //            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
        //                start = 8.dp, end = 8.dp
        //            )

        // update border thickness and shape
        //            border = {
        //                TextFieldDefaults.BorderStroke(
        //                    enabled = enabled,
        //                    isError = false,
        //                    colors = colors,
        //                    interactionSource = interactionSource,
        //                    shape = RectangleShape,
        //                    unfocusedBorderThickness = 2.dp,
        //                    focusedBorderThickness = 4.dp
        //                )
        //            },
        // update border colors
        //            colors = colors
        //        )
    }
}