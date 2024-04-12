package com.sametozkan.kutuphane.data.datasource.remote

import com.sametozkan.kutuphane.data.dto.request.AccountReq
import com.sametozkan.kutuphane.data.dto.response.AccountRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountService {

    @POST("/api/account")
    suspend fun save(@Body accountReq: AccountReq): Response<Unit>

    @GET("/api/account/{id}")
    suspend fun findById(@Path("id") id: Long): Response<AccountRes>
}