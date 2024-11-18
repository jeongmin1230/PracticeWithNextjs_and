package com.jm.practicewithnextjs.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jm.practicewithnextjs.api.builder.APIBuilder
import com.jm.practicewithnextjs.api.model.response.FindIdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindIdViewModel: ViewModel() {
    private val _name = mutableStateOf("")
    val name: MutableState<String> = _name
    private val _phone = mutableStateOf("")
    val phone: MutableState<String> = _phone

    private val _id = mutableStateOf("")
    val id: MutableState<String> = _id

    fun init() {
        name.value = ""
        phone.value = ""
    }

    fun choose(isName: Boolean): MutableState<String> {
        return if(isName) _name else _phone
    }

    fun findId(onSuccess: () -> Unit, onFailure: () -> Unit) {
        APIBuilder.auth.findId(name.value, phone.value).enqueue(object : Callback<List<FindIdResponse>> {
            override fun onResponse(call: Call<List<FindIdResponse>>, response: Response<List<FindIdResponse>>) {
                if(response.isSuccessful && response.body() != null) {
                    val matchedUser = response.body()?.firstOrNull { it.name == name.value && it.phone == phone.value }
                    if(matchedUser != null) {
                        onSuccess()
                        _id.value = matchedUser.id
                    } else {
                        init()
                        onFailure()
                    }
                }
            }

            override fun onFailure(call: Call<List<FindIdResponse>>, t: Throwable) {
                println("onFailure : ${t.message}")
            }
        })
    }
}