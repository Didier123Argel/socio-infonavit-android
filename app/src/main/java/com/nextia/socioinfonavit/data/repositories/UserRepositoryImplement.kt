package com.nextia.socioinfonavit.data.repositories

import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.functional.Either
import com.nextia.socioinfonavit.core.helpers.Authenticator
import com.nextia.socioinfonavit.core.plataform.NetworkHandler
import com.nextia.socioinfonavit.data.dto.*
import com.nextia.socioinfonavit.domain.apis.UserApi
import com.nextia.socioinfonavit.domain.repositories.UserRepository
import com.nextia.socioinfonavit.framework.api.ApiRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImplement @Inject constructor(
private val networkHandler: NetworkHandler,
private val userApi: UserApi,
private val authenticator: Authenticator
): UserRepository, ApiRequest{
    override fun login(loginRequest: UserRequest): Either<Failure, UserResponse> {
        return when (networkHandler.isConnected) {
            true -> {
                makeRequest(
                    userApi.login(loginRequest),
                    {userResponse, headers ->
                        authenticator.setToken(headers["Authorization"] as String, "")
                        userResponse
                    },
                    UserResponse.empty()
                )
            }
            false, null -> Either.Left(Failure.NetworkConnection)
        }
    }

    override fun logout(): Either<Failure, Unit> {
        return when (networkHandler.isConnected) {
            true -> {
                makeRequest(
                    userApi.logout(),
                    {_ ->
                        authenticator.clear()
                    },
                    Unit
                )
            }
            false, null -> Either.Left(Failure.NetworkConnection)
        }
    }

    override fun getWallets(): Either<Failure, List<Wallet>> {
        return when (networkHandler.isConnected) {
            true -> {
                makeRequest(
                    userApi.getWallets(),
                    {wallets ->
                        wallets
                    },
                    emptyList()
                )
            }
            false, null -> Either.Left(Failure.NetworkConnection)
        }
    }

    override fun getBenevits(): Either<Failure, BenevitResponse> {
        return when (networkHandler.isConnected) {
            true -> {
                makeRequest(
                    userApi.getBenevits(),
                    { benevits ->
                        benevits
                    },
                    BenevitResponse.empty()
                )
            }
            false, null -> Either.Left(Failure.NetworkConnection)
        }
    }
}