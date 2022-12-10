package com.nextia.socioinfonavit.domain.usecases

import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.functional.Either
import com.nextia.socioinfonavit.core.interactor.UseCase
import com.nextia.socioinfonavit.domain.repositories.UserRepository
import javax.inject.Inject

class LogoutUC @Inject constructor(
    private val userRepository: UserRepository,
    ) : UseCase<Unit, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, Unit> {
        return userRepository.logout()
    }
}