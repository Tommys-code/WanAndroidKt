package com.tommy.shen.module_home.repository

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tommy.shen.module_common.base.BaseRepository
import com.tommy.shen.module_home.SEARCH_HISTORY
import com.tommy.shen.module_home.data.BannerData
import com.tommy.shen.module_home.net.Instance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository : BaseRepository() {

    private val api = Instance.api

    suspend fun getBannerInfo(): List<BannerData> = request {
        Log.i("api",api.toString())
        api.getBanner()
    }

    suspend fun getArticleList(pageNum: Int) = request { api.getArticleList(pageNum) }

    suspend fun getHotKey() = request {
        Log.i("api",api.toString())
        api.getHotKey()
    }

    suspend fun queryArticle(pageNum: Int, key: String) = request { api.queryArticle(pageNum, key) }

    suspend fun getHistoryKey(): List<String>? {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                Moshi.Builder().build().adapter<List<String>>(
                    Types.newParameterizedType(List::class.java, String::class.java)
                ).fromJson(db.myDao().get(SEARCH_HISTORY)?.data_value ?: "")
            }.getOrNull()
        }
    }

    suspend fun saveHistoryKey(data: List<String>) {
        withContext(Dispatchers.IO) {
            db.myDao().save(
                SEARCH_HISTORY,
                Moshi.Builder().build().adapter<List<String>>(
                    Types.newParameterizedType(List::class.java, String::class.java)
                ).toJson(data)
            )
        }
    }

    suspend fun deleteHistoryKey() {
        withContext(Dispatchers.IO) { db.myDao().delete(SEARCH_HISTORY) }
    }

}