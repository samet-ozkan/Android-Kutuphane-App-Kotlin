package com.sametozkan.kutuphane.domain.usecase.account

import com.sametozkan.kutuphane.data.dto.request.AccountReq
import com.sametozkan.kutuphane.domain.repository.AccountRepository
import com.sametozkan.kutuphane.domain.usecase.Result
import javax.inject.Inject

class SaveAccountUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(accountReq: AccountReq): Result<Unit> {
        return try {
            val response = accountRepository.save(accountReq)
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Failed to save account: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}