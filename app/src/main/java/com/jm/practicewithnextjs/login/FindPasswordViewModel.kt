package com.jm.practicewithnextjs.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jm.practicewithnextjs.api.builder.APIBuilder
import com.jm.practicewithnextjs.api.model.response.ChangePasswordResponse
import com.jm.practicewithnextjs.api.model.response.ExistUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindPasswordViewModel: ViewModel() {
    private val _name = mutableStateOf("")
    val name: MutableState<String> = _name
    private val _id = mutableStateOf("")
    var id: MutableState<String> = _id
    private val _phone = mutableStateOf("")
    var phone: MutableState<String> = _phone

    private val _newPw = mutableStateOf("")
    val newPw: MutableState<String> = _newPw
    private val _confirmNewPw = mutableStateOf("")
    val confirmNewPw: MutableState<String> = _confirmNewPw

    fun init() {
        _name.value = ""
        _id.value = ""
        _phone.value = ""
    }

    fun choose(first: Boolean): MutableState<String> {
        return if(first) _newPw else _confirmNewPw
    }

    fun checkExistUser(onSuccess: () -> Unit, onFailure: () -> Unit) {
        APIBuilder.auth.existUser(_name.value, _id.value, _phone.value).enqueue(object : Callback<List<ExistUserResponse>> {
            override fun onResponse(call: Call<List<ExistUserResponse>>, response: Response<List<ExistUserResponse>>) {
                if(response.isSuccessful && response.body() != null) {
                    val matchedUser = response.body()?.firstOrNull { it.name == name.value && it.id == id.value && it.phone == phone.value }
                    if(matchedUser != null) {
                        onSuccess()
                    } else {
                        init()
                        onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<List<ExistUserResponse>>, t: Throwable) {
                println("onFailure : ${t.message}")
            }
        })
    }

    fun changePassword(onSuccess: () -> Unit) {
        APIBuilder.auth.changePassword(_id.value, mapOf("pw" to _newPw.value)).enqueue(object : Callback<List<ChangePasswordResponse>> {
            override fun onResponse(call: Call<List<ChangePasswordResponse>>, response: Response<List<ChangePasswordResponse>>) {
                if(response.isSuccessful) {
                    onSuccess()
                }
            }

            override fun onFailure(call: Call<List<ChangePasswordResponse>>, t: Throwable) {
                println("changePassword onFailure : ${t.message}")
            }
        })
    }
}