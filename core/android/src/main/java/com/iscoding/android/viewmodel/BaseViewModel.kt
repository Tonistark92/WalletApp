package com.wallet.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iscoding.android.AppLogger
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base class for simple, single-state screens (MVVM style).
 * Use for screens without discrete one-off intents/effects — the state is
 * read and mutated directly (e.g. Login: email/password/loading/error).
 */
abstract class WalletAppViewModel<State, Event>(
    initialState: State
) : ViewModel() {

    // Holds the current state for the UI
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    // Emits one-time events (navigation, snackbars, etc.)
    private val eventChannel = Channel<Event>(Channel.UNLIMITED)
    val singleEvent: Flow<Event> = eventChannel.receiveAsFlow()

    protected val currentState: State
        get() = _state.value

    protected fun setState(reducer: State.() -> State) {
        _state.value = currentState.reducer()
        AppLogger.d("Updated state: ${_state.value}")
    }

    protected fun sendEvent(event: Event) {
        AppLogger.d("Emitting single event: $event")
        eventChannel.trySend(event)
    }

    protected fun launch(block: suspend () -> Unit) {
        viewModelScope.launch {
            block()
        }
    }

    override fun onCleared() {
        super.onCleared()
        AppLogger.d("ViewModel cleared")
        eventChannel.close()
    }
}