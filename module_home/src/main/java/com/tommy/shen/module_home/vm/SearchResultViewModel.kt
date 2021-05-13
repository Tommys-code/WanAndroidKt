package com.tommy.shen.module_home.vm

import androidx.lifecycle.MutableLiveData
import com.tommy.shen.module_common.base.BasePagingViewModel
import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_home.data.ArticleData
import com.tommy.shen.module_home.repository.HomeRepository

class SearchResultViewModel : BasePagingViewModel<HomeRepository, ArticleData>() {

    var queryKey: String = ""
    val collectLiveData = MutableLiveData<Int>()

    override fun getRepository() = HomeRepository()

    override suspend fun getData(pageSize: Int): BaseListData<ArticleData>? {
        return repo.queryArticle(pageSize, queryKey)
    }

    fun collectArticle(id: Int, collect: Boolean, position: Int) =
        collectLiveData.launch {
            if (collect) {
                repo.unCollectArticle(id)
                position
            } else {
                repo.collectArticle(id)
                position
            }
        }

}