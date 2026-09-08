package com.iscoding.android.util

import android.database.sqlite.SQLiteAccessPermException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteDatabaseLockedException
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import com.iscoding.android.R
import com.iscoding.common.error.AppException
import com.iscoding.common.error.ErrorHandler
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException


/**
 * Retrofit implementation of ErrorHandler that maps Throwables to domain-level [AppException].
 */
internal class GeneralErrorHandlerImpl : ErrorHandler {

    override fun getError(throwable: Throwable): AppException {
        throwable.printStackTrace()
        return when (throwable) {
            // Cache/Security/Validation layers throw AppException directly at the
            // source (see examples below) — pass through untouched here.
            is AppException -> throwable

            is IOException -> AppException.Local.IOOperation(
                R.string.error_io_unexpected_message, throwable.message
            )

            is HttpException -> mapHttpStatusCode(throwable)

            is SQLiteException -> mapSqliteException(throwable)

            else -> AppException.Unknown(throwable.message)
        }
    }

    private fun mapSqliteException(e: SQLiteException): AppException.Database = when (e) {
        is SQLiteConstraintException -> AppException.Database.ConstraintViolation(e.message)
        is SQLiteFullException -> AppException.Database.InsufficientSpace
        is SQLiteDatabaseLockedException -> AppException.Database.DatabaseLocked
        is SQLiteAccessPermException -> AppException.Database.PermissionDenied
        is SQLiteDatabaseCorruptException -> AppException.Database.Corruption
        else -> AppException.Database.Unknown(e.message)
    }

    private fun mapHttpStatusCode(exception: HttpException): AppException {
        val errorBody = exception.response()?.errorBody()?.string()
        val json = try {
            if (!errorBody.isNullOrBlank()) JSONObject(errorBody) else JSONObject()
        } catch (_: Exception) { JSONObject() }

        val message = extractErrorMessage(json, exception.message())
        val code = exception.code()

        return when (code) {
            401 -> AppException.Client.Unauthorized
            408 -> AppException.Network.Retrial(R.string.error_timeout, message)
            429 -> AppException.Network.Retrial(R.string.error_too_many_requests, message)
            422 -> AppException.Client.ResponseValidation(extractErrorMap(json), message)
            in 400..499 -> AppException.Client.Unhandled(code, message)
            in 500..599 -> AppException.Server.InternalServerError(code, message)
            else -> AppException.Unknown(message)
        }
    }

    private fun extractErrorMessage(json: JSONObject, fallback: String?): String? = when {
        json.has("details") -> json.optString("details", fallback)
        json.has("error_description") -> json.optString("error_description", fallback)
        json.has("error") -> json.optString("error", fallback)
        json.has("message") -> json.optString("message", fallback)
        json.has("title") -> json.optString("title", fallback)
        else -> fallback
    }

    private fun extractErrorMap(json: JSONObject): Map<String, String> {
        if (!json.has("errors")) return emptyMap()
        return try {
            val errorsJson = json.getJSONObject("errors")
            val result = mutableMapOf<String, String>()
            val keys = errorsJson.keys()
            while (keys.hasNext()) { val k = keys.next(); result[k] = errorsJson.optString(k) }
            result
        } catch (_: Exception) { emptyMap() }
    }
}