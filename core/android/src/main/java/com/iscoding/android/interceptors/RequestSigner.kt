package com.iscoding.android.interceptors

import android.util.Base64
import java.util.UUID
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Signs requests with HMAC-SHA256
 * Provides: Integrity (not tampered) + Authenticity (from legitimate client)
 */
object RequestSigner {

    private const val HMAC_ALGORITHM = "HmacSHA256"

    /**
     * Sign a request body + timestamp + nonce
     * Server verifies with shared secret (or public key)
     */
    fun signRequest(
        method: String,      // GET, POST, etc.
        path: String,        // /accounts/123/balance
        body: String,       // JSON body or empty
        timestamp: Long,     // Prevent replay
        nonce: String,      // Unique per request
        secretKey: ByteArray // From secure storage
    ): String {
        val payload = buildString {
            append(method.uppercase())
            append("\\n")
            append(path)
            append("\\n")
            append(timestamp)
            append("\\n")
            append(nonce)
            append("\\n")
            append(body)
        }

        val mac = Mac.getInstance(HMAC_ALGORITHM)
        mac.init(SecretKeySpec(secretKey, HMAC_ALGORITHM))
        val signature = mac.doFinal(payload.toByteArray())

        return Base64.encodeToString(signature, Base64.NO_WRAP)
    }

    /**
     * Generate nonce for request uniqueness
     */
    fun generateNonce(): String {
        return UUID.randomUUID().toString()
    }
}