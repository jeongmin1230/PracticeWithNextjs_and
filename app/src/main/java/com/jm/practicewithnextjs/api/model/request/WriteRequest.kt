package com.jm.practicewithnextjs.api.model.request

data class WriteRequest(
    val title: String,
    val body: String,
    val date: String,
    val registrant: String
)
