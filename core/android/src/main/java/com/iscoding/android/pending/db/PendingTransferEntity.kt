package com.iscoding.android.pending.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.iscoding.android.pending.PendingStatus

@Entity(
    tableName = "pending_transfers",
    indices = [
        Index(value = ["idempotencyKey"], unique = true)
    ]
)
data class PendingTransferEntity(
    @PrimaryKey
    val id: String,
    val fromAccountId: String,
    val toAccountId: String,
    val amount: String,
    val currency: String,
    val reference: String?,

    // CRITICAL: Unique key for deduplication and replay attack prevention
    val idempotencyKey: String,

    val status: PendingStatus,
    val createdAt: Long,
    val retryCount: Int = 0,
    val lastError: String? = null
)