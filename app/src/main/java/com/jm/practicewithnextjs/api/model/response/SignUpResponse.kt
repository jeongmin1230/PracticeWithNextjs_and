package com.jm.practicewithnextjs.api.model.response

data class SignUpResponse(
    val name: String = "",
    val id: String = "",
    val pw: String = "",
    val phone: String = "",
    val type: String = ""
)