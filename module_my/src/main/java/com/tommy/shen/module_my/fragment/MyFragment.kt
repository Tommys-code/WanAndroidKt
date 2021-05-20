package com.tommy.shen.module_my.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV
import com.tommy.shen.module_common.base.BaseFragment
import com.tommy.shen.module_common.constant.*
import com.tommy.shen.module_common.util.isLogin
import com.tommy.shen.module_my.R
import com.tommy.shen.module_my.databinding.FragMyBinding
import com.tommy.shen.module_my.vm.CoinViewModel

@Route(path = Mine.MINE_PAGE)
class MyFragment : BaseFragment<FragMyBinding>(), View.OnClickListener {

    private val userKv by lazy { MMKV.mmkvWithID(USER) }
    private val viewModel by viewModels<CoinViewModel>()

    override fun getLayoutId(): Int = R.layout.frag_my

    override fun onCreate() {
        binding.onClick = this

        initData()
        LiveEventBus.get<Boolean>(LOGIN).observe(this, Observer { initData() })
        viewModel.coinLiveData.observe(this, Observer {
            binding.coin = it.coinCount
        })
    }

    private fun initData() {
        binding.name = userKv?.decodeString(USER_NAME, getString(R.string.my_not_login))
            ?: getString(R.string.my_not_login)
        if (isLogin()) viewModel.getCoin()
        else binding.coin = -1
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageView3, R.id.name_tv ->
                if (!isLogin(userKv?.decodeInt(USER_ID, -1))) Login.openLogin()
            R.id.my_score -> ARouter.getInstance().build(Mine.MY_SCORE).navigation()
            R.id.my_collect -> ARouter.getInstance().build(Mine.MY_COLLECT).navigation()
            R.id.setting -> ARouter.getInstance().build(Mine.MY_SETTING).navigation()
        }
    }
}