package com.iscoding.common.models.dto


import kotlinx.serialization.Serializable

@Serializable
data class TopUpRequestDto(val amount: Double, val source: String)

@Serializable
data class TransactionResponseDto(
    val id: String,
    val status: String,
    val referenceNumber: String
)