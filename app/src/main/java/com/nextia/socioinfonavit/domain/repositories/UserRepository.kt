package com.nextia.socioinfonavit.domain.repositories

import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.functional.Either
import com.nextia.socioinfonavit.data.dto.LoginRequest
import com.nextia.socioinfonavit.data.dto.UserResponse

interface UserRepository {
    fun login(loginRequest: LoginRequest): Either<Failure, UserResponse>
    fun logout(): Either<Failure, Unit>
}