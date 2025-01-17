package com.sametozkan.kutuphane.domain.usecase.auth

import com.sametozkan.kutuphane.data.dto.request.LoginReq
import com.sametozkan.kutuphane.data.dto.response.JwtRes
import com.sametozkan.kutuphane.domain.repository.AuthRepository
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyException
import com.sametozkan.kutuphane.util.MyResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(loginReq: LoginReq): MyResult<JwtRes> {
        LoadingManager.startLoading()
        return try {
            val response = authRepository.login(loginReq)
            if (response.isSuccessful) {
                val jwtRes = response.body()
                if (jwtRes != null) {
                    MyResult.Success(jwtRes)
                } else {
                    MyResult.Error(Exception("Jwt is null"), response.code())
                }
            } else {
                if (response.code() == 404 || response.code() == 401)
                    MyResult.Error(
                        MyException.InvalidCredentialsException("Failed to login!"),
                        response.code()
                    )
                else
                    MyResult.Error(Exception("Failed to login!"), response.code())
            }
        } catch (e: Exception) {
            MyResult.Error(e, null)
        } finally {
            LoadingManager.stopLoading()
        }
    }
}