package com.sametozkan.kutuphane.domain.usecase.account

import com.sametozkan.kutuphane.data.dto.response.AccountRes
import com.sametozkan.kutuphane.domain.repository.AccountRepository
import com.sametozkan.kutuphane.domain.usecase.MyResult
import javax.inject.Inject

class FindAccountByIdUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(id: Long): MyResult<AccountRes> {
        return try {
            val response = accountRepository.findById(id)
            if (response.isSuccessful) {
                val accountRes = response.body()
                if (accountRes != null) {
                    MyResult.Success(accountRes)
                } else {
                    MyResult.Error(Exception("Account not found"))
                }
            } else {
                MyResult.Error(Exception("Failed to find account: ${response.message()}"))
            }
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }
}