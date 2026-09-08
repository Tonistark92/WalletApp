package com.iscoding.common.error

import kotlin.reflect.KClass

sealed class AppException(message: String?) : Exception(message) {

    sealed class Network(message: String? = null) : AppException(message) {
        data class Retrial( val messageRes: Int, override val message: String?) :
            Network(message)
        data class Unhandled( val messageRes: Int, override val message: String?) :
            Network(message)
    }

    sealed class Client(message: String? = null) : AppException(message) {
        data object Unauthorized : Client("Unauthorized Access.")

        // FIX: was `: AppException(message)` — broke `is Client` / exhaustive `when(client)`
        data class ResponseValidation(
            val errors: Map<String, String> = hashMapOf(), override val message: String?
        ) : Client(message)

        data class Unhandled(val httpErrorCode: Int, override val message: String?) : Client(
            message = "Unhandled client error with code:$httpErrorCode, and the failure reason: $message"
        )
    }

    sealed class Server(message: String? = null) : AppException(message) {
        data class InternalServerError(val httpErrorCode: Int, override val message: String?) :
            Server("Internal server error with code:$httpErrorCode, and the failure reason: $message")
    }

    sealed class Local(message: String? = null) : AppException(message) {
        data class RequestValidation(val clazz: KClass<*>, override val message: String? = null) :
            Local(StringBuilder("There is missing input for this class: ${clazz.simpleName}").apply {
                message?.let { append(", message: $message") }
            }.toString())

        data class IOOperation( val messageRes: Int, override val message: String? = "") :
            Local(message)
    }

    // NEW — mirrors your old DataError.DatabaseError enum
    sealed class Database(message: String? = null) : AppException(message) {
        data object PermissionDenied : Database("Database permission denied.")
        data object InsufficientSpace : Database("Not enough storage space.")
        data object DatabaseLocked : Database("Database is currently locked.")
        data object DataNotFound : Database("Requested data not found.")
        data class ConstraintViolation(override val message: String?) : Database(message)
        data object Corruption : Database("Database corruption detected.")
        data object SchemaMismatch : Database("Database schema mismatch.")
        data class Unknown(override val message: String?) : Database(message)
    }

    // NEW — mirrors your old CacheError
    sealed class Cache(message: String? = null) : AppException(message) {
        data class Expired(val lastRefresh: Long) : Cache("Cache expired at $lastRefresh")
        data class Invalidated(val eventType: String) : Cache("Cache invalidated by: $eventType")
        data class Conflict(val local: String, val remote: String) :
            Cache("Cache conflict — local=$local remote=$remote")
        data class StorageFailed(override val message: String?) : Cache(message)
    }

    // NEW — mirrors your old SecurityError
    sealed class Security(message: String? = null) : AppException(message) {
        data class KeystoreUnavailable(val reason: String) : Security(reason)
        data class StrongBoxUnavailable(val fallback: String) :
            Security("StrongBox unavailable, fallback: $fallback")
        data class EncryptionFailed(override val message: String?) : Security(message)
        data class DecryptionFailed(override val message: String?) : Security(message)
        data class KeyGenerationFailed(override val message: String?) : Security(message)
    }

    // NEW — mirrors your old ValidationError
    sealed class Validation(message: String? = null) : AppException(message) {
        data class InvalidAmount(val reason: String) : Validation(reason)
        data class InsufficientFunds(val available: String, val requested: String) :
            Validation("Insufficient funds — available=$available requested=$requested")
        data class InvalidAccount(val accountId: String) : Validation("Invalid account: $accountId")
    }

    data class Unknown(override val message: String?) : AppException(message)

    fun isUnauthorized() = this == Client.Unauthorized
}