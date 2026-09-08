package com.iscoding.common.models

import java.time.Instant

data class Transaction(
    val id: String,
    val type: TransactionType,
    val amount: Double,
    val currency: String,
    val senderId: String?,
    val receiverId: String?,
    val description: String?,
    val status: TransactionStatus,
    val createdAt: Instant,
    val referenceNumber: String
)

enum class TransactionType {
    P2P_SEND, P2P_RECEIVE, TOP_UP, CASH_OUT, CARD_PAYMENT, REFUND
}

enum class TransactionStatus { PENDING, COMPLETED, FAILED, REVERSED }