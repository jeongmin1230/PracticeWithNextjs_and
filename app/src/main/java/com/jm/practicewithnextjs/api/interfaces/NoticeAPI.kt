package com.jm.practicewithnextjs.api.interfaces

import com.jm.practicewithnextjs.api.model.response.NoticeDetailResponse
import com.jm.practicewithnextjs.api.model.response.NoticeListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NoticeAPI {
    @Headers("accept: application/json", "content-type: application/json")
    @GET("notice")
    fun noticeList(): Call<List<NoticeListResponse.Data>>

    @Headers("accept: application/json", "content-type: application/json")
    @GET("notice")
    fun detail(
        @Query("id") writingId: String
    ): Call<List<NoticeDetailResponse>>
}