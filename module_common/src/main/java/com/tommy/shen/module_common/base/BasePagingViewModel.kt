package com.tommy.shen.module_common.base

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.tommy.shen.module_common.data.BaseListData

abstract class BasePagingViewModel<T : BaseRepository, Value : Any> : BaseViewModel<T>() {

    open val pageIndex = 0

    abstract suspend fun getData(pageNum: Int): BaseListData<Value>?

    val listData = getPager().flow.cachedIn(viewModelScope)

    private fun getPager() = Pager(PagingConfig(pageSize = 6)) {
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
                    val prevPage = if (currentPage > 0) currentPage - 1 else null
                    if (demoReqData != null) {
                        LoadResult.Page(
                            data = demoReqData.datas,
                            prevKey = prevPage,
                            nextKey = nextPage
                        )
                    } else {
                        LoadResult.Error(throwable = Throwable())
                    }
                } catch (e: Exception) {
                    LoadResult.Error(throwable = e)
                }
            }

            override fun getRefreshKey(state: PagingState<Int, Value>): Int? = null
        }
    }

}