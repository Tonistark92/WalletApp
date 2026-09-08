package com.iscoding.android.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer

class SigningInterceptor(
    private val secretKey: ByteArray
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // Don't sign if no body (GET requests may skip)
        if (original.method == "GET") {
            return chain.proceed(original)
        }

        val timestamp = System.currentTimeMillis()
        val nonce = RequestSigner.generateNonce()

        // Read body for signing
        val body = original.body?.let {
            val buffer = Buffer()
            it.writeTo(buffer)
            buffer.readUtf8()
        } ?: ""

        val signature = RequestSigner.signRequest(
            method = original.method,
            path = original.url.encodedPath,
            body = body,
            timestamp = timestamp,
            nonce = nonce,
            secretKey = secretKey
        )

        val signedRequest = original.newBuilder()
            .addHeader("X-Request-Timestamp", timestamp.toString())
            .addHeader("X-Request-Nonce", nonce)
            .addHeader("X-Request-Signature", signature)
            .build()

        return chain.proceed(signedRequest)
    }
}