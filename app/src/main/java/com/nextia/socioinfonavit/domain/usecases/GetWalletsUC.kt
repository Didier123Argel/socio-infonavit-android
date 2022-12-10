package com.nextia.socioinfonavit.domain.usecases

import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.functional.Either
import com.nextia.socioinfonavit.core.interactor.UseCase
import com.nextia.socioinfonavit.data.dto.Wallet
import com.nextia.socioinfonavit.domain.repositories.UserRepository
import javax.inject.Inject

class GetWalletsUC @Inject constructor(
    private val userRepository: UserRepository,
    ) : UseCase<List<Wallet>, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, List<Wallet>> {
        return userRepository.getWallets()
    }
}