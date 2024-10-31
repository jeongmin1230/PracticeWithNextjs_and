package com.jm.practicewithnextjs.api.model.response

data class NoticeListResponse(
    val data: List<Data>
) {
    data class Data(
        val id: String = "",
        val title: String = "",
        val body: String = "",
        val date: String = "",
        val registrant: String = ""
    )
}
