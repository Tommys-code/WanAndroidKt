package com.tommy.shen.module_login.act

import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV
import com.tommy.shen.module_common.base.BaseActivity
import com.tommy.shen.module_common.constant.*
import com.tommy.shen.module_common.util.DialogUtil
import com.tommy.shen.module_common.util.ToastUtils
import com.tommy.shen.module_login.R
import com.tommy.shen.module_login.databinding.ActLoginBinding
import com.tommy.shen.module_login.vm.LoginViewModel

@Route(path = Login.LOGIN_PAGE)
class LoginActivity : BaseActivity<ActLoginBinding>(), View.OnClickListener {

    @JvmField
    @Autowired(name = "register")
    var isRegister: Boolean = false

    private val viewModel by viewModels<LoginViewModel>()
    private val userKv by lazy { MMKV.mmkvWithID(USER) }

    override fun getLayoutId(): Int = R.layout.act_login

    override fun onCreate() {
        ARouter.getInstance().inject(this)

        binding.isRegister = isRegister
        binding.onClick = this

        addObserver()
    }

    private fun addObserver() {
        viewModel.registerAndLoginLiveData.observe(this, Observer {
            userKv?.encode(USER_ID, it.id)
            userKv?.encode(USER_NAME, it.nickname)
            LiveEventBus.get<Boolean>(LOGIN).post(true)
            finish()
        })
        viewModel.isShowLoading.observe(this, Observer {
            if (it) DialogUtil.showLoadingDialog(this)
            else DialogUtil.dismissLoadingDialog()
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> if (check()) {
                if (binding.isRegister) viewModel.register(
                    binding.accountEt.text.toString(),
                    binding.passwordEt.text.toString(),
                    binding.passwordConfirmEt.text.toString()
                ) else viewModel.login(
                    binding.accountEt.text.toString(),
                    binding.passwordEt.text.toString()
                )
            }
            R.id.login_or_register -> binding.isRegister = !binding.isRegister
        }
    }

    private fun check(): Boolean {
        return if (binding.isRegister) {
            binding.accountEt.check() && binding.passwordEt.check() && binding.passwordConfirmEt.check()
        } else {
            binding.accountEt.check() && binding.passwordEt.check()
        }
    }

    private fun EditText.check(hint: String? = null): Boolean {
        if (visibility == View.VISIBLE && text.isNullOrEmpty()) {
            ToastUtils.showToast(hint ?: "请${getHint()}")
            return false
        }
        return true
    }
}