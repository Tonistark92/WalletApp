package com.iscoding.common.base

import com.google.gson.annotations.SerializedName
// add here the replicated fields in the other dto and put them here make them inh from it
open class BaseDto(
    @SerializedName("message") var message: String? = null,
    @SerializedName("code") var code: String? = null
)