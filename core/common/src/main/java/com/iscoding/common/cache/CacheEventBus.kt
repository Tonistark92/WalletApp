package com.iscoding.common.cache

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class CacheEventBus{

    // SharedFlow: hot stream, broadcasts to ALL collectors
    // replay = 0: new subscribers don't get old events
    // extraBufferCapacity = 10: buffer events if collectors are slow
    private val _events = MutableSharedFlow<CacheEvent>(
        replay = 0,
        extraBufferCapacity = 10,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val events = _events.asSharedFlow()

    // Emit an event — any repository can call this
    suspend fun emit(event: CacheEvent) {
        _events.emit(event)
    }

    // Convenience method for simple event types
    suspend fun emit(eventType: CacheEventType) {
        _events.emit(CacheEvent(type = eventType, timestamp = System.currentTimeMillis()))
    }
}

data class CacheEvent(
    val type: CacheEventType,
    val timestamp: Long = System.currentTimeMillis(),
    val payload: Map<String, Any> = emptyMap()  // Optional: account IDs, etc.
)
