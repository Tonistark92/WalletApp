package com.iscoding.common.models.dto


import kotlinx.serialization.Serializable


@Serializable
data class BankTransferRequestDto(
    val accountNumber: String,
    val bankCode: String,
    val amount: Double
)