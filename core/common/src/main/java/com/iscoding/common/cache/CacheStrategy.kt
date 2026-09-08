package com.iscoding.common.cache

import kotlin.time.Duration

sealed class CacheStrategy {

    // Time-based: cache expires after duration
    data class TimeBased(
        val validDuration: Duration
    ) : CacheStrategy()

    // Event-based: cache expires when specific event occurs
    data class EventBased(
        val eventType: CacheEventType
    ) : CacheStrategy()

    // Combined: both time AND event can invalidate
    data class Combined(
        val timeStrategy: TimeBased,
        val eventStrategy: EventBased
    ) : CacheStrategy()
}
