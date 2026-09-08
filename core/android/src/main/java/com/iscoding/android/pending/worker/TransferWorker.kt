package com.iscoding.android.pending.worker

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.iscoding.android.pending.PendingStatus
import com.iscoding.android.pending.TransferNotificationManager
import com.iscoding.android.pending.db.PendingTransferDao
import com.iscoding.common.cache.CacheEventBus
import java.util.concurrent.TimeUnit

//class TransferWorker(
//    context: Context,
//    params: WorkerParameters,
//    private val api: BankingApi,
//    private val pendingDao: PendingTransferDao,
//    private val eventBus: CacheEventBus,
//    private val mapper: TransferMapper,
//    private val notificationManager: TransferNotificationManager
//
//) : CoroutineWorker(context, params) {
//
//    companion object {
//        const val KEY_TRANSFER_ID = "transfer_id"
//
//        fun schedule(context: Context, transferId: String) {
//            val constraints = Constraints.Builder()
//                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .build()
//
//            val inputData = workDataOf(KEY_TRANSFER_ID to transferId)
//
//            val workRequest = OneTimeWorkRequestBuilder<TransferWorker>()
//                .setConstraints(constraints)
//                .setInputData(inputData)
//                .setBackoffCriteria(
//                    BackoffPolicy.EXPONENTIAL,
//                    10_000L,
//                    TimeUnit.MILLISECONDS
//                )
//                .build()
//
//            WorkManager.getInstance(context).enqueueUniqueWork(
//                "transfer_$transferId",
//                ExistingWorkPolicy.KEEP,
//                workRequest
//            )
//        }
//    }
//
//
//    override suspend fun doWork(): Result {
//        val transferId = inputData.getString(KEY_TRANSFER_ID) ?: return Result.failure()
//
//        val pending = pendingDao.getById(transferId) ?: return Result.failure()
//
//        // Already completed? Don't reprocess (prevents replay)
//        if (pending.status == PendingStatus.COMPLETED) {
//            return Result.success()
//        }
//        notificationManager.showTransferProcessing(
//            amount = "${pending.currency} ${pending.amount}",
//            toAccount = pending.toAccountId
//        )
//
//        pendingDao.update(pending.copy(status = PendingStatus.PROCESSING))
//
//        return try {
//            val requestDto = TransferRequestDto(
//                fromAccountId = pending.fromAccountId,
//                toAccountId = pending.toAccountId,
//                amount = pending.amount,
//                currency = pending.currency,
//                reference = pending.reference,
//                // CRITICAL: Send same idempotency key to server
//                idempotencyKey = pending.idempotencyKey
//            )
//
//            api.createTransfer(requestDto, "My Key")
//
//            // Success! Mark completed
//            pendingDao.update(
//                pending.copy(status = PendingStatus.COMPLETED)
//            )
//
//            // Don't delete immediately — keep for deduplication checks
//            // Clean up later via clearCompletedOperations()
//            notificationManager.showTransferSuccess(
//                amount = "${pending.currency} ${pending.amount}",
//                toAccount = pending.toAccountId,
//                transactionId = transferId
//            )
//            eventBus.emit(CacheEventType.BALANCE_CHANGED)
//
//            Result.success()
//
//        } catch (e: Exception) {
//            val updated = pending.copy(
//                retryCount = pending.retryCount + 1,
//                lastError = e.message,
//                status = if (pending.retryCount >= 4) PendingStatus.FAILED else PendingStatus.PENDING
//            )
//            pendingDao.update(updated)
//
//            if (updated.status == PendingStatus.FAILED){
//                // Show failure notification
//                notificationManager.showTransferFailed(
//                    amount = "${pending.currency} ${pending.amount}",
//                    toAccount = pending.toAccountId,
//                    error = e.message ?: "Unknown error"
//                )
//                Result.failure()
//            } else {
//                Result.retry()
//            }
//        }
//    }
//}
//
//class TransferWorkerFactory(
//    private val api: BankingApi,
//    private val pendingDao: PendingTransferDao,
//    private val eventBus: CacheEventBus,
//    private val mapper: TransferMapper,
//    private val notificationManager: TransferNotificationManager,
//
//) : WorkerFactory() {
//
//
//
//    override fun createWorker(
//        appContext: Context,
//        workerClassName: String,
//        workerParameters: WorkerParameters
//    ): ListenableWorker? {
//        return TransferWorker(
//            context = appContext,
//            params = workerParameters,
//            api = api,
//            pendingDao = pendingDao,
//            eventBus = eventBus,
//            mapper = mapper,
//            notificationManager = notificationManager
//        )    }
//}