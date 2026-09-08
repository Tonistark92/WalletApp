package com.iscoding.android.interceptors.certificate

import okhttp3.CertificatePinner
import java.security.MessageDigest
import java.security.cert.Certificate

object CertificatePinningConfig {

    /**
     * Pin the SPKI (Subject Public Key Info) hash
     * This is the RECOMMENDED approach — keys rotate less than certificates
     */
    fun createCertificatePinner(): CertificatePinner {
        return CertificatePinner.Builder()
            // Primary pin: Current production key
            .add(
                "api.yourbank.com",
                "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=" // Replace with actual hash
            )
            // Backup pin: Next key (for rotation)
            .add(
                "api.yourbank.com",
                "sha256/BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB=" // Replace with backup
            )
            .build()
    }

    /**
     * Extract SPKI hash from certificate for setup
     */
    fun extractSpkiHash(certificate: Certificate): String {
        val publicKey = certificate.publicKey.encoded
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(publicKey)
        return "sha256/" + hash.joinToString("") { "%02x".format(it) }
    }
}