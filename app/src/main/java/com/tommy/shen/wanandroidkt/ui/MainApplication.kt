package com.tommy.shen.wanandroidkt.ui

import com.tommy.shen.module_common.app.ApplicationImpl
import com.tommy.shen.module_common.app.BaseApplication

class MainApplication:BaseApplication(){

    override fun onCreate() {
        super.onCreate()
        modulesApplicationInit()
    }

    //通过反射方法找到对应的类，并调用接口
    private fun modulesApplicationInit() {
        for (moduleImpl in MODULES_LIST) {
            try {
                val clazz = Class.forName(moduleImpl)
                val obj = clazz.newInstance()
                if (obj is ApplicationImpl) {
                    obj.onInit(this)
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            }
        }
    }

}