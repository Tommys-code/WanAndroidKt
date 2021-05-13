package com.tommy.shen.module_public.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tommy.shen.module_common.base.BasePagingViewModel
import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_public.data.ArticleData
import com.tommy.shen.module_public.data.WxArticleData
import com.tommy.shen.module_public.repo.PublicRepository

class PublicViewModel : BasePagingViewModel<PublicRepository, ArticleData>() {

    var id = 0
    val collectLiveData = MutableLiveData<Int>()

    override fun getRepository() = PublicRepository()

    override suspend fun getData(pageNum: Int): BaseListData<ArticleData>? {
        return repo.getArticleById(id, pageNum)
    }

    private val chaptersLiveData = MutableLiveData<List<WxArticleData>>()

    fun getWxArticle(): LiveData<List<WxArticleData>> {
        return chaptersLiveData.launch { repo.getWxArticle() }
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