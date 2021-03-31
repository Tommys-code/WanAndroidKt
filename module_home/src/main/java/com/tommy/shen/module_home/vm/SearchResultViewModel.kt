package com.tommy.shen.module_home.vm

import com.tommy.shen.module_common.base.BasePagingViewModel
import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_home.data.ArticleData
import com.tommy.shen.module_home.repository.HomeRepository

class SearchResultViewModel : BasePagingViewModel<HomeRepository, ArticleData>() {

    var queryKey: String = ""

    override fun getRepository() = HomeRepository()

    override suspend fun getData(pageSize: Int): BaseListData<ArticleData>? {
        return repo.queryArticle(pageSize, queryKey)
    }

}