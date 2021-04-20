package com.tommy.shen.module_common.app

import android.app.Application
import androidx.room.Room
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.mmkv.MMKV
import com.tommy.shen.module_common.BuildConfig
import com.tommy.shen.module_common.db.MyDb

open class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
        lateinit var db: MyDb
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MMKV.initialize(this)
        db = Room.databaseBuilder(applicationContext, MyDb::class.java, "my-db").build()
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()    // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(instance) // 尽可能早，推荐在Application中初始化
    }

}