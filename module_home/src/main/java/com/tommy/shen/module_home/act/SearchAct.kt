package com.tommy.shen.module_home.act

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.tommy.shen.module_common.base.BaseActivity
import com.tommy.shen.module_common.constant.Home
import com.tommy.shen.module_common.util.ToastUtils
import com.tommy.shen.module_home.R
import com.tommy.shen.module_home.adapter.SearchKeyAdapter
import com.tommy.shen.module_home.databinding.ActSearchBinding
import com.tommy.shen.module_home.vm.SearchKeyViewModel


@Route(path = Home.SEARCH_PAGE)
class SearchAct : BaseActivity<ActSearchBinding>(), View.OnClickListener {

    private val viewModel by viewModels<SearchKeyViewModel>()
    private val hotAdapter by lazy {
        SearchKeyAdapter().apply { setKeyClickListener { search(it) } }
    }
    private val historyAdapter by lazy {
        SearchKeyAdapter().apply { setKeyClickListener { search(it) } }
    }

    override fun getLayoutId(): Int = R.layout.act_search

    override fun onCreate() {
        binding.onClick = this

        binding.etSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 当按了搜索
                search(v.text.toString())
                true
            }
            false
        }

        val managerHot = binding.hotRecycle.layoutManager as FlexboxLayoutManager
        managerHot.flexDirection = FlexDirection.ROW
        managerHot.flexWrap = FlexWrap.WRAP
        managerHot.alignItems = AlignItems.STRETCH
        binding.hotRecycle.adapter = hotAdapter

        val managerHistory = binding.historyRecycle.layoutManager as FlexboxLayoutManager
        managerHistory.flexDirection = FlexDirection.ROW
        managerHistory.flexWrap = FlexWrap.WRAP
        managerHistory.alignItems = AlignItems.STRETCH
        binding.historyRecycle.adapter = historyAdapter

        viewModel.getHotKey().observe(this, Observer {
            hotAdapter.submitList(it.map { data -> data.name })
        })
        viewModel.getHistoryKey().observe(this, Observer {
            binding.hasHistory = !it.isNullOrEmpty()
            historyAdapter.submitList(it)
        })
    }

    private fun search(key: String?) {
        if (key.isNullOrEmpty()) {
            ToastUtils.showToast(R.string.home_search_empty)
            return
        }
        binding.etSearch.setText(key)
        binding.etSearch.setSelection(key.length)
        ARouter.getInstance().build(Home.SEARCH_RESULT_PAGE).withString("key", key).navigation()
        viewModel.saveHistoryKey(key)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ic_back -> finish()
            R.id.history_clear -> viewModel.deleteHistory()
        }
    }

}