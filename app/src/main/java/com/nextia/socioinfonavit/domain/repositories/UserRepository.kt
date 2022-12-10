package com.nextia.socioinfonavit.domain.repositories

import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.functional.Either
import com.nextia.socioinfonavit.data.dto.BenevitResponse
import com.nextia.socioinfonavit.data.dto.LoginRequest
import com.nextia.socioinfonavit.data.dto.UserResponse
import com.nextia.socioinfonavit.data.dto.Wallet

interface UserRepository {
    fun login(loginRequest: LoginRequest): Either<Failure, UserResponse>
    fun logout(): Either<Failure, Unit>
    fun getWallets(): Either<Failure, List<Wallet>>
    fun getBenevits(): Either<Failure, BenevitResponse>
}