package com.sametozkan.kutuphane.data.repository

import com.sametozkan.kutuphane.data.datasource.remote.AccountService
import com.sametozkan.kutuphane.data.dto.request.AccountReq
import com.sametozkan.kutuphane.data.dto.response.AccountRes
import com.sametozkan.kutuphane.domain.repository.AccountRepository
import retrofit2.Response

class AccountRepositoryImpl(private val accountService: AccountService) : AccountRepository {

    override suspend fun save(accountReq: AccountReq): Response<Unit> {
        return accountService.save(accountReq)
    }

    override suspend fun findById(id: Long): Response<AccountRes> {
        return accountService.findById(id)
    }
}