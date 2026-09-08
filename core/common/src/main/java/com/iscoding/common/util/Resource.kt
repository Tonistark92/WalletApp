package com.iscoding.common.util

import com.iscoding.common.error.AppException


sealed class Resource<out Model, out E : AppException> {

    data class Progress<out Model>(
        val loading: Boolean,
        val partialData: Model? = null
    ) : Resource<Model, Nothing>()

    data class Success<out Model>(
        val model: Model
    ) : Resource<Model, Nothing>()

    data class Failure<out E : AppException>(
        val exception: E
    ) : Resource<Nothing, E>()

    val isSuccess: Boolean get() = this is Success
    val isFailure: Boolean get() = this is Failure
    val isLoading: Boolean get() = this is Progress

    fun getOrNull(): Model? = (this as? Success)?.model
    fun exceptionOrNull(): E? = (this as? Failure)?.exception

    inline fun onSuccess(action: (Model) -> Unit):Resource<Model, E> {
        if (this is Success) action(model)
        return this
    }

    inline fun onFailure(action: (E) -> Unit): Resource<Model, E> {
        if (this is Failure) action(exception)
        return this
    }

    inline fun onLoading(action: (Boolean) -> Unit):Resource<Model, E> {
        if (this is Progress) action(loading)
        return this
    }

    inline fun <T> map(transform: (Model) -> T): Resource<T, E> = when (this) {
        is Success -> Success(transform(model))
        is Failure -> this
        is Progress -> Progress(loading, partialData?.let(transform))
    }

    inline fun <T : AppException> mapError(transform: (E) -> T): Resource<Model, T> = when (this) {
        is Success -> this
        is Progress -> this
        is Failure -> Failure(transform(exception))
    }

    companion object {
        fun <Model> loading(
            loading: Boolean = true,
            partialData: Model? = null
        ): Resource<Model, Nothing> = Progress(loading, partialData)

        fun <Model> success(model: Model): Resource<Model, Nothing> = Success(model)

        fun <E : AppException> failure(exception: E): Resource<Nothing, E> = Failure(exception)
    }
}