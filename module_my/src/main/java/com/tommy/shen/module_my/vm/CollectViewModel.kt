package com.tommy.shen.module_my.vm

import com.tommy.shen.module_common.base.BasePagingViewModel
import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_my.data.ArticleData
import com.tommy.shen.module_my.repo.MineRepository

class CollectViewModel : BasePagingViewModel<MineRepository, ArticleData>() {

    override fun getRepository() = MineRepository()

    override suspend fun getData(pageNum: Int): BaseListData<ArticleData>? =
        repo.getCollectList(pageNum)

}