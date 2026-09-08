package com.iscoding.common.models

import java.time.Instant

data class Card(
    val id: String,
    val userId: String,
    val cardNumber: String,
    val cardHolderName: String,
    val expiryDate: String,
    val cvv: String,
    val cardType: CardType,
    val scheme: CardScheme,
    val isVirtual: Boolean,
    val isActive: Boolean,
    val createdAt: Instant
)

enum class CardType { DEBIT, PREPAID }
enum class CardScheme { MEEZA, VISA, MASTERCARD }