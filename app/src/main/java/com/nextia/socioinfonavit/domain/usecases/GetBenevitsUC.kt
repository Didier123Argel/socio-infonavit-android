package com.nextia.socioinfonavit.domain.usecases

import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.functional.Either
import com.nextia.socioinfonavit.core.interactor.UseCase
import com.nextia.socioinfonavit.data.dto.BenevitResponse
import com.nextia.socioinfonavit.domain.repositories.UserRepository
import javax.inject.Inject

class GetBenevitsUC @Inject constructor(
    private val userRepository: UserRepository,
    ) : UseCase<BenevitResponse, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, BenevitResponse> {
        return userRepository.getBenevits()
    }
}