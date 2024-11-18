package com.jm.practicewithnextjs

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jm.practicewithnextjs.ui.theme.Shapes

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
fun GeneralButton(modifier: Modifier, text: String, click: () -> Unit) {
    Column(modifier) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black, textAlign = TextAlign.Center),
            modifier = Modifier
                .clickable { click() }
                .fillMaxWidth()
                .padding(vertical = 8.dp)
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

@Composable
fun EnterColumn(label: String, tfValue: MutableState<String>, isPassword: Boolean) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(color = Color.Black),
            modifier = Modifier.padding(start = 12.dp)
        )
        Spacer(Modifier.height(4.dp))
        TextField(
            value = tfValue.value,
            onValueChange = { tfValue.value = it },
            colors = setTextFieldColor(Color(0xFFE2EDEB)),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
            shape = Shapes.small,
            visualTransformation = if(isPassword) PasswordVisualTransformation('*') else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PwSupportingText(password: String) {
    if(password.isNotEmpty()) {
        when(isValidPassword(password)) {
            true -> SupportingText(stringResource(R.string.supportingText_right_password), true)
            false -> SupportingText(stringResource(R.string.supportingText_wrong_password), false)
        }
    }
}

@Composable
fun ConfirmPwSupportingText(password: String, confirmPassword: String) {
    if(confirmPassword.isNotEmpty()) {
        when(password == confirmPassword) {
            true -> SupportingText(stringResource(R.string.supportingText_right_confirmPassword), true)
            false -> SupportingText(stringResource(R.string.supportingText_wrong_confirmPassword), false )
        }
    }
}

@Composable
fun SupportingText(text: String, correct: Boolean) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium.copy(if(correct) Color.Blue else Color.Red),
        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
    )
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun isValidPassword(password: String): Boolean {
    val minLength = password.length >= 8
    val hasUppercase = password.any { it.isUpperCase() }
    val hasSpecialChar = password.any { it in "!@#$%^&*" }
    val hasLetter = password.any { it.isLetter() }

    return minLength && hasUppercase && hasSpecialChar && hasLetter
}
