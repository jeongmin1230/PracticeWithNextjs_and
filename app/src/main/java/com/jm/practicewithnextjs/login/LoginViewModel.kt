package com.jm.practicewithnextjs.login

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.jm.practicewithnextjs.R
import com.jm.practicewithnextjs.User
import com.jm.practicewithnextjs.api.builder.APIBuilder
import com.jm.practicewithnextjs.api.model.response.LoginResponse
import com.jm.practicewithnextjs.main.MainActivity
import com.jm.practicewithnextjs.setTextFieldColor
import com.jm.practicewithnextjs.ui.theme.Shapes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    private var id = mutableStateOf("")
    private var pw = mutableStateOf("")

    @Composable
    fun InputRow(isId: Boolean) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = if (isId) stringResource(R.string.id) else stringResource(R.string.password),
                style = MaterialTheme.typography.labelSmall.copy(color = Color.Black, textAlign = TextAlign.End),
                modifier = Modifier
                    .width(60.dp)
                    .padding(end = 4.dp)
            )
            TextField(
                value = if(isId) id.value else pw.value,
                onValueChange = { if(isId) id.value = it else pw.value = it },
                colors = setTextFieldColor(Color(0xFFE2EDEB)),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                shape = Shapes.small,
                visualTransformation = if(isId) VisualTransformation.None else PasswordVisualTransformation('*'),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
    }

    @Composable
    fun LoginButton() {
        val context = LocalContext.current
        Column(Modifier.background(Color(0xFFBCD4C9))) {
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black, textAlign = TextAlign.Center),
                modifier = Modifier
                    .clickable { doLogin(context) }
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
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

    private fun doLogin(context: Context) {
        APIBuilder.auth.login(id.value, pw.value).enqueue(object : Callback<List<LoginResponse>> {
            override fun onResponse(call: Call<List<LoginResponse>>, response: Response<List<LoginResponse>>) {
                if (response.isSuccessful && response.body() != null) {
                    val matchedUser = response.body()?.firstOrNull { it.id == id.value && it.pw == pw.value }
                    if (matchedUser != null) {
                        User.name = matchedUser.name
                        User.phone = matchedUser.phone
                        context.apply {
                            val intent = Intent(context, MainActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            }
                            startActivity(intent)
                        }
                    } else {
                        id.value = ""
                        pw.value = ""
                        Toast.makeText(context, context.getString(R.string.login_warning), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    println("로그인 실패 : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<LoginResponse>>, t: Throwable) {
                println("on failure : ${t.message}")
            }
        })
    }

}