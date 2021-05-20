package com.tommy.shen.module_my.act

import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tommy.shen.module_common.base.BaseActivity
import com.tommy.shen.module_common.constant.LOGIN
import com.tommy.shen.module_common.constant.Login
import com.tommy.shen.module_common.constant.Mine
import com.tommy.shen.module_common.util.isLogin
import com.tommy.shen.module_my.R
import com.tommy.shen.module_my.databinding.ActSettingBinding
import com.tommy.shen.module_my.vm.LogoutViewModel

@Route(path = Mine.MY_SETTING)
class SettingAct : BaseActivity<ActSettingBinding>() {

    private val viewModel by viewModels<LogoutViewModel>()

    override fun getLayoutId(): Int = R.layout.act_setting

    override fun onCreate() {
        binding.toolbar.init(getString(R.string.my_setting))
        binding.isLogin = isLogin()

        binding.version = packageManager.getPackageInfo(packageName, 0).versionName

        binding.btn.setOnClickListener {
            if (binding.isLogin == true) {
                dialog.show()
            } else {
                Login.openLogin()
                finish()
            }
        }

        viewModel.logoutLiveData.observe(this, Observer {
            LiveEventBus.get<Boolean>(LOGIN).post(false)
            finish()
        })
    }

    private val dialog by lazy {
        AlertDialog.Builder(this).apply {
            setTitle("提示")
            setMessage(R.string.my_confirm_logout)
            setPositiveButton(R.string.confirm) { _, _ ->
                viewModel.logout()
            }
        }
    }

}