package com.iscoding.common.models.network

data class RemoteRequest(
    val requestBody: HashMap<String, Any> = hashMapOf(),
    val requestQueries: HashMap<String, Any> = hashMapOf(),
    val requestHeaders: HashMap<String, Any> = hashMapOf()
)