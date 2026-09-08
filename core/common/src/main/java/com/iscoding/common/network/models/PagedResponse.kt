package com.wallet.core.network

/** Standard pagination envelope for list endpoints (transaction history, beneficiaries, etc). */
data class PagedResponse<T>(
    val items: List<T>,
    val page: Int,
    val pageSize: Int,
    val totalItems: Long,
    val totalPages: Int
)
