package com.tommy.shen.module_common.base

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.tommy.shen.module_common.data.BaseListData

abstract class BasePagingViewModel<T : BaseRepository, Value : Any> : BaseViewModel<T>() {

    open val pageIndex = 0

    abstract suspend fun getData(pageSize: Int): BaseListData<Value>?

    val listData = Pager(PagingConfig(pageSize = 20)) {
        object : PagingSource<Int, Value>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
                return try {
                    //页码未定义置为0
                    val currentPage = params.key ?: pageIndex
                    //仓库层请求数据
                    val demoReqData = getData(currentPage)

                    //当前页码 小于 总页码 页面加1
                    val nextPage = if (demoReqData?.curPage ?: 0 < demoReqData?.pageCount ?: 0) {
                        currentPage + 1
                    } else {
                        //没有更多数据
                        null
                    }
                    if (demoReqData != null) {
                        LoadResult.Page(
                            data = demoReqData.datas,
                            prevKey = null,
                            nextKey = nextPage
                        )
                    } else {
                        LoadResult.Error(throwable = Throwable())
                    }
                } catch (e: Exception) {
                    LoadResult.Error(throwable = e)
                }
            }
        }
    }.flow.cachedIn(viewModelScope)

}