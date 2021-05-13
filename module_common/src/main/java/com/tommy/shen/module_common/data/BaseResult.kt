package com.tommy.shen.module_common.data

data class BaseResult<T>(
    val data: T?,
    val errorCode: Int,
    val errorMsg: String
)