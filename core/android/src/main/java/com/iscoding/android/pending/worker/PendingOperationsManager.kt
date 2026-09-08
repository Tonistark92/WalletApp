package com.iscoding.android.pending.worker

import kotlinx.coroutines.flow.Flow

/**
 * Manages operations that need to be executed when internet is available.
 * Uses WorkManager for guaranteed execution with retry logic.
 */
interface PendingOperationsManager {

    /**
     * Queue an operation for later execution when internet returns.
     * Returns immediately with a pending ID for tracking.
     */
//    suspend fun queueTransfer(request: TransferRequest): String

    /**
     * Cancel a pending operation by its ID.
     */
    suspend fun cancelOperation(pendingId: String)

    /**
     * Retry a failed operation immediately.
     */
    suspend fun retryOperation(pendingId: String)

    /**
     * Observe all pending/failed operations.
     */
//    fun getPendingOperations(): Flow<List<PendingTransferEntity>>

    /**
     * Observe operations for a specific account.
     */
//    fun getPendingOperationsForAccount(accountId: String): Flow<List<PendingTransferEntity>>

    /**
     * Get count of pending operations (for badge/notification).
     */
    fun getPendingCount(): Flow<Int>

    /**
     * Clear all completed/failed operations.
     */
    suspend fun clearCompletedOperations()
}