package com.jm.practicewithnextjs.api.interfaces

import com.jm.practicewithnextjs.api.model.request.SignUpRequest
import com.jm.practicewithnextjs.api.model.response.ChangePasswordResponse
import com.jm.practicewithnextjs.api.model.response.ExistUserResponse
import com.jm.practicewithnextjs.api.model.response.FindIdResponse
import com.jm.practicewithnextjs.api.model.response.LoginResponse
import com.jm.practicewithnextjs.api.model.response.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthAPI {
    @Headers("accept: application/json", "content-type: application/json")
    @GET("users")
    fun login(
        @Query("id") id: String,
        @Query("pw") pw: String
    ): Call<List<LoginResponse>>

    @Headers("accept: application/json", "content-type: application/json")
    @GET("users")
    fun checkId(@Query("id") id: String): Call<List<Any>>

    @Headers("accept: application/json", "content-type: application/json")
    @POST("users")
    fun signUp(
        @Body request: SignUpRequest
    ): Call<SignUpResponse>

    @Headers("accept: application/json", "content-type: application/json")
    @GET("users")
    fun findId(
        @Query("name") name: String,
        @Query("phone") phone: String
    ): Call<List<FindIdResponse>>

    @Headers("accept: application/json", "content-type: application/json")
    @GET("users") // 비밀 번호 찾기 에서 이름, 아이디, 핸드폰 번호 모두 일치 하는 지 확인
    fun existUser(
        @Query("name") name: String,
        @Query("id") id: String,
        @Query("phone") phone: String
    ): Call<List<ExistUserResponse>>

    @Headers("accept: application/json", "content-type: application/json")
    @PATCH("users/{id}") // 비밀 번호 변경
    fun changePassword(
        @Path("id") userId: String,
        @Body newPassword: Map<String, String>
    ): Call<List<ChangePasswordResponse>>
}