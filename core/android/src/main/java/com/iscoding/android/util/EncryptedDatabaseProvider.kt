package com.iscoding.android.util

// we do encrypt in the DP those
// Account numbers , Balance history , Transaction details ,
// User PIN (hashed) , API tokens(encrypted prefs), Idempotency keys , Pending transfers

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.security.keystore.StrongBoxUnavailableException
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.room.Room
//import net.sqlcipher.database.SupportFactory
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import androidx.core.content.edit
import java.security.KeyStoreException
import java.security.SecureRandom

object EncryptedDatabaseProvider {

    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val KEY_ALIAS = "database_passphrase"
    private const val PREFS_NAME = "secure_prefs"
    private const val ENCRYPTED_PASSPHRASE_KEY = "encrypted_db_passphrase"
    private const val IV_KEY = "db_passphrase_iv"

    /**
     * Creates encrypted Room database with SQLCipher
     * Uses StrongBox if available, falls back to TEE (Trusted Execution Environment)
     */
//    @RequiresApi(Build.VERSION_CODES.P)
//    fun create(context: Context): Result<AppDatabase, SecurityError> {
//        return try {
//            val passphrase = getOrCreatePassphrase(context)
//            val factory = SupportFactory(passphrase)
//
//            val db = Room.databaseBuilder(
//                context,
//                AppDatabase::class.java,
//                "app_encrypted.db"
//            )
//                .openHelperFactory(factory)
//                .fallbackToDestructiveMigration()
//                .build()
//
//            Result.success(db)
//        } catch (e: StrongBoxUnavailableException) {
//            Result.failure(SecurityError.StrongBoxUnavailable("Using TEE fallback"))
//        } catch (e: KeyStoreException) {
//            Result.failure(SecurityError.KeystoreUnavailable(e.message ?: "Keystore error"))
//        } catch (e: Exception) {
//            Result.failure(SecurityError.EncryptionFailed(e))
//        }
//    }

    /**
     * Get existing passphrase or generate new one
     * Stored encrypted with Keystore, never in plaintext
     */
    private fun getOrCreatePassphrase(context: Context): ByteArray {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val encryptedPassphrase = prefs.getString(ENCRYPTED_PASSPHRASE_KEY, null)
        val iv = prefs.getString(IV_KEY, null)

        return if (encryptedPassphrase != null && iv != null) {
            // Decrypt existing passphrase
            decryptPassphrase(encryptedPassphrase, iv)
        } else {
            // Generate new passphrase and encrypt it
            generateAndStorePassphrase( prefs)
        }
    }

    /**
     * Generate random passphrase, encrypt with Keystore, store in prefs
     */
    private fun generateAndStorePassphrase(prefs: SharedPreferences): ByteArray {
        // 256-bit random passphrase for SQLCipher
        val passphrase = ByteArray(32)
        SecureRandom().nextBytes(passphrase)

        // Encrypt passphrase with Keystore key
        val (encrypted, iv) = encryptPassphrase(passphrase)

        // Store encrypted (safe to store in prefs since it's encrypted)
        prefs.edit {
            putString(
                ENCRYPTED_PASSPHRASE_KEY,
                Base64.encodeToString(encrypted, Base64.NO_WRAP)
            )
                .putString(
                    IV_KEY,
                    Base64.encodeToString(iv, Base64.NO_WRAP)
                )
        }

        return passphrase
    }

    /**
     * Create or get SecretKey from Android Keystore
     * Tries StrongBox first, falls back to TEE
     */
    private fun getOrCreateKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)

        // Check if key already exists
        keyStore.getEntry(KEY_ALIAS, null)?.let {
            return (it as KeyStore.SecretKeyEntry).secretKey
        }

        // Generate new key
        val keyGen = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)

        val builder = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .setUserAuthenticationRequired(false) // Set true for biometric
            .setRandomizedEncryptionRequired(true)

        // Try StrongBox (dedicated secure hardware)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                builder.setIsStrongBoxBacked(true)
            } catch (_: StrongBoxUnavailableException) {
                // StrongBox not available, use TEE (still hardware-backed)
                builder.setIsStrongBoxBacked(false)
            }
        }

        keyGen.init(builder.build())
        return keyGen.generateKey()
    }

    private fun encryptPassphrase(plaintext: ByteArray): Pair<ByteArray, ByteArray> {
        val key = getOrCreateKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val iv = cipher.iv
        val encrypted = cipher.doFinal(plaintext)
        return Pair(encrypted, iv)
    }

    private fun decryptPassphrase(encryptedBase64: String, ivBase64: String): ByteArray {
        val key = getOrCreateKey()
        val encrypted = Base64.decode(encryptedBase64, Base64.NO_WRAP)
        val iv = Base64.decode(ivBase64, Base64.NO_WRAP)

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, iv))
        return cipher.doFinal(encrypted)
    }
}