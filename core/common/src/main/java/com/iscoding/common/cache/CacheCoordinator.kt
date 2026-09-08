package com.iscoding.common.cache

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CacheCoordinator<in Input, out Output>(
    private val remoteFetch: suspend (Input) -> Output?,
    private val localFetch: suspend (Input) -> Output?,
    private val localStore: suspend (Output) -> Unit,
    private val localDelete: suspend () -> Unit,
    private val refreshControl: RefreshControl,
    private val eventBus: CacheEventBus? = null,
    private val subscribedEvents: Set<CacheEventType> = emptySet(),
    // NEW: Conflict resolver — server always wins
    private val conflictResolver: ConflictResolver<Output>? = null
) : RefreshControl.EventListener {
    // NEW: Coroutine scope for background event listening
    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.Default
    )

    init {
        refreshControl.addListener(this)
        subscribeToEvents() // ← Auto-subscribe on creation
    }

    /**
     * AUTO-SUBSCRIBE: Listen for events that invalidate this cache
     */
    private fun subscribeToEvents() {
        // Skip if no event bus or no events specified
        if (eventBus == null || subscribedEvents.isEmpty()) return

        scope.launch {
            eventBus.events
                // FILTER: Only events I care about
                .filter { event ->  event.type in subscribedEvents  }
                .collect { event ->
                    // When matching event occurs, mark cache as expired
                    refreshControl.notifyEvent(event.type)

                    // Example timeline:
                    // T=0: refreshControl.refresh() → cache valid
                    // T=5: eventBus.emit(BALANCE_CHANGED)
                    //      → refreshControl.notifyEvent(BALANCE_CHANGED)
                    //      → lastEventTimestamp[BALANCE_CHANGED] = T=5
                    //
                    // Next query() at T=10:
                    //      isEventExpired() checks:
                    //      lastEvent (T=5) > lastRefresh (T=0)? YES!
                    //      → Cache EXPIRED → fetch from remote
                }
        }
    }


    // ─── MAIN QUERY ───
     fun query(args: Input, force: Boolean = false): Flow<Output?> = flow {

        // Step 1: Emit cache if available (stale-while-revalidate)
        if (!force) {
            val cached = fetchFromLocal(args)
            if (cached != null) {
                emit(cached)
            }
        }

        // Step 2: Check if we need remote fetch
        if (refreshControl.isExpired() || force) {
            val remote = fetchFromRemote(args)
            emit(remote)
        }
    }

//    // ─── EVENT NOTIFICATION ───
//    // Call this when an event invalidates cache!
//    fun notifyEvent(eventType: CacheEventType) {
//        refreshControl.notifyEvent(eventType)
//    }

    // ─── EVICTION ───
    suspend fun evict(cleanup: Boolean = false) {
        refreshControl.evict(cleanup)
    }

    // ─── LISTENER IMPLEMENTATION ───
    override suspend fun cleanup() {
        deleteLocal()
    }

    // ─── PRIVATE ───
    private suspend fun fetchFromLocal(args: Input): Output? {
        return try {
            localFetch(args)
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun fetchFromRemote(args: Input): Output? {
        return try {
            remoteFetch(args)?.also { remoteData ->
                try {
                    // Check for conflicts before storing
                    val localData = fetchFromLocal(args)

                    if (localData != null && conflictResolver != null) {
                        val resolution = conflictResolver.resolve(localData, remoteData)

                        when (resolution) {
                            is ConflictResolution.ServerWins -> {
                                // Overwrite local with server data
                                localStore(remoteData)
                            }
                            is ConflictResolution.Merge -> {
                                // Use merged data (rare in fintech)
                                localStore(resolution.merged)
                            }

                            else -> {
                                //TODO: Fill This
                            }
                        }
                    } else {
                        // No local data or no resolver — just store
                        localStore(remoteData)
                    }

                    refreshControl.refresh()

                } catch (e: Exception) {
                    // Log: cache save failed, but we still have the data
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }
    suspend fun dispose() {
        scope.cancel()
        refreshControl.evict(false)
    }
    private suspend fun deleteLocal() {
        try {
            localDelete()
        } catch (e: Exception) {
            // Log: cleanup failed
        }
    }
}