package com.tommy.shen.module_common.http

import java.lang.Exception

data class NetErrorException(val errorCode: Int, val msg: String) : Exception()
