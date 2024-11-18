package com.jm.practicewithnextjs.api.model.request

data class SignUpRequest(
    val name: String = "",
    val id: String = "",
    val pw: String = "",
    val phone: String = "",
    val type: String = ""
)
