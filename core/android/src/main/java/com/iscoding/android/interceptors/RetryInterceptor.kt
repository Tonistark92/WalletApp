package com.iscoding.android.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

/**
 * Retries on IOException (network failures) and 5xx server errors
 * Does NOT retry 4xx errors (client's fault - won't succeed on retry)
 */
class RetryInterceptor(
    private val maxRetries: Int = 3,
    private val retryIntervalMs: Long = 1000L,
    private val exponentialBackoff: Boolean = true
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var lastException: IOException? = null

        for (attempt in 0 until maxRetries) {
            try {
                val response = chain.proceed(request)
                // Don't retry POST/PUT/DELETE — let WorkManager handle those
                if (request.method != "GET") {
                    return chain.proceed(request)  // No retry for writes
                }
                // Success - return immediately
                if (response.isSuccessful) {
                    return response
                }

                // Don't retry 4xx errors (client errors)
                if (response.code in 400..499) {
                    return response
                }

                // 5xx error - will retry
                response.close()

            } catch (e: IOException) {
                lastException = e
                // Will retry on next iteration
            }

            // Wait before retry (except last attempt)
            if (attempt < maxRetries - 1) {
                val delay = if (exponentialBackoff) {
                    retryIntervalMs * (1 shl attempt) // 1s, 2s, 4s
                } else {
                    retryIntervalMs
                }
                Thread.sleep(delay)
            }
        }

        // All retries exhausted
        throw lastException ?: IOException(
            "Failed after $maxRetries attempts"
        )
    }
}