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
import com.jm.practicewithnextjs.EnterColumn
import com.jm.practicewithnextjs.GeneralButton
import com.jm.practicewithnextjs.R
import com.jm.practicewithnextjs.showToast

@Composable
fun FindIdScreen(loginNavController: NavHostController) {
    val context = LocalContext.current
    val findIdViewModel = FindIdViewModel()
    val route = stringArrayResource(R.array.findId_findPassword_nav)
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = route[0]) {
        composable(route[0]) {
            Column {
                AppTopBar(stringResource(R.string.find_id)) { loginNavController.popBackStack() }
                Column(
                    modifier = Modifier
                        .padding(all = 12.dp)
                        .fillMaxSize()) {
                    EnterColumn(stringResource(R.string.name), findIdViewModel.choose(true), false)
                    Spacer(Modifier.height(10.dp))
                    EnterColumn(stringResource(R.string.phone), findIdViewModel.choose(false), false)
                    Spacer(Modifier.height(10.dp))
                    GeneralButton(Modifier.background(Color(0xFFBCD4C9)), stringResource(R.string.find_id)) {
                        if(findIdViewModel.id.value.isNotEmpty() && findIdViewModel.name.value.isNotEmpty() && findIdViewModel.phone.value.isNotEmpty()) {
                            findIdViewModel.findId(
                                onSuccess = { navController.navigate(route[1]) },
                                onFailure = { context.showToast(context.getString(R.string.fail_find_id)) }
                            )
                        } else context.showToast(context.getString(R.string.please_enter_all))
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
        composable(route[1]) {
            Column {
                AppTopBar(stringResource(R.string.find_id)) { navController.popBackStack() }
                Column(modifier = Modifier
                    .padding(all = 12.dp)
                    .fillMaxSize()) {
                    findIdViewModel.init()
                    FindIdLast(findIdViewModel.id.value)
                    Spacer(Modifier.height(10.dp))
                    GeneralButton(Modifier.background(Color(0xFFBCD4C9)), stringResource(R.string.do_login)) {
                        navController.popBackStack()
                        loginNavController.popBackStack()
                    }
                    Spacer(Modifier.height(10.dp))
                    GeneralButton(Modifier.border(1.dp, Color.Black), stringResource(R.string.change_password)) {
                        loginNavController.navigate(context.resources.getStringArray(R.array.login_nav)[2])
                    }
                }
            }
        }
    }
}


@Composable
fun FindIdLast(id: String) {
    Column(Modifier.border(1.dp, Color.DarkGray)) {
        Text(
            text = stringResource(R.string.success_find_id).format(id),
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black, textAlign = TextAlign.Center),
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 12.dp)
                .fillMaxWidth()
        )
    }
}