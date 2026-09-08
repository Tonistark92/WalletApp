package com.wallet.core.model

data class WalletBalance(
    val amount: Double,
    val currency: String = "EGP"
)
