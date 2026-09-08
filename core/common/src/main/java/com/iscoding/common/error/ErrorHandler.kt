package com.iscoding.common.error

interface ErrorHandler {
    fun getError(throwable: Throwable): AppException
}