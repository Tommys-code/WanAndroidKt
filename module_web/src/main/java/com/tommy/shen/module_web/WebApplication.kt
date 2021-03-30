package com.tommy.shen.module_web

import com.tencent.smtt.sdk.QbSdk
import com.tommy.shen.module_common.app.BaseApplication
import com.tommy.shen.module_common.app.ModuleApplication

class WebApplication : ModuleApplication() {

    override fun onInit(application: BaseApplication) {
        QbSdk.initX5Environment(application.applicationContext, null)
    }

}