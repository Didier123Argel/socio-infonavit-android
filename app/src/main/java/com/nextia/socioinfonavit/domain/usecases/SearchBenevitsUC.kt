package com.nextia.socioinfonavit.domain.usecases

import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.functional.Either
import com.nextia.socioinfonavit.core.interactor.UseCase
import com.nextia.socioinfonavit.data.dto.Benevit
import com.nextia.socioinfonavit.data.dto.SearchRequest
import com.nextia.socioinfonavit.domain.repositories.UserRepository
import javax.inject.Inject

class SearchBenevitsUC @Inject constructor(
    private val userRepository: UserRepository,
    ) : UseCase<List<Benevit>, SearchRequest>() {
    override suspend fun run(params: SearchRequest): Either<Failure, List<Benevit>> {
        return userRepository.searchBenevits(params)
    }
}