package com.tommy.shen.module_my.data

import com.tommy.shen.module_common.util.getDateFormat

data class CoinListData(
    val coinCount: Int,
    val id: Long,
    val date: Long,
    val desc: String,
    val reason: String
){

    fun getDate():String = getDateFormat(date)

}