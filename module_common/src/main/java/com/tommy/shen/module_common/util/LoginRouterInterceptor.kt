package com.tommy.shen.module_common.util

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.tommy.shen.module_common.constant.Login
import com.tommy.shen.module_common.constant.NEED_LOGIN

@Interceptor(priority = 10, name = "LoginInterceptor")
class LoginRouterInterceptor : IInterceptor {

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        //判断是否需要验证登录
        if (postcard?.extra ?: -1 == NEED_LOGIN) {
            if (isLogin()) callback?.onContinue(postcard)
            else Login.openLogin()
        } else callback?.onContinue(postcard)
    }

    override fun init(context: Context?) {

    }

}