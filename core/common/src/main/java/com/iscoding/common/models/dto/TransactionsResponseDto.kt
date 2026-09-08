package com.iscoding.common.models.dto

import kotlinx.serialization.Serializable


@Serializable
data class TransactionsResponseDto(val transactions: List<TransactionDto>)

@Serializable
data class TransactionDto(
    val id: String,
    val type: String,
    val amount: Double,
    val currency: String,
    val senderId: String?,
    val receiverId: String?,
    val description: String?,
    val status: String,
    val createdAt: String,
    val referenceNumber: String
)
