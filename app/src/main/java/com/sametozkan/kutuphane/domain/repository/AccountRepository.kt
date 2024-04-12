package com.sametozkan.kutuphane.domain.repository

import com.sametozkan.kutuphane.data.dto.request.AccountReq
import com.sametozkan.kutuphane.data.dto.response.AccountRes
import retrofit2.Response

interface AccountRepository {

    suspend fun save(accountReq: AccountReq): Response<Unit>

    suspend fun findById(id: Long): Response<AccountRes>
}