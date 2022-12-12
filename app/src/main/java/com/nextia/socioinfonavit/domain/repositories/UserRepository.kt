package com.nextia.socioinfonavit.domain.repositories

import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.functional.Either
import com.nextia.socioinfonavit.data.dto.*

interface UserRepository {
    fun login(loginRequest: UserRequest): Either<Failure, UserResponse>
    fun logout(): Either<Failure, Unit>
    fun getBenevits(): Either<Failure, BenevitResponse>
    fun searchBenevits(searchRequest: SearchRequest): Either<Failure, List<Benevit>>
}