package com.jm.practicewithnextjs.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jm.practicewithnextjs.User
import com.jm.practicewithnextjs.api.builder.APIBuilder
import com.jm.practicewithnextjs.api.model.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    private val _id = mutableStateOf("")
    val id: MutableState<String> = _id
    private val _pw = mutableStateOf("")
    val pw: MutableState<String> = _pw

    fun init() {
        _id.value = ""
        _pw.value = ""
    }

    fun choose(isId: Boolean): MutableState<String> {
        return if(isId) _id else _pw
    }

    fun doLogin(onSuccess: () -> Unit, onFailure: () -> Unit) {
        APIBuilder.auth.login(_id.value, _pw.value).enqueue(object : Callback<List<LoginResponse>> {
            override fun onResponse(call: Call<List<LoginResponse>>, response: Response<List<LoginResponse>>) {
                if (response.isSuccessful && response.body() != null) {
                    val matchedUser = response.body()?.firstOrNull { it.id == id.value && it.pw == pw.value }
                    if (matchedUser != null) {
                        User.name = matchedUser.name
                        User.phone = matchedUser.phone
                        User.type = matchedUser.type
                        println("로그인 정보 : ${matchedUser.name} / ${matchedUser.phone} / ${matchedUser.type}")
                        onSuccess()
                    } else {
                        init()
                        onFailure()
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