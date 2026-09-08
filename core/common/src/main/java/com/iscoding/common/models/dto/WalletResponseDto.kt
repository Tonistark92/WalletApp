package com.iscoding.common.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class WalletResponseDto(
    val id: String,
    val userId: String,
    val balance: Double,
    val currency: String,
    val status: String,
    val dailyLimit: Double,
    val monthlyLimit: Double
)