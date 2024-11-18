package com.jm.practicewithnextjs.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jm.practicewithnextjs.EnterColumn
import com.jm.practicewithnextjs.GeneralButton
import com.jm.practicewithnextjs.R
import com.jm.practicewithnextjs.login.ui.theme.PracticeWithNextjsTheme
import com.jm.practicewithnextjs.main.MainActivity
import com.jm.practicewithnextjs.setTextFieldColor
import com.jm.practicewithnextjs.showToast
import com.jm.practicewithnextjs.ui.theme.Shapes

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
    val context = LocalContext.current
    val loginViewModel = LoginViewModel()

    Column(
        modifier = Modifier
            .padding(all = 12.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center) {
        EnterColumn(stringResource(R.string.id), loginViewModel.choose(true), false)
        Spacer(Modifier.height(10.dp))
        EnterColumn(stringResource(R.string.password), loginViewModel.choose(false), true)
        Spacer(Modifier.height(10.dp))
        GeneralButton(Modifier.background(Color(0xFFBCD4C9)), stringResource(R.string.login)) {
            if(loginViewModel.id.value.isNotEmpty() && loginViewModel.pw.value.isNotEmpty()) {
                loginViewModel.doLogin(
                    onSuccess = {
                        context.apply {
                            val intent = Intent(context, MainActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            }
                            startActivity(intent)
                        }},
                    onFailure = { context.showToast(context.getString(R.string.login_warning)) })
            } else context.showToast(context.getString(R.string.login_empty))
 }
        Spacer(Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(
                name = stringResource(R.string.find_id),
                modifier = Modifier
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { loginNav.navigate(routeArray[1]) }
                    .weight(1f))
            TextButton(
                name = stringResource(R.string.change_password),
                modifier = Modifier
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { loginNav.navigate(routeArray[2]) }
                    .weight(1f))
            TextButton(
                name = stringResource(R.string.sign_up),
                modifier = Modifier
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { loginNav.navigate(routeArray[3]) }
                    .weight(1f))
        }
    }
}

@Composable
fun TextButton(name: String, modifier: Modifier) {
    Text(
        text = name,
        style = MaterialTheme.typography.labelMedium.copy(color = Color.Black, textAlign = TextAlign.Center),
        modifier = modifier
    )
}