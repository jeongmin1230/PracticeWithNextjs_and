package com.jm.practicewithnextjs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun AppTopBar(text: String, onClickBack: () -> Unit) {
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .fillMaxWidth()) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_back),
            contentDescription = stringResource(R.string.back_description),
            modifier = Modifier
                .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { onClickBack() }
                .padding(end = 12.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(Color.Black),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun setTextFieldColor(color: Color): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.DarkGray,
        disabledTextColor = Color.Gray,
        errorTextColor = Color.Red,
        focusedContainerColor = color,
        unfocusedContainerColor = color,
        disabledContainerColor = Color.Transparent,
        cursorColor = Color.Blue,
        errorCursorColor = Color.Red,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Red,
        focusedLeadingIconColor = Color.Black,
        unfocusedLeadingIconColor = Color.DarkGray,
        disabledLeadingIconColor = Color.Gray,
        focusedTrailingIconColor = Color.Black,
        unfocusedTrailingIconColor = Color.DarkGray,
        disabledTrailingIconColor = Color.Gray,
        focusedLabelColor = Color.Blue,
        unfocusedLabelColor = Color.Gray,
        disabledLabelColor = Color.LightGray,
        errorLabelColor = Color.Red
    )
}
