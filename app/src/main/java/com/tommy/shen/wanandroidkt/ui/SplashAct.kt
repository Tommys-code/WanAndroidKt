package com.tommy.shen.wanandroidkt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.tommy.shen.module_common.constant.Main
import kotlinx.coroutines.delay

class SplashAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            delay(2000)
            ARouter.getInstance().build(Main.MAIN_PAGE).navigation()
            finish()
        }
    }
}