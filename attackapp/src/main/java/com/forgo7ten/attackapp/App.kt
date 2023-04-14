package com.forgo7ten.attackapp

import android.app.Application

/**
 * @ClassName App
 * @Description // 全局App
 * @Author Forgo7ten
 **/
class App() : Application() {
    override fun onCreate() {
        super.onCreate()
        // 保存全局Context
        ConstValue.appContext = this
        ConstValue.initVulnerabilities()
    }
}