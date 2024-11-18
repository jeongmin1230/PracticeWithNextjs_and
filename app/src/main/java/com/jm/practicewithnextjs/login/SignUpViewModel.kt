package com.jm.practicewithnextjs.login

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.jm.practicewithnextjs.R
import com.jm.practicewithnextjs.api.builder.APIBuilder
import com.jm.practicewithnextjs.api.model.request.SignUpRequest
import com.jm.practicewithnextjs.api.model.response.SignUpResponse
import com.jm.practicewithnextjs.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    private val _name = mutableStateOf("")
    val name: MutableState<String> = _name
    private val _id = mutableStateOf("")
    val id: MutableState<String> = _id
    private val _isIdDuplicated = mutableStateOf(false)
    val isDuplicated: MutableState<Boolean> = _isIdDuplicated
    private val _pw = mutableStateOf("")
    val pw: MutableState<String> = _pw
    private val _confirmPw = mutableStateOf("")
    val confirmPw: MutableState<String> = _confirmPw
    private val _phone = mutableStateOf("")
    val phone: MutableState<String> = _phone
    private val _type = mutableStateOf("")
    val type: MutableState<String> = _type

    fun choose(num: Int): MutableState<String> {
        return when(num) {
            0 -> _name
            1 -> _id
            2 -> _pw
            3 -> _confirmPw
            4 -> _phone
            else -> mutableStateOf("")
        }
    }

    fun checkDuplicateId() {
        APIBuilder.auth.checkId(_id.value).enqueue(object : Callback<List<Any>> {
            override fun onResponse(call: Call<List<Any>>, response: Response<List<Any>>) {
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        _isIdDuplicated.value = body.isNotEmpty()
                    } ?: run {
                        _isIdDuplicated.value = false
                    }
                } else {
                    _isIdDuplicated.value = false
                }
            }

            override fun onFailure(call: Call<List<Any>>, t: Throwable) {
                _isIdDuplicated.value = false
                println("요청 실패: ${t.message}")
            }
        })
    }



    fun signUp(context: Context, navController: NavHostController) {
        APIBuilder.auth.signUp(SignUpRequest(name.value, id.value, pw.value, phone.value, type.value)).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if(response.isSuccessful && response.body() != null) {
                    context.showToast(context.getString(R.string.sign_up_success))
                    navController.navigate(context.resources.getStringArray(R.array.login_nav)[0])
                } else {
                    context.showToast(context.getString(R.string.sign_up_fail))
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                println("on failure : ${t.message}")
            }
        })
    }
}
