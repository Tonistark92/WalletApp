package com.iscoding.common.cache


sealed class ConflictResolution<out T> {
    data class ServerWins<T>(val data: T) : ConflictResolution<T>()
    data class Merge<T>(val merged: T) : ConflictResolution<T>()
    // NOT used in fintech it is done with
    data class LocalWins<T>(val data: T) : ConflictResolution<T>()
}


//// For AccountBalance — always server wins
//class BalanceConflictResolver : ConflictResolver<AccountBalance> {
//    override fun resolve(local: AccountBalance, remote: AccountBalance): ConflictResolution<AccountBalance> {
//        // Fintech rule: Server is the single source of truth
//        // Local is just a cache, never authoritative
//
//        return ConflictResolution.ServerWins(remote)
//    }
//}

// For complex objects with timestamps
class TimestampConflictResolver<T : Timestamped> : ConflictResolver<T> {
    override fun resolve(local: T, remote: T): ConflictResolution<T> {
        // If server has newer timestamp, server wins
        // If same timestamp, server still wins (server is truth)
        return ConflictResolution.ServerWins(remote)
    }
}


interface ConflictResolver<T> {
    fun resolve(local: T, remote: T): ConflictResolution<T>
}

interface Timestamped {
    val serverTimestamp: Long
}