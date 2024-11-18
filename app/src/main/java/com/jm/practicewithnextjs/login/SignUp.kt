package com.jm.practicewithnextjs.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.jm.practicewithnextjs.AppTopBar
import com.jm.practicewithnextjs.ConfirmPwSupportingText
import com.jm.practicewithnextjs.EnterColumn
import com.jm.practicewithnextjs.GeneralButton
import com.jm.practicewithnextjs.PwSupportingText
import com.jm.practicewithnextjs.R
import com.jm.practicewithnextjs.SupportingText
import com.jm.practicewithnextjs.isValidPassword
import com.jm.practicewithnextjs.setTextFieldColor
import com.jm.practicewithnextjs.showToast
import com.jm.practicewithnextjs.ui.theme.Shapes

@Composable
fun SignUp(navController: NavHostController) {
    val context = LocalContext.current
    val signUpViewModel = remember { SignUpViewModel() }
    val press = remember { mutableStateOf(false) }
    val idEnabled = remember { mutableStateOf(true) }
    val type = remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize()) {
        AppTopBar(stringResource(R.string.sign_up)) { navController.popBackStack() }
        Spacer(Modifier.height(20.dp))
        Column(Modifier.padding(horizontal = 8.dp)) {
            EnterColumn(stringResource(R.string.name), signUpViewModel.choose(0), false)
            Spacer(Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.id),
                style = MaterialTheme.typography.labelSmall.copy(color = Color.Black),
                modifier = Modifier.padding(start = 12.dp)
            )
            Spacer(Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                TextField(
                    value = signUpViewModel.id.value,
                    onValueChange = { signUpViewModel.id.value = it },
                    colors = setTextFieldColor(Color(0xFFE2EDEB)),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = if(!idEnabled.value) Color.Gray else Color.Black),
                    shape = Shapes.small,
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier
                        .focusable(idEnabled.value)
                        .weight(1f)
                        .padding(end = 4.dp)
                        .height(48.dp)
                )
                Box(
                    modifier = Modifier
                    .background(Color.LightGray, shape = Shapes.large)
                    .padding(horizontal = 4.dp, vertical = 8.dp)) {
                    Text(
                        text = stringResource(R.string.check_duplicate),
                        style = MaterialTheme.typography.labelMedium.copy(Color.Black),
                        modifier = Modifier
                            .background(Color.LightGray)
                            .padding(horizontal = 4.dp, vertical = 8.dp)
                            .clickable(enabled = signUpViewModel.id.value.isNotEmpty()) {
                                press.value = true
                                signUpViewModel.checkDuplicateId()
                            }
                    )
                }
            }
            if(press.value) {
                when(signUpViewModel.isDuplicated.value) {
                    true -> SupportingText(stringResource(R.string.id_already_used), false)
                    false -> {
                        IdDialog(
                            onDismiss = { press.value = false },
                            confirmAction = {
                                press.value = false
                                idEnabled.value = false
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            EnterColumn(stringResource(R.string.password), signUpViewModel.choose(2), true)
            if(signUpViewModel.pw.value.isNotEmpty()) {
                PwSupportingText(signUpViewModel.pw.value)
            }
            Spacer(Modifier.height(10.dp))
            EnterColumn(stringResource(R.string.confirm_password), signUpViewModel.choose(3), true)
            if(signUpViewModel.confirmPw.value.isNotEmpty()) {
                ConfirmPwSupportingText(signUpViewModel.pw.value, signUpViewModel.confirmPw.value)
            }
            Spacer(Modifier.height(10.dp))
            EnterColumn(stringResource(R.string.phone), signUpViewModel.choose(4), false)
            Spacer(Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.type),
                style = MaterialTheme.typography.labelSmall.copy(color = Color.Black, textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 4.dp)
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.height(100.dp),
                verticalAlignment = Alignment.CenterVertically) {
                ChooseType(true,
                    modifier = Modifier
                        .background(if(signUpViewModel.type.value == context.getString(R.string.manager)) Color(0xFFE2EDEB) else Color.Transparent)
                        .clickable {
                            type.value = context.getString(R.string.manager)
                            signUpViewModel.type.value = type.value
                        }
                        .border(1.dp, Color.Black)
                        .weight(1f)
                )
                Spacer(Modifier.width(20.dp))
                ChooseType(false,
                    modifier = Modifier
                        .background(if(signUpViewModel.type.value == context.getString(R.string.general)) Color(0xFFE2EDEB) else Color.Transparent)
                        .clickable {
                            type.value = context.getString(R.string.general)
                            signUpViewModel.type.value = type.value
                        }
                        .border(1.dp, Color.Black)
                        .weight(1f)
                    )
            }
            Spacer(Modifier.height(20.dp))
            GeneralButton(Modifier.background(Color(0xFFBCD4C9)), stringResource(R.string.sign_up)) {
                if(signUpViewModel.name.value.isNotEmpty()
                    && !idEnabled.value
                    && isValidPassword(signUpViewModel.pw.value)
                    && signUpViewModel.pw.value == signUpViewModel.confirmPw.value
                    && signUpViewModel.phone.value.isNotEmpty()
                    && signUpViewModel.type.value.isNotEmpty()) { signUpViewModel.signUp(context, navController) }
                else {
                    when {
                        signUpViewModel.isDuplicated.value -> context.showToast(context.getString(R.string.sign_up_id_warning))/** 중복된 아이디 일 경우 */
                        signUpViewModel.pw.value.isNotEmpty() && !isValidPassword(signUpViewModel.pw.value) -> context.showToast(context.getString(R.string.sign_up_password_warning)) /** 비밀 번호 형식이 맞지 않을 경우 */
                        signUpViewModel.confirmPw.value.isNotEmpty() && signUpViewModel.pw.value != signUpViewModel.confirmPw.value -> context.showToast(context.getString(R.string.sign_up_confirm_password_warning))/** 비밀 번호와 확인이 맞지 않을 경우 */
                        else -> context.showToast(context.getString(R.string.please_enter_all))/** 빈 칸이 있을 경우 */
                    }
                }
            }
        }
    }
}

@Composable
fun IdDialog(onDismiss: () -> Unit, confirmAction: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.id_available),
                    style = MaterialTheme.typography.bodyLarge.copy(Color.Black)
                )
            }
        },
        shape = Shapes.medium,
        containerColor = Color.White,
        confirmButton = {
            Text(
                text = stringResource(id = R.string.yes),
                style = MaterialTheme.typography.bodyLarge.copy(Color.Blue),
                modifier = Modifier.clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { confirmAction() }
            )
        },
        dismissButton = {
            Text(
                text = stringResource(id = R.string.no),
                style = MaterialTheme.typography.bodyMedium.copy(Color.Red),
                modifier = Modifier
                    .padding(end = 32.dp)
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { onDismiss() }
            )
        },
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun ChooseType(isManager: Boolean, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier) {
        Image(
            imageVector = if(isManager) ImageVector.vectorResource(R.drawable.ic_manager) else ImageVector.vectorResource(R.drawable.ic_general),
            contentDescription = if(isManager) stringResource(R.string.manager) else stringResource(R.string.general),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = if(isManager) stringResource(R.string.manager) else stringResource(R.string.general),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.4f)
        )
    }
}