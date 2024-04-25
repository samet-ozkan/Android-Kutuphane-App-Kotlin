package com.sametozkan.kutuphane.domain.usecase.account

import com.sametozkan.kutuphane.data.dto.request.AccountReq
import com.sametozkan.kutuphane.domain.repository.AccountRepository
import com.sametozkan.kutuphane.domain.usecase.MyResult
import javax.inject.Inject

class SaveAccountUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(accountReq: AccountReq): MyResult<Unit> {
        return try {
            val response = accountRepository.save(accountReq)
            if (response.isSuccessful) {
                MyResult.Success(Unit)
            } else {
                MyResult.Error(Exception("Failed to save account: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}