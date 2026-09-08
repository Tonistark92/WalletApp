package com.iscoding.common.cache

class RefreshControl(
    private val strategy: CacheStrategy
) {
    // ─── STATE ───
    @Volatile
    private var lastRefreshTime: Long? = null

    @Volatile
    private var lastEventTimestamp: Map<CacheEventType, Long> = emptyMap()

    private val listeners = mutableListOf<EventListener>()

    // ─── LISTENER INTERFACE ───
    interface EventListener {
        suspend fun cleanup()
    }

    fun addListener(listener: EventListener) {
        listeners.add(listener)
    }

    // ─── PUBLIC API: Check if cache is valid ───
    fun isValid(): Boolean = !isExpired()

    fun isExpired(): Boolean {
        return when (strategy) {
            is CacheStrategy.TimeBased -> isTimeExpired()
            is CacheStrategy.EventBased -> isEventExpired(strategy.eventType)
            is CacheStrategy.Combined -> isTimeExpired() || isEventExpired(strategy.eventStrategy.eventType)
        }
    }

    // ─── PUBLIC API: Mark cache as fresh ───
    fun refresh() {
        lastRefreshTime = System.currentTimeMillis()
    }

    // ─── PUBLIC API: Soft eviction (mark expired) ───
   private fun evict() {
        lastRefreshTime = null
    }

    // ─── PUBLIC API: Hard eviction (mark expired + cleanup) ───
    suspend fun evict(cleanup: Boolean = false) {
        evict()
        if (cleanup) {
            listeners.forEach { it.cleanup() }
        }
    }

    // ─── EVENT API: Notify that an event occurred ───
    fun notifyEvent(eventType: CacheEventType) {
        lastEventTimestamp = lastEventTimestamp.toMutableMap().apply {
            put(eventType, System.currentTimeMillis())
        }
    }

    // ─── PRIVATE: Time-based check ───
    private fun isTimeExpired(): Boolean {
        val duration = when (strategy) {
            is CacheStrategy.TimeBased -> strategy.validDuration
            is CacheStrategy.Combined -> strategy.timeStrategy.validDuration
            else -> return true  // No time strategy = always expired
        }

        val last = lastRefreshTime ?: return true  // Never refreshed = expired
        val elapsed = System.currentTimeMillis() - last
        return elapsed > duration.inWholeMilliseconds
    }

    // ─── PRIVATE: Event-based check ───
    private fun isEventExpired(eventType: CacheEventType): Boolean {
        // If no refresh ever happened, it's expired
        val lastRefresh = lastRefreshTime ?: return true

        // If event never occurred, cache is valid (no invalidation)
        val lastEvent = lastEventTimestamp[eventType] ?: return false

        // Event occurred AFTER last refresh? → Cache is stale!
        return lastEvent > lastRefresh
    }

}