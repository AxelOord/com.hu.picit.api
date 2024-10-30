package com.hu.picit.app.model

data class ApiResponse<T>(
    val message: String,
    val status: Int,
    val data: List<DataWrapper<T>>
)

data class DataWrapper<T>(
    val type: String,
    val id: Int,
    val attributes: T
)