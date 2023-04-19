package com.rahul.data_module.models.response

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("error")
    var error: String?,
)