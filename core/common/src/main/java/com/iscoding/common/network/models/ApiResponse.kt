package com.wallet.core.network

/**
 * Shape of every response from the Spring Boot backend. `data` is populated
 * on success, `error` on failure — never both. Deserialize into this wrapper
 * first, then unwrap into the DTO/domain type.
 */
data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val error: ApiError?,
    val timestamp: Long
)

data class ApiError(
    val code: String,
    val message: String,
    val fieldErrors: List<FieldError>? = null
)

data class FieldError(
    val field: String,
    val message: String
)
