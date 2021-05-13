package com.tommy.shen.module_home.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tommy.shen.module_common.base.BasePagingViewModel
import com.tommy.shen.module_common.data.BaseListData
import com.tommy.shen.module_home.data.ArticleData
import com.tommy.shen.module_home.data.BannerData
import com.tommy.shen.module_home.repository.HomeRepository

class HomeViewModel : BasePagingViewModel<HomeRepository, ArticleData>() {

    private val bannerLiveData = MutableLiveData<List<BannerData>>()
    val collectLiveData = MutableLiveData<Int>()

    override fun getRepository() = HomeRepository()

    fun getBanner(): LiveData<List<BannerData>> {
        return bannerLiveData.launch { repo.getBannerInfo() }
    }

    override suspend fun getData(pageNum: Int): BaseListData<ArticleData>? {
        return repo.getArticleList(pageNum)
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