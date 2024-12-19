package com.azzam.cryptocurrency.currencies.data.api.dto


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val error: String?,
    @SerializedName("timestamp")
    val timestamp: Long?
)