package com.nextia.socioinfonavit.domain.usecases

import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.functional.Either
import com.nextia.socioinfonavit.core.interactor.UseCase
import com.nextia.socioinfonavit.data.dto.LoginRequest
import com.nextia.socioinfonavit.data.dto.UserResponse
import com.nextia.socioinfonavit.domain.repositories.UserRepository
import javax.inject.Inject

class LogInUC @Inject constructor(
    private val userRepository: UserRepository,
    ) : UseCase<UserResponse, LoginRequest>() {
    override suspend fun run(params: LoginRequest): Either<Failure, UserResponse> {
        return userRepository.login(params)
    }
}