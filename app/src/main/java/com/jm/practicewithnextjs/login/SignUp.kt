package com.jm.practicewithnextjs.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.jm.practicewithnextjs.AppTopBar
import com.jm.practicewithnextjs.R

@Composable
fun SignUp(navController: NavHostController) {
    val signUpViewModel = SignUpViewModel()
    Column(Modifier.fillMaxSize()) {
        AppTopBar(stringResource(R.string.sign_up)) { navController.popBackStack() }

        Text(
            text = stringResource(R.string.sign_up)
        )
    }
}

@Composable
fun EnterInfo(label: String, info: MutableState<String>) {
    Column(Modifier.fillMaxSize()) {

    }
}