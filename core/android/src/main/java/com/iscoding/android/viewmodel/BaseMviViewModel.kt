package com.iscoding.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iscoding.common.mvi.ViewAction
import com.iscoding.common.mvi.ViewEvent
import com.iscoding.common.mvi.ViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class WalletAppMviViewModel<A : ViewAction, E : ViewEvent, S : ViewState>(
    private val initialState: S
) : ViewModel() {

    // Holds the current state for the UI
    private val _viewState = MutableStateFlow(initialState)
    val viewState: StateFlow<S> = _viewState.asStateFlow()

    // Emits one-time events (navigation, snackbars, etc.)
    private val eventChannel = Channel<E>(Channel.UNLIMITED)
    val singleEvent: Flow<E> = eventChannel.receiveAsFlow()

    // Accepts actions from the UI
    private val _actionFlow = MutableSharedFlow<A>(extraBufferCapacity = Int.MAX_VALUE)
    private val actionFlow: SharedFlow<A> get() = _actionFlow

    val currentViewState: S
        get() = viewState.value

    init {
        observeActions(actionFlow) { onActionTrigger(it) }
    }

    fun processIntent(action: A) {
        AppLogger.d("Received action: $action")
        check(_actionFlow.tryEmit(action)) { "Failed to emit action: $action" }
    }

    protected fun sendEvent(event: E) {
        AppLogger.d("Emitting single event: $event")
        eventChannel.trySend(event)
    }

    fun setState(newState: S) {
        _viewState.value = newState
        AppLogger.d("New state set: $newState")
    }

    protected fun updateState(transform: S.() -> S) {
        _viewState.value = currentViewState.transform()
        AppLogger.d("Updated state: ${_viewState.value}")
    }

    private fun clearState() {
        setState(initialState)
        AppLogger.d("State reset to initial")
    }

    private fun <F : SharedFlow<A>> observeActions(flow: F, onAction: (A) -> Unit) {
        viewModelScope.launch {
            flow.collect { action ->
                onAction(action)
            }
        }
    }

    protected abstract fun onActionTrigger(action: A)

    override fun onCleared() {
        super.onCleared()
        AppLogger.d("ViewModel cleared")
        eventChannel.close()
        clearState()
    }
}