package com.iscoding.common.base

import com.iscoding.common.error.AppException
import com.iscoding.common.error.ErrorHandler
import com.iscoding.common.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseUseCase<Domain, in Body> : KoinComponent {
    protected val errorHandler: ErrorHandler by inject()

    protected fun requireBody(body: Body?): @UnsafeVariance Body =
        body ?: throw IllegalArgumentException("UseCase body is required and was null")

    protected abstract fun executeDS(body: Body? = null): Flow<Domain>

    // ── Change 1: Add <AppException> to the callback type ──
    operator fun invoke(
        scope: CoroutineScope,
        body: Body? = null,
        multipleInvoke: Boolean = false,
        onResult: (Resource<Domain, AppException>) -> Unit   // ← added , AppException
    ) {
        scope.launch(Dispatchers.Main) {
            if (multipleInvoke.not()) onResult(Resource.loading())  // ← removed .Companion

            runFlow(executeDS(body), onResult).collect {
                onResult(Resource.success(it))              // ← removed .Companion
                if (multipleInvoke.not()) onResult(Resource.loading(false))
            }
        }
    }

    // ── Change 2: Add <AppException> to the Flow return type ──
    operator fun invoke(
        body: Body? = null,
        multipleInvoke: Boolean = false,
    ): Flow<Resource<Domain, AppException>> =                 // ← added , AppException
        channelFlow {
            if (multipleInvoke.not()) send(Resource.loading())
            runFlow(executeDS(body)) {
                send(it)
            }.collect {
                send(Resource.success(it))
                if (multipleInvoke.not()) send(Resource.loading(false))
            }
        }

    // ── Change 3: Add <AppException> to the callback type ──
    fun <M> runFlow(
        requestExecution: Flow<M>,
        onResult: suspend (Resource<Domain, AppException>) -> Unit   // ← added , AppException
    ): Flow<M> = requestExecution.catch { e ->
        onResult(Resource.failure(errorHandler.getError(e)))    // ← removed .Companion
        onResult(Resource.loading(false))
    }.flowOn(Dispatchers.IO)
}