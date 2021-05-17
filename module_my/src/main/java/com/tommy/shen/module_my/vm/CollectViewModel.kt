package com.tommy.shen.module_my.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.tommy.shen.module_common.base.BasePagingViewModel
import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_my.data.ArticleData
import com.tommy.shen.module_my.repo.MineRepository
import kotlinx.coroutines.flow.*

class CollectViewModel : BasePagingViewModel<MineRepository, ArticleData>() {

    override fun getRepository() = MineRepository()

    val unCollectLiveData = MutableLiveData<Int>()

    override val listData: Flow<PagingData<ArticleData>>
        get() = getPager().flow.map { pagingData ->
            pagingData.filter { it.collect }
        }.cachedIn(viewModelScope)

    override suspend fun getData(pageNum: Int): BaseListData<ArticleData>? =repo.getCollectList(pageNum)

    fun unCollectArticle(id: Int, position: Int) = unCollectLiveData.launch {
        repo.unCollectArticle(id)
        position
    }

}