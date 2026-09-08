package com.iscoding.common.util

import java.security.MessageDigest
import java.util.UUID

object IdempotencyKeyGenerator {

    /**
     * Generates deterministic idempotency key
     * Same transfer = same key (prevents accidental duplicates)
     * Different transfer = different key
     */
//    fun generate(request: TransferRequest): String {
//        // Combine transfer details for deterministic hash
//        val raw = buildString {
//            append(request.fromAccountId)
//            append("|")
//            append(request.toAccountId)
//            append("|")
//            append(request.amount.toString())
//            append("|")
//            append(request.currency)
//            append("|")
//            append(request.reference ?: "")
//            append("|")
//            // Time window: transfers within same minute = same key
//            // This prevents double-submit if user taps twice quickly
//            append(System.currentTimeMillis() / (60 * 1000))
//        }
//
//        return hashString(raw)
//    }

    /**
     * Generate random UUID-based key for non-deterministic scenarios
     */
    fun generateRandom(): String {
        return UUID.randomUUID().toString()
    }

    private fun hashString(input: String): String {
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }
}