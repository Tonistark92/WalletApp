package com.iscoding.android.pending.worker

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.iscoding.android.pending.PendingStatus
import com.iscoding.android.pending.db.PendingTransferDao
import com.iscoding.android.pending.db.PendingTransferEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.MessageDigest
import java.util.UUID
import java.util.concurrent.TimeUnit

//
//class PendingOperationsManagerImpl(
//    private val context: Context,
//    private val pendingDao: PendingTransferDao,
//    private val workManager: WorkManager
//) : PendingOperationsManager {
//
//    override suspend fun queueTransfer(request: TransferRequest): String {
//
//        // ═══════════════════════════════════════════════════════
//        // DEDUPLICATION: Generate deterministic idempotency key
//        // Same operation (same from/to/amount/time-window) = same key
//        // ═══════════════════════════════════════════════════════
//        val idempotencyKey = generateIdempotencyKey(request)
//
//        // Check if this exact operation already exists
//        val existing = pendingDao.getByIdempotencyKey(idempotencyKey)
//        if (existing != null) {
//            // Duplicate detected! Return existing pending ID
//            return when (existing.status) {
//                PendingStatus.COMPLETED -> {
//                    // Already succeeded — return the completed operation ID
//                    existing.id
//                }
//                PendingStatus.FAILED -> {
//                    // Failed before — retry it
//                    retryOperation(existing.id)
//                    existing.id
//                }
//                else -> {
//                    // Already pending/processing — just return existing ID
//                    existing.id
//                }
//            }
//        }
//
//        // NEW operation — create and queue
//        val pendingId = UUID.randomUUID().toString()
//
//        val entity = PendingTransferEntity(
//            id = pendingId,
//            fromAccountId = request.fromAccountId,
//            toAccountId = request.toAccountId,
//            amount = request.amount.toString(),
//            currency = request.currency,
//            reference = request.reference,
//            idempotencyKey = idempotencyKey,  // Deterministic!
//            status = PendingStatus.PENDING,
//            createdAt = System.currentTimeMillis()
//        )
//
//        pendingDao.insert(entity)
//        scheduleWork(pendingId)
//
//        return pendingId
//    }
//
//    /**
//     * Generate deterministic idempotency key based on operation content.
//     * Same inputs = same key = detected as duplicate.
//     */
//    private fun generateIdempotencyKey(request: TransferRequest): String {
//        // Combine all fields that define "same operation"
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
//            // Time window: operations within 5 minutes are considered same
//            append(System.currentTimeMillis() / (5 * 60 * 1000))
//        }
//
//        // Hash to fixed length
//        return hashString(raw)
//    }
//
//    private fun hashString(input: String): String {
//        val bytes = input.toByteArray()
//        val md = MessageDigest.getInstance("SHA-256")
//        val digest = md.digest(bytes)
//        return digest.fold("") { str, it -> str + "%02x".format(it) }
//    }
//
//    private fun scheduleWork(pendingId: String) {
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .build()
//
//        val inputData = workDataOf(TransferWorker.KEY_TRANSFER_ID to pendingId)
//
//        val workRequest = OneTimeWorkRequestBuilder<TransferWorker>()
//            .setConstraints(constraints)
//            .setInputData(inputData)
//            .setBackoffCriteria(
//                BackoffPolicy.EXPONENTIAL,
//                10_000L,
//                TimeUnit.MILLISECONDS
//            )
//            .build()
//
//        // UNIQUE work prevents duplicate Workers for same operation
//        workManager.enqueueUniqueWork(
//            "transfer_$pendingId",
//            ExistingWorkPolicy.KEEP,  // Keep existing, don't duplicate
//            workRequest
//        )
//    }
//
//    override suspend fun cancelOperation(pendingId: String) {
//        workManager.cancelUniqueWork("transfer_$pendingId")
//        pendingDao.deleteById(pendingId)
//    }
//
//    override suspend fun retryOperation(pendingId: String) {
//        val pending = pendingDao.getById(pendingId) ?: return
//
//        // Reset for retry
//        pendingDao.update(
//            pending.copy(
//                status = PendingStatus.PENDING,
//                retryCount = 0,
//                lastError = null
//            )
//        )
//
//        scheduleWork(pendingId)
//    }
//
//    override fun getPendingOperations(): Flow<List<PendingTransferEntity>> {
//        return pendingDao.getActiveTransfersFlow()
//    }
//
//    override fun getPendingOperationsForAccount(accountId: String): Flow<List<PendingTransferEntity>> {
//        return pendingDao.getActiveTransfersFlow().map { list ->
//            list.filter { it.fromAccountId == accountId }
//        }
//    }
//
//    override fun getPendingCount(): Flow<Int> {
//        return pendingDao.getActiveCount()
//    }
//
//    override suspend fun clearCompletedOperations() {
//        pendingDao.deleteCompleted()
//    }
//}