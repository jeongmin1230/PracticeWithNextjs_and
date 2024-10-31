package com.jm.practicewithnextjs.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jm.practicewithnextjs.R
import com.jm.practicewithnextjs.login.ui.theme.PracticeWithNextjsTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeWithNextjsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val route = stringArrayResource(R.array.login_nav)
    val loginNavController = rememberNavController()
    NavHost(navController = loginNavController, startDestination = route[0]) {
        composable(route[0]) {
            Login(route, loginNavController)
        }
        composable(route[1]) {
            FindIdScreen(loginNavController)
        }
        composable(route[2]) {
            FindPasswordScreen(loginNavController)
        }
        composable(route[3]) {
            SignUp(loginNavController)
        }
    }
}

@Composable
fun Login(routeArray: Array<String>,loginNav: NavHostController) {
    val loginViewModel = LoginViewModel()

    Column(
        modifier = Modifier
            .padding(all = 12.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center) {
        loginViewModel.InputRow(isId = true)
        Spacer(Modifier.padding(bottom = 8.dp))
        loginViewModel.InputRow(isId = false)
        Spacer(Modifier.padding(bottom = 12.dp))
        loginViewModel.LoginButton()
        Spacer(Modifier.padding(bottom = 8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            loginViewModel.TextButton(
                name = stringResource(R.string.find_id),
                modifier = Modifier
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { loginNav.navigate(routeArray[1]) }
                    .weight(1f))
            loginViewModel.TextButton(
                name = stringResource(R.string.find_password),
                modifier = Modifier
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { loginNav.navigate(routeArray[2]) }
                    .weight(1f))
            loginViewModel.TextButton(
                name = stringResource(R.string.sign_up),
                modifier = Modifier
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { loginNav.navigate(routeArray[3]) }
                    .weight(1f))
        }
    }
}