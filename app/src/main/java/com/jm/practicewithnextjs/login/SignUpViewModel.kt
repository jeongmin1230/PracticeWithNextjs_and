package com.jm.practicewithnextjs.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SignUpViewModel: ViewModel() {
    private val name = mutableStateOf("")
    private val id = mutableStateOf("")
    private val password = mutableStateOf("")
    private val confirmPassword = mutableStateOf("")
    private val phone = mutableStateOf("")

    fun inputValue(input: MutableState<String>): MutableState<String> {
        input.value += input.value
        return input
    }
}