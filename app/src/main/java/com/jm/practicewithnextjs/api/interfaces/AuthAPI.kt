package com.jm.practicewithnextjs.api.interfaces

import com.jm.practicewithnextjs.api.model.response.LoginResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AuthAPI {
    @Headers("accept: application/json", "content-type: application/json")
    @GET("users")
    fun login(
        @Query("id") id: String,
        @Query("pw") pw: String
    ): Call<List<LoginResponse>>
}