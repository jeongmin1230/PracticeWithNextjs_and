package com.jm.practicewithnextjs.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jm.practicewithnextjs.api.builder.APIBuilder
import com.jm.practicewithnextjs.api.model.response.NoticeDetailResponse
import com.jm.practicewithnextjs.api.model.response.NoticeListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    var noticeList = mutableStateOf(listOf<NoticeListResponse.Data>())
    var writingId = mutableStateOf("")
    var content = mutableStateOf(listOf(NoticeDetailResponse()))

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
                    response.body()?.firstOrNull()?.let { content.value = listOf(it) }
                } else {
                    println("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<NoticeDetailResponse>>, t: Throwable) {
                println("onFailure : ${t.message}")
            }
        })
    }
}