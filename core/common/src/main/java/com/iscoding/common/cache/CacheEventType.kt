package com.iscoding.common.cache

enum class CacheEventType {
    BALANCE_CHANGED,      // Transfer, deposit, withdrawal
    PROFILE_UPDATED,      // User changed name, email, etc.
    SETTINGS_CHANGED,     // App settings modified
    SESSION_INVALIDATED,  // Token expired, logged out
    MANUAL_REFRESH        // User pulled to refresh
}