package com.iscoding.common.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class P2PTransferRequestDto(
    val receiverPhone: String,
    val amount: Double,
    val note: String?
)
