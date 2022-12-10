package com.nextia.socioinfonavit.core.exception

import com.nextia.socioinfonavit.domain.models.server.ErrorResponse
import java.lang.Exception

sealed class Failure {
    object NetworkConnection : Failure()
    class ServerError (val errorResponse: ErrorResponse) : Failure()
    class DebugError (val stackTrace: String): Failure()
    class Unauthorized(val errorResponse: ErrorResponse? = null) : Failure()
    object DatabaseError : Failure()
    object LastPageError : Failure()
    object PermissionDenied: Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}