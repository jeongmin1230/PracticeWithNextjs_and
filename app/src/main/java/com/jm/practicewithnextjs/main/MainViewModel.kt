package com.jm.practicewithnextjs.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jm.practicewithnextjs.User
import com.jm.practicewithnextjs.api.builder.APIBuilder
import com.jm.practicewithnextjs.api.model.request.WriteRequest
import com.jm.practicewithnextjs.api.model.response.NoticeDetailResponse
import com.jm.practicewithnextjs.api.model.response.NoticeListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    var noticeList = mutableStateOf(listOf<NoticeListResponse.Data>())
    var writingId = mutableStateOf("")
    var loadContent = mutableStateOf(listOf(NoticeDetailResponse()))

    private val _writeTitle = mutableStateOf("")
    val writeTitle: MutableState<String> = _writeTitle

    private val _writeBody = mutableStateOf("")
    val writeBody: MutableState<String> = _writeBody

    fun init() {
        _writeTitle.value = ""
        _writeBody.value = ""
    }

    // 공지 사항 전체 가져 오기
    fun loadList() {
        APIBuilder.notice.noticeList().enqueue(object : Callback<List<NoticeListResponse.Data>> {
            override fun onResponse(call: Call<List<NoticeListResponse.Data>>, response: Response<List<NoticeListResponse.Data>>) {
                if (response.isSuccessful) {
                    response.body()?.let { noticeList.value = it }
                } else {
                    println("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<NoticeListResponse.Data>>, t: Throwable) {
                println("onFailure : ${t.message}")
            }
        })
    }

    // 공지 사항 상세 가져 오기
    fun detail() {
        APIBuilder.notice.detail(writingId.value).enqueue(object : Callback<List<NoticeDetailResponse>> {
            override fun onResponse(call: Call<List<NoticeDetailResponse>>, response: Response<List<NoticeDetailResponse>>) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.firstOrNull()?.let { loadContent.value = listOf(it) }
                } else {
                    println("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<NoticeDetailResponse>>, t: Throwable) {
                println("onFailure : ${t.message}")
            }
        })
    }

    // 공지 사항 작성(manager 만)
    fun write(date: String, done: () -> Unit) {
        APIBuilder.notice.write(WriteRequest(_writeTitle.value, _writeBody.value, date, User.name)).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if(response.isSuccessful) done()
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                println("onFailure : ${t.message}")
            }

        })
    }
}