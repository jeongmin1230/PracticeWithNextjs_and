package com.jm.practicewithnextjs.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jm.practicewithnextjs.AppTopBar
import com.jm.practicewithnextjs.ConfirmPwSupportingText
import com.jm.practicewithnextjs.EnterColumn
import com.jm.practicewithnextjs.GeneralButton
import com.jm.practicewithnextjs.PwSupportingText
import com.jm.practicewithnextjs.R
import com.jm.practicewithnextjs.showToast

@Composable
fun FindPasswordScreen(loginNavController: NavHostController) {
    val context = LocalContext.current
    val findPasswordViewModel = FindPasswordViewModel()
    val route = stringArrayResource(R.array.findId_findPassword_nav)
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = route[0]) {
        composable(route[0]) {
            Column {
                AppTopBar(stringResource(R.string.change_password)) { loginNavController.popBackStack() }
                Column(
                    modifier = Modifier
                        .padding(all = 12.dp)
                        .fillMaxSize()
                ) {
                    EnterColumn(stringResource(R.string.name), findPasswordViewModel.name, false)
                    EnterColumn(stringResource(R.string.id), findPasswordViewModel.id, false)
                    EnterColumn(stringResource(R.string.phone), findPasswordViewModel.phone, false)
                    GeneralButton(Modifier.background(Color(0xFFBCD4C9)), stringResource(R.string.change_password)) {
                        findPasswordViewModel.checkExistUser(
                            onSuccess = { navController.navigate(route[1]) },
                            onFailure = { context.showToast(context.getString(R.string.fail_find_matched_user))}
                        )
                    }
                }
            }
        }
        composable(route[1]) {
            Column {
                AppTopBar(stringResource(R.string.change_password)) { navController.popBackStack() }
                Column(
                    modifier = Modifier
                        .padding(all = 12.dp)
                        .fillMaxSize()
                ) {
                    Column {
                        Column(modifier = Modifier
                            .border(1.dp, Color.Black)
                            .fillMaxWidth()) {
                            Text(
                                text = stringResource(R.string.success_find_matched_user_after),
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black, textAlign = TextAlign.Center),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp, horizontal = 12.dp)
                            )
                        }
                        Spacer(Modifier.height(20.dp))
                        EnterColumn(stringResource(R.string.new_password), findPasswordViewModel.choose(true), true)
                        if(findPasswordViewModel.newPw.value.isNotEmpty()) {
                            PwSupportingText(findPasswordViewModel.newPw.value)
                        }
                        Spacer(Modifier.height(10.dp))
                        EnterColumn(stringResource(R.string.confirm_new_password), findPasswordViewModel.choose(false), true)
                        if(findPasswordViewModel.confirmNewPw.value.isNotEmpty()) {
                            ConfirmPwSupportingText(findPasswordViewModel.newPw.value, findPasswordViewModel.confirmNewPw.value)
                        }
                        Spacer(Modifier.height(10.dp))
                        GeneralButton(Modifier.background(Color(0xFFBCD4C9)), stringResource(R.string.do_change_password)) {
                            if(findPasswordViewModel.newPw.value == findPasswordViewModel.confirmNewPw.value) {
                                findPasswordViewModel.changePassword{
                                    context.showToast(context.getString(R.string.success_change_password))
                                }
                            } else context.showToast(context.getString(R.string.supportingText_wrong_confirmPassword))
                            navController.popBackStack()
                            loginNavController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}