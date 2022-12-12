package com.nextia.socioinfonavit.data.repositories

import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.functional.Either
import com.nextia.socioinfonavit.core.helpers.Authenticator
import com.nextia.socioinfonavit.core.plataform.NetworkHandler
import com.nextia.socioinfonavit.data.dto.*
import com.nextia.socioinfonavit.domain.apis.UserApi
import com.nextia.socioinfonavit.domain.repositories.UserRepository
import com.nextia.socioinfonavit.framework.api.ApiRequest
import okhttp3.Headers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImplement @Inject constructor(
private val networkHandler: NetworkHandler,
private val userApi: UserApi,
private val authenticator: Authenticator
): UserRepository, ApiRequest{
    override fun login(loginRequest: UserRequest): Either<Failure, UserResponse> {
        return makeRequest(
            networkHandler,
            userApi.login(loginRequest),
            {userResponse, headers ->
                authenticator.setToken(headers.getAuthorization(), "")
                userResponse
            },
            UserResponse.empty()
        )
    }

    override fun logout(): Either<Failure, Unit> {
        authenticator.clear()
        return Either.Right(Unit)
    }

    override fun getBenevits(): Either<Failure, BenevitResponse> {
        return makeRequest(
            networkHandler,
            userApi.getBenevits(),
            { benevits, _ ->
                benevits
            },
            BenevitResponse.empty()
        )
    }

    override fun searchBenevits(searchRequest: SearchRequest): Either<Failure, List<Benevit>> {
        return makeRequest(
            networkHandler,
            userApi.search(searchRequest),
            { benevits, _ ->
                benevits
            },
            emptyList()
        )
    }
}

fun Headers.getAuthorization(): String = this["Authorization"] as String