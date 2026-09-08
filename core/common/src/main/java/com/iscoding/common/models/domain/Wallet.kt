package com.iscoding.common.models

import com.wallet.core.model.WalletBalance

data class Wallet(
    val id: String,
    val userId: String,
    val balance: WalletBalance,
    val currency: String = "EGP",
    val status: WalletStatus,
    val dailyLimit: Double,
    val monthlyLimit: Double
)

enum class WalletStatus { ACTIVE, FROZEN, SUSPENDED }