package com.nextia.socioinfonavit.domain.usecases

import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.functional.Either
import com.nextia.socioinfonavit.core.interactor.UseCase
import com.nextia.socioinfonavit.core.utils.CipherHelper
import com.nextia.socioinfonavit.data.dto.LoginRequest
import com.nextia.socioinfonavit.data.dto.UserRequest
import com.nextia.socioinfonavit.data.dto.UserResponse
import com.nextia.socioinfonavit.domain.repositories.UserRepository
import javax.inject.Inject

class LogInUC @Inject constructor(
    private val userRepository: UserRepository,
    private val cipherHelper: CipherHelper
    ) : UseCase<UserResponse, LoginRequest>() {
    override suspend fun run(params: LoginRequest): Either<Failure, UserResponse> {
        val paramsJoined = params.joinKeys()
        cipherHelper.encrypt(paramsJoined)
        return userRepository.login(UserRequest(cipherHelper.encrypt(paramsJoined)))
    }
}