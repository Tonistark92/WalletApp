package com.iscoding.android.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> Flow<T>.collectSingleEvent(
    key: Any? = true,
    onEvent: suspend (T) -> Unit
) {
    LaunchedEffect(key) {
        collectLatest { onEvent(it) }
    }
}