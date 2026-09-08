package com.iscoding.android.pending.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface PendingTransferDao {

    @Query("SELECT * FROM pending_transfers WHERE status IN ('PENDING', 'PROCESSING') ORDER BY createdAt DESC")
    fun getActiveTransfersFlow(): Flow<List<PendingTransferEntity>>

    @Query("SELECT * FROM pending_transfers WHERE status IN ('PENDING', 'PROCESSING')")
    suspend fun getActiveTransfers(): List<PendingTransferEntity>

    @Query("SELECT * FROM pending_transfers WHERE id = :id")
    suspend fun getById(id: String): PendingTransferEntity?

    // NEW: Check if operation with same idempotency key exists
    @Query("SELECT * FROM pending_transfers WHERE idempotencyKey = :key LIMIT 1")
    suspend fun getByIdempotencyKey(key: String): PendingTransferEntity?

    // NEW: Get completed/failed operations
    @Query("SELECT * FROM pending_transfers WHERE status IN ('COMPLETED', 'FAILED')")
    suspend fun getCompletedOrFailed(): List<PendingTransferEntity>

    @Insert
    suspend fun insert(transfer: PendingTransferEntity)

    @Update
    suspend fun update(transfer: PendingTransferEntity)

    @Delete
    suspend fun delete(transfer: PendingTransferEntity)

    @Query("DELETE FROM pending_transfers WHERE id = :id")
    suspend fun deleteById(id: String)

    // NEW: Delete completed/failed operations
    @Query("DELETE FROM pending_transfers WHERE status IN ('COMPLETED', 'FAILED')")
    suspend fun deleteCompleted()

    // NEW: Delete by idempotency key (for cleanup)
    @Query("DELETE FROM pending_transfers WHERE idempotencyKey = :key")
    suspend fun deleteByIdempotencyKey(key: String)

    // NEW: Count active operations
    @Query("SELECT COUNT(*) FROM pending_transfers WHERE status IN ('PENDING', 'PROCESSING')")
    fun getActiveCount(): Flow<Int>
}